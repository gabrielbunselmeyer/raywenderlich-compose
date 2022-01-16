# purple raywenderlich
An Android app based on the [Raywenderlich.com interview iOS project](https://github.com/raywenderlich/ios-interview/tree/master/Practical%20Example), but purple! 

Done mainly as a way to hone my Jetpack Compose skills, this small app uses the [official raywenderlich app](https://play.google.com/store/apps/details?id=com.razeware.emitron) as inspiration, and displays a list of tutorials using the iOS interview project dataset. The cards and the information displayed within them are pretty basic, and no navigation was implemented for separate screens showing the actual videos and courses video collections (yet at least!).

## demo

<details>
  <summary>Recorded on a Galaxy S21+ on Android 12</summary>

https://user-images.githubusercontent.com/29930410/149681830-abdeb34d-68c3-4b1c-a6df-d6e1362137c5.mp4

</details>

## strucure

Starting with Gradle, I opted for the Kotlin DSL instead of Groovy, and also used the experimental [Version Catalog](https://docs.gradle.org/current/userguide/platforms.html), as seen in [`settings.gradle.kts`](https://github.com/gabrielbunselmeyer/raywenderlich-compose/blob/main/settings.gradle.kts).

For the application itself, there is a single Activity holding the [`TutorialListScreen`](https://github.com/gabrielbunselmeyer/raywenderlich-compose/blob/main/app/src/main/java/com/gabrielbunselmeyer/raywenderlichcomposeviewer/ui/tutorialfeed/TutorialListScreen.kt). Then, the list screen composes a [`ListToolbar`](https://github.com/gabrielbunselmeyer/raywenderlich-compose/blob/main/app/src/main/java/com/gabrielbunselmeyer/raywenderlichcomposeviewer/ui/tutorialfeed/ListToolbar.kt) with a searchbox and filter options as a header to the `LazyColumn` used to list the content.

Brought in through the [GitHub API](https://docs.github.com/en/rest) in [PracticalExamplesRepository](https://github.com/gabrielbunselmeyer/raywenderlich-compose/blob/main/app/src/main/java/com/gabrielbunselmeyer/raywenderlichcomposeviewer/data/PracticalExamplesRepository.kt), the resulting JSON is serialized and listed in the UI through the [`TutorialCard`](https://github.com/gabrielbunselmeyer/raywenderlich-compose/blob/main/app/src/main/java/com/gabrielbunselmeyer/raywenderlichcomposeviewer/ui/tutorialfeed/TutorialCard.kt).

Controlling all this is a single [`MainViewModel`](https://github.com/gabrielbunselmeyer/raywenderlich-compose/blob/main/app/src/main/java/com/gabrielbunselmeyer/raywenderlichcomposeviewer/ui/MainViewModel.kt). In there, the app [`State`](https://github.com/gabrielbunselmeyer/raywenderlich-compose/blob/main/app/src/main/java/com/gabrielbunselmeyer/raywenderlichcomposeviewer/ui/State.kt) is created and made available as a [`StateFlow`](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow).

The `MainViewModel` is instantiated in the `MainActivity`, and the `State` observed there. This observable is passed into all the children composables, so the `MainViewModel` itself stays in the `MainActivity`. Together with the `State`, the composables also receive a `Dispatcher` function from the `MainViewModel`, which uses [`Actions`](https://github.com/gabrielbunselmeyer/raywenderlich-compose/blob/main/app/src/main/java/com/gabrielbunselmeyer/raywenderlichcomposeviewer/ui/model/Action.kt) to update the `State` and do whatever else is necessary.

This way, composables down the line can observe the `State` and dispatch `Actions` to the `MainViewModel` if needed, without needing direct access to the `MainViewModel` object.

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
- API error handling is far from ideal. Currently it just displays a toast with the error.
- There are no tests!!!

## github api rate limit
Inside the (`PracticalExamplesRepository`](https://github.com/gabrielbunselmeyer/raywenderlich-compose/blob/main/app/src/main/java/com/gabrielbunselmeyer/raywenderlichcomposeviewer/data/PracticalExamplesRepository.kt) you'll find a commented out section. The GitHub API has a rate limit of 60 calls per hour for non-authorized requesters. This proved limiting during development, but, for testing it should be enough.

Regardless, I left in the `Interceptor` I used while developing the app and instructions on how to use it. If you find the rate limit to be too low, you can change it up to use your own GitHub profile and an access token.
