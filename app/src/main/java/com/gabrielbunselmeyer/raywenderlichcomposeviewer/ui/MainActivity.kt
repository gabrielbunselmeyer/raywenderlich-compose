package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.RayWenderlichComposeViewerTheme
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed.TutorialListScreen

class MainActivity : ComponentActivity() {

    // Because of the low complexity of the app, I opted on using a single MainViewModel.
    // It holds and is the single source of access for changing the app's State.
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsState(State())

            RayWenderlichComposeViewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // So we don't have to pass the whole MainViewModel everywhere, I'm passing
                    // in just the app State and using a dispatcher to communicate with the
                    // ViewModel when needed down the line.
                    TutorialListScreen(state, viewModel::dispatch)
                }
            }
        }
    }
}
