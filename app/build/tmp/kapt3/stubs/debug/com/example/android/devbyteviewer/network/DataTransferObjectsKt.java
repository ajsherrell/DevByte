package com.example.android.devbyteviewer.network;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 2, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003\u001a\u0010\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001*\u00020\u0003\u00a8\u0006\u0006"}, d2 = {"asDatabaseModel", "", "Lcom/example/android/devbyteviewer/database/DatabaseVideo;", "Lcom/example/android/devbyteviewer/network/NetworkVideoContainer;", "asDomainModel", "Lcom/example/android/devbyteviewer/domain/DevByteVideo;", "app_debug"})
public final class DataTransferObjectsKt {
    
    /**
     * Convert Network results to database objects
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.example.android.devbyteviewer.domain.DevByteVideo> asDomainModel(@org.jetbrains.annotations.NotNull()
    com.example.android.devbyteviewer.network.NetworkVideoContainer $this$asDomainModel) {
        return null;
    }
    
    /**
     * In this sample app, the conversion is simple, and some of this code isn't necessary.
     * But in a real-world app, the structure of the domain, database, and network objects will be different.
     * You'll need conversion logic, which can get complicated.
     * Convert Network results to database objects.
     * create an extension function called asDatabaseModel().
     * Use the function to convert network objects into DatabaseVideo database objects.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.example.android.devbyteviewer.database.DatabaseVideo> asDatabaseModel(@org.jetbrains.annotations.NotNull()
    com.example.android.devbyteviewer.network.NetworkVideoContainer $this$asDatabaseModel) {
        return null;
    }
}