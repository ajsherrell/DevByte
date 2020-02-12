# DevByte - Starter Code
======================
- Made with Google Code Labs!
- Room
- Repository
- Caching

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