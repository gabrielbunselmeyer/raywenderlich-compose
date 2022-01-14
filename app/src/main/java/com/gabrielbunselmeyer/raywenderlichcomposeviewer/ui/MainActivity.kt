package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed.FeedScreen
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.RayWenderlichComposeViewerTheme

class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RayWenderlichComposeViewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    FeedScreen(viewModel = viewModel)
                }
            }
        }
    }
}
