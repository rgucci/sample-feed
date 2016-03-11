9GAG Feed Sample App

Description
This application will display a “feed” (a list of posts containing images and captions) similar to those displayed in the 9gag website. There are three tabs, each corresponding to one of the three categories: FRESH, HOT and TRENDING.

Each tab will display the feed for each category. Swiping left or right will switch to the previous/next tabs. Initially, only the first page of items will be displayed. Scrolling to the bottom of the list will auto load the next pages (maximum of 3 pages). The horizontal list at the top will also auto load the next pages when the rightmost items are displayed.

The data for the feed is taken from json files stored as “raw” resources in the APK. Note that an internet connection is needed to actually download and display the images.
Frameworks and libraries used:
Android-Clean Architecture was used as the main framework starting point, then modified to display feed items. This is a good starting point to make sure that a good MVP pattern and separation of concerns and proper depenencies are used.
https://github.com/android10/Android-CleanArchitecture

RecyclerView was used to implement both the vertical and horizontal lists.

Dagger2 was used for dependency injection throughout the project
http://google.github.io/dagger/

Butterknife was used for view binding in the presentation layer
http://jakewharton.github.io/butterknife/

RxJava was used to implement asynchronous calls to get data
https://github.com/ReactiveX/RxJava

Glide was used for image downloading and caching
https://github.com/bumptech/glide
