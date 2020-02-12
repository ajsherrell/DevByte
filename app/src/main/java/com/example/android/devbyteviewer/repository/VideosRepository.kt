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

package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.DevByteVideo
import com.example.android.devbyteviewer.network.DevByteNetwork
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/** Repository for fetching devbyte videos from the network and storing them on disk.
 * create a repository to manage the offline cache, which you implemented in the previous task.
 * Your Room database doesn't have logic for managing the offline cache, it only has methods to
 * insert and retrieve the data. The repository will have the logic to fetch the network results
 * and to keep the database up-to-date.
 * Pass in a VideosDatabase object as the class's constructor parameter to access the Dao methods.*/
class VideosRepository(private val database: VideosDatabase) {

    /** create a LiveData object to read the video playlist from the database.
     * This LiveData object is automatically updated when the database is updated.
     * The attached fragment, or the activity, is refreshed with new values.
     * declare a LiveData object called videos to hold a list of DevByteVideo objects.
     * Initialize the videos object, using database.videoDao. Call the getVideos() DAO method.
     * Because the getVideos() method returns a list of database objects, not a list of
     * DevByteVideo objects, Android Studio throws a "type mismatch" error.*/
    val videos: LiveData<List<DevByteVideo>> = Transformations.map(database.videoDao.getVideos()) {
        it.asDomainModel()
    }
    //To fix the error, use Transformations.map to convert the list of database objects
    // to a list of domain objects. Use the asDomainModel() conversion function.
    // Refresher: The Transformations.map method uses a conversion function to convert
    // one LiveData object into another LiveData object. The transformations are only
    // calculated when an active activity or a fragment is observing the returned LiveData property.

    /**
     * Refresh the videos stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * add a refreshVideos() method that has no arguments and returns nothing.
     * This method will be the API used to refresh the offline cache.
     *
     * Note: Databases on Android are stored on the file system, or disk, and in order to save they
     * must perform a disk I/O. The disk I/O, or reading and writing to disk, is slow and always
     * blocks the current thread until the operation is complete. Because of this, you have to run
     * the disk I/O in the I/O dispatcher. This dispatcher is designed to offload blocking I/O
     * tasks to a shared pool of threads using withContext(Dispatchers.IO) { ... }.
     *
     * Inside the refreshVideos() method, switch the coroutine context to Dispatchers.IO
     * to perform network and database operations.
     */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh videos is called")
            //fetch the DevByte video playlist from the network using the Retrofit service instance,
            // DevByteNetwork. Use the await() function to suspend the coroutine until the
            // playlist is available.
            val playlist = DevByteNetwork.devbytes.getPlaylist().await()
            //after fetching the playlist from the network, store the playlist in the Room database.
            // To store the playlist, use the VideosDatabase object, database. Call the insertAll
            // DAO method, passing in the playlist retrieved from the network. Use the
            // asDatabaseModel() extension function to map the playlist to the database object.
            database.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }

}

