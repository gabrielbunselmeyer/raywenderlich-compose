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
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed.FeedScreen

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsState(State())

            RayWenderlichComposeViewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    FeedScreen(state, viewModel::dispatch)
                }
            }
        }
    }
}
