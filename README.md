# DevByte - Starter Code
======================
- Made with Google Code Labs!
- Room
- Repository
- Caching
- WorkManager

Introduction
------------

DevByteViewer app displays a list of DevByte videos. DevByte videos are short
videos made by the Google Android developer relations team to introduce new
developer features on Android. They're also a great way to stay up to date with
new features as they come out as well as tips and best practices. This app
fetches the DevByte video list from the network using the Retrofit library and
displays it on the screen. It uses a ViewModel and LiveData to hold the data and
update the UI. Since the video list is big, results are displayed in a
RecyclerView.

- Explore the code:
The data transfer object is used to parse the network result. This file also contains a convenience method, asDomainModel(), to convert network results to a list of domain objects. The data transfer objects are different from the domain objects, because they contain extra logic for parsing network results. Tip: It's a best practice to separate the network, domain, and database objects. This strategy follows the separation of concerns principle. If the network response or the database schema changes, you want to be able to change and manage app components without updating the entire app's code. 
The Retrofit service, network/Service.kt, fetches the devbytes playlist from the network.
The DevByteViewModel holds the app data as LiveData objects.
The UI controller, DevByteFragment, contains a RecyclerView to display the video list and the observers for the LiveData objects.

### Caching
- After an app fetches data from the network, the app can cache the data by storing the data in a device's storage. You cache data so that you can access it later when the device is offline, or if you want to access the same data again.

The following shows several ways to implement network caching in Android. In this codelab, you use Room, because it's the recommended way to store structured data on a device file system.

- Retrofit is a networking library used to implement a type-safe REST client for Android. You can configure Retrofit to store a copy of every network result locally.
- - Good solution for simple requests and responses, infrequent network calls, or small datasets.
- You can use SharedPreferences to store key-value pairs.
- - 	
Good solution for a small number of keys and simple values. You can't use this technique to store large amounts of structured data.
- You can access the app's internal storage directory and save data files in it. Your app's package name specifies the app's internal storage directory, which is in a special location in the Android file system. This directory is private to your app, and it is cleared when your app is uninstalled.
- - Good solution if you have specific needs that a file system can solve—for example, if you need to save media files or data files and you have to manage the files yourself. You can't use this technique to store complex and structured data.
- You can cache data using Room, which is an SQLite object-mapping library that provides an abstraction layer over SQLite.
- - Recommended solution for complex and structured data, because the best way to store structured data on a device's file system is in a local SQLite database.

### Room
- Key concept: Don't retrieve data from the network every time the app is launched. Instead, display data that you fetch from the database. This technique decreases app-loading time. When the app fetches data from the network, store the data in the database instead of displaying the data immediately.

When a new network result is received, update the local database and display the new content on the screen from the local database. This technique ensures that the offline cache is always up-to-date. Also, if the device is offline, your app can still load locally cached data.

### Repository
- The repository pattern is a design pattern that isolates data sources from the rest of the app.
- A repository mediates between data sources (such as persistent models, web services, and caches) and the rest of the app. 
- The repository class isolates the data sources from the rest of the app and provides a clean API for data access to the rest of the app. Using a repository class is a recommended best practice for code separation and architecture.
- A repository module handles data operations and allows you to use multiple backends. In a typical real-world app, the repository implements the logic for deciding whether to fetch data from a network or use results that are cached in a local database. This helps make your code modular and testable. You can easily mock up the repository and test the rest of the code.
- A database refresh is a process of updating or refreshing the local database to keep it in sync with data from the network. For this sample app, you use a very simple refresh strategy, where the module that requests data from the repository is responsible for refreshing the local data. In a real-world app, your strategy might be more complex. For example, your code might automatically refresh the data in the background (taking bandwidth into account), or cache the data that the user is most likely to use next.

### WorkManager
- Most real-world apps need to perform long-running background tasks. For example, an app might upload files to a server, sync data from a server and save it to a Room database, send logs to a server, or execute expensive operations on data. Such operations should be performed in the background, off the UI thread (main thread). Background tasks consume a device's limited resources, like RAM and battery. This may result in a poor experience for the user if not handled correctly.

In this codelab, you learn how to use WorkManager to schedule a background task in an optimized and efficient way. To learn more about other available solutions for background processing in Android, see Guide to background processing.
- WorkManager is for background work that's deferrable and requires guaranteed execution:
- - Deferrable means that the work is not required to run immediately. For example, sending analytical data to the server or syncing the database in the background is work that can be deferred.
- - Guaranteed execution means that the task will run even if the app exits or the device restarts.
While WorkManager runs background work, it takes care of compatibility issues and best practices for battery and system health. WorkManager offers compatibility back to API level 14. WorkManager chooses an appropriate way to schedule a background task, depending on the device API level. It might use JobScheduler (on API 23 and higher) or a combination of AlarmManager and BroadcastReceiver.
- WorkManager also lets you set criteria on when the background task runs. For example, you might want the task to run only when the battery status, network status, or charge state meet certain criteria. You learn how to set constraints later in this codelab. Note:

WorkManager is not intended for in-process background work that can be terminated safely if the app process is killed.
WorkManager is not intended for tasks that require immediate execution.

- Worker:
This class is where you define the actual work (the task) to run in the background. You extend this class and override the doWork() method. The doWork() method is where you put code to be performed in the background, such as syncing data with the server or processing images.

- WorkRequest:
This class represents a request to run the worker in background. Use WorkRequest to configure how and when to run the worker task, with the help of Constraints such as device plugged in or Wi-Fi connected. 

- WorkManager:
This class schedules and runs your WorkRequest. WorkManager schedules work requests in a way that spreads out the load on system resources, while honoring the constraints that you specify.
- After you define your WorkRequest, you can schedule it with WorkManager, using the enqueueUniquePeriodicWork() method. This method allows you to add a uniquely named PeriodicWorkRequest to the queue, where only one PeriodicWorkRequest of a particular name can be active at a time.
For example, you might only want one sync operation to be active. If one sync operation is pending, you can choose to let it run or replace it with your new work, using an ExistingPeriodicWorkPolicy.

- Constraints:
When defining the WorkRequest, you can specify constraints for when the Worker should run. For example, you might want to specify that the work should only run when the device is idle, or only when the device is plugged in and connected to Wi-Fi. You can also specify a backoff policy for retrying work. The supported constraints are the set methods in Constraints.Builder. 
- PeriodicWorkRequest and constraints:

A WorkRequest for repeating work, for example PeriodicWorkRequest, executes multiple times until it is cancelled. The first execution happens immediately, or as soon as the given constraints are met.

The next execution happens during the next period interval. Note that execution might be delayed, because WorkManager is subject to OS battery optimizations, for example when the device is in Doze mode.



#### Summary
- Caching is a process of storing data fetched from a network on a device's storage. Caching lets your app access data when the device is offline, or if your app needs to access the same data again.
The best way for your app to store structured data on a device's file system is to use a local SQLite database. Room is an SQLite object-mapping library, meaning that it provides an abstraction layer over SQLite. Using Room is the recommended best practice for implementing offline caching.
A repository class isolates data sources, such as Room database and web services, from the rest of the app. The repository class provides a clean API for data access to the rest of the app.
Using repositories is a recommended best practice for code separation and architecture.
When you design an offline cache, it's a best practice to separate the app's network, domain, and database objects. This strategy is an example of separation of concerns.
To add offline-support to an app, add a local database using Room. Implement a repository to manage and access the Room database. In the ViewModel, fetch and display the data directly from the repository instead of fetching the data from the network.
Use a database refresh strategy to insert and update the data in the local database. In a database refresh, the local database is updated or refreshed to keep it in sync with data from the network.
To update your app's UI data automatically when the data in the database changes, use observable queries with a return value of type LiveData in the DAO. When the Room database is updated, Room runs the query in background to update the associated LiveData.
- The WorkManager API makes it easy to schedule deferrable, asynchronous tasks that must be run reliably.
Most real-world apps need to perform long-running background tasks. To schedule a background task in an optimized and efficient way, use WorkManager.
The main classes in the WorkManager library are Worker, WorkRequest, and WorkManager.
The Worker class represents a unit of work. To implement the background task, extend the Worker class and override the doWork() method.
The WorkRequest class represents a request to perform a unit of work. WorkRequest is the base class for specifying parameters for work that you schedule in WorkManager.
There are two concrete implementations of the WorkRequest class: OneTimeWorkRequest for one-off tasks, and PeriodicWorkRequest for periodic work requests.
When defining the WorkRequest, you can specify Constraints indicating when the Worker should run. Constraints include things like whether the device is plugged in, whether the device is idle, or whether Wi-Fi is connected.
To add constraints to the WorkRequest, use the set methods listed in the Constraints.Builder documentation. For example, to indicate that the WorkRequest should not run if the device battery is low, use the setRequiresBatteryNotLow() set method.
After you define the WorkRequest, hand off the task to the Android system. To do this, schedule the task using one of the WorkManager enqueue methods.
The exact time that the Worker is executed depends on the constraints that are used in the WorkRequest, and on system optimizations. WorkManager is designed to give the best possible behavior, given these restrictions