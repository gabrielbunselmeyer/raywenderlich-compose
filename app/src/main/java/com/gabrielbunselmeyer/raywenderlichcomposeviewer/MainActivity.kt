package com.gabrielbunselmeyer.raywenderlichcomposeviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.PracticalExamplesRepository
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.RayWenderlichComposeViewerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RayWenderlichComposeViewerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val response = remember { mutableStateOf("no response yet") }

    Text(text = "Hello $name!")
    Text(text = response.value)

    LaunchedEffect(true) {
        this.launch {
            response.value = PracticalExamplesRepository().getArticles()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RayWenderlichComposeViewerTheme {
        Greeting("Android")
    }
}