package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.PracticalExamplesRepository
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.TutorialData
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.toast
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Because this is a small app, I've opted to using a single ViewModel.
 * If expanded, it'd of course make sense to create screen-specific ViewModels and break this one up.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Here we only allow other classes to get the state flow, not the State directly.
    // This centralized ViewModel holds the single source of truth for app state.
    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    // The ViewModel is the single source for our repository. It fetches the required files from the GitHub API.
    // The CoroutinesExceptionHandler is used together with the Retrofit scheduler when launching the ViewModel Coroutine scope.
    // It's used so we don't crash out on HttpExceptions. This is a pretty simplistic solution.
    // Error handling on a bigger application should probably be more robust.
    private val repository = PracticalExamplesRepository()
    private val repositoryCoroutinesExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorMessage = "Something went wrong when fetching from the GitHub API!"
        throwable.printStackTrace()
        throwable.message?.let {
            application.applicationContext.toast("$errorMessage $it")
        }
    }

    fun fetchContent() {
        // Retrofit makes so we don't need to include the Dispatcher.IO scheduler here.
        viewModelScope.launch(repositoryCoroutinesExceptionHandler) {
            val contentList = mutableListOf<TutorialData>()

            contentList.addAll(repository.getArticles().data)
            contentList.addAll(repository.getVideos().data)
            updateContent(contentList)
        }
    }

    private fun updateContent(content : List<TutorialData>) {
        mutableState.mutate {
            copy(loadedTutorials = content)
        }
    }

    private fun <T> MutableStateFlow<T>.mutate(mutateFunction: T.() -> T) {
        value = value.mutateFunction()
    }
}