package com.example.android.devbyteviewer.repository;

import java.lang.System;

/**
 * Repository for fetching devbyte videos from the network and storing them on disk.
 * create a repository to manage the offline cache, which you implemented in the previous task.
 * Your Room database doesn't have logic for managing the offline cache, it only has methods to
 * insert and retrieve the data. The repository will have the logic to fetch the network results
 * and to keep the database up-to-date.
 * Pass in a VideosDatabase object as the class's constructor parameter to access the Dao methods.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2 = {"Lcom/example/android/devbyteviewer/repository/VideosRepository;", "", "database", "Lcom/example/android/devbyteviewer/database/VideosDatabase;", "(Lcom/example/android/devbyteviewer/database/VideosDatabase;)V", "videos", "Landroidx/lifecycle/LiveData;", "", "Lcom/example/android/devbyteviewer/domain/DevByteVideo;", "getVideos", "()Landroidx/lifecycle/LiveData;", "refreshVideos", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class VideosRepository {
    
    /**
     * create a LiveData object to read the video playlist from the database.
     * This LiveData object is automatically updated when the database is updated.
     * The attached fragment, or the activity, is refreshed with new values.
     * declare a LiveData object called videos to hold a list of DevByteVideo objects.
     * Initialize the videos object, using database.videoDao. Call the getVideos() DAO method.
     * Because the getVideos() method returns a list of database objects, not a list of
     * DevByteVideo objects, Android Studio throws a "type mismatch" error.
     */
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.example.android.devbyteviewer.domain.DevByteVideo>> videos = null;
    private final com.example.android.devbyteviewer.database.VideosDatabase database = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.example.android.devbyteviewer.domain.DevByteVideo>> getVideos() {
        return null;
    }
    
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
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshVideos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> p0) {
        return null;
    }
    
    public VideosRepository(@org.jetbrains.annotations.NotNull()
    com.example.android.devbyteviewer.database.VideosDatabase database) {
        super();
    }
}