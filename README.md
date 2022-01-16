# purple raywenderlich
An Android app based on the [Raywenderlich.com interview iOS project](https://github.com/raywenderlich/ios-interview/tree/master/Practical%20Example), but purple! 

Done mainly as a way to hone my Jetpack Compose skills, this small app uses the [official raywenderlich app](https://play.google.com/store/apps/details?id=com.razeware.emitron) as inspiration, and displays a list of tutorials using the iOS interview project dataset. The cards and the information displayed within them are pretty basic, and no navigation was implemented for separate screens showing the actual videos and courses video collections (yet at least!).

## demo

<details>
  <summary>Recorded on a Galaxy S21+ on Android 12</summary>

https://user-images.githubusercontent.com/29930410/149681830-abdeb34d-68c3-4b1c-a6df-d6e1362137c5.mp4

</details>

## strucure

a

## libraries used
Other than the expected Android, Kotlin, AndroidX and Jetpack Compose libs, the following were used:
- [retrofit2](https://square.github.io/retrofit/): to call the GitHub API a bit more easily. Plus familiarity.
- [coil-compose](https://coil-kt.github.io/coil/compose/): as it's the recommended way to load images in Jetpack Compose.
- [fuzzywuzzy](https://github.com/willowtreeapps/fuzzywuzzy-kotlin): so I don't have to reinvent the wheel for fuzzy string matching.
- [accompanist-swiperefresh](https://github.com/google/accompanist/tree/main/swiperefresh): for, well, adding swipe refresh to the list. Currently at the release candidate stage.
- [compose-shimmer](https://github.com/kazemihabib/compose-shimmer): for the shimmer effect on the empty list skeleton.

## known issues
- There is no dark theme. The app currently forces light colors.
- When searching in combination with a specific set of filters, instead of an empty list when no tutorials are found, the app might display the skeleton as if it was loading content.
- Performance for the toolbar could possibly be improved through better state management.
- The filters menu should use Chips instead of the current Switches, as those are more common in Android. 
- The filters UI could be improved significantly to better deal with different screen sizes. Support for those is limited at the moment and the text in each button might get cut in certain scenarios.
- Searchbar doesn't have a "delete all text" button on its right.
