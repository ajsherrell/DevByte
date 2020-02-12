/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.devbyteviewer

import android.app.Application
import android.os.Build
import androidx.work.*
import com.example.android.devbyteviewer.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Override application to setup background work via WorkManager
 */
class DevByteApplication : Application() {
    /** Best Practice: The onCreate() method runs in the main thread.
     *  Performing a long-running operation in onCreate() might block the UI thread and
     *  cause a delay in loading the app. To avoid this problem, run tasks such as initializing
     *  Timber and scheduling WorkManager off the main thread, inside a coroutine.
     */
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    /**
     * onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    //add a new method called delayedInit() to start a coroutine.
    private fun delayedInit() {
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
            setupRecurringWork()
        }
    }

    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     */
    private fun setupRecurringWork() {
        //create constraint
        val constraints = Constraints.Builder()
        //add a network-type constraint to the constraints object.
        // Use the UNMETERED enum so that the work request will only
        // run when the device is on an unmetered network.
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }
                .build()

        //Inside the setupRecurringWork() method, create and initialize a
        // periodic work request to run once a day, using the PeriodicWorkRequestBuilder() method.
        // Pass in the RefreshDataWorker class that you created in the previous task.
        // Pass in a repeat interval of 1 with a time unit of TimeUnit.DAYS.
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build() //optional: make new repeatingRequest for (15, TimeUnit.MINUTES) to test in logcat.

        //schedule the work using the enqueueUniquePeriodicWork() method.
        // Pass in the KEEP enum for the ExistingPeriodicWorkPolicy.
        // Pass in repeatingRequest as the PeriodicWorkRequest parameter.
        WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest)
        //If pending (uncompleted) work exists with the same name,
        // the ExistingPeriodicWorkPolicy.KEEP parameter makes the WorkManager
        // keep the previous periodic work and discard the new work request.
    }
}

/** A Worker defines a unit of work, and the WorkRequest defines how and when work should be run.
 * There are two concrete implementations of the WorkRequest class:

The OneTimeWorkRequest class is for one-off tasks. (A one-off task happens only once.)
The PeriodicWorkRequest class is for periodic work, work that repeats at intervals.
Tasks can be one-off or periodic, so choose the class accordingly. For more information on scheduling
recurring work, see the recurring work documentation.

Note: The minimum interval for periodic work is 15 minutes. Periodic work can't have an initial delay
as one of its constraints.

In this task, you define and schedule a WorkRequest to run the worker that you created in the previous task.
Within an Android app, the Application class is the base class that contains all other components,
such as activities and services. When the process for your application or package is created, the
Application class (or any subclass of Application) is instantiated before any other class.

In this sample app, the DevByteApplication class is a subclass of the Application class.
The DevByteApplication class is a good place to schedule the WorkManager.

 */
