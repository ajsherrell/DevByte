package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException
import timber.log.Timber

/** add a Worker to pre-fetch the DevBytes video playlist in the background.
 *Extend the RefreshDataWorker class from the CoroutineWorker class.
 * Pass in the context and WorkerParameters as constructor parameters. */
class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
        CoroutineWorker(appContext, params) {

    // Define a work name to uniquely identify this worker.
    companion object {
        const val WORK_NAME = "com.example.android.devbyteviewer.work.RefreshDataWorker"
    }

    //A suspending function is a function that can be paused and resumed later.
    // A suspending function can execute a long running operation and wait for it
    // to complete without blocking the main thread.
    override suspend fun doWork(): Result {
        //inside doWork(), create and instantiate a VideosDatabase object and a VideosRepository object.
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)
        //call the refreshVideos() method inside a try block. Add a log to track when the worker is run.
        try {
            repository.refreshVideos()
            Timber.d("Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }
        return Result.success()
    }
}
/** The doWork() method inside the Worker class is called on a background thread.
 * The method performs work synchronously, and should return a ListenableWorker.Result object.
 * The Android system gives a Worker a maximum of 10 minutes to finish its execution and return
 * a ListenableWorker.Result object. After this time has expired, the system forcefully stops the Worker.
 * To create a ListenableWorker.Result object, call one of the following static methods to indicate the completion status of the background work:

Result.success()—work completed successfully.
Result.failure()—work completed with a permanent failure.
Result.retry()—work encountered a transient failure and should be retried.
In this task, you implement the doWork() method to fetch the DevBytes video playlist from the network.
You can reuse the existing methods in the VideosRepository class to retrieve the data from the network.*/