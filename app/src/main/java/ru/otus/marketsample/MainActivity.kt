package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.marketsample.ui.navigation.MainScreen
import ru.otus.marketsample.ui.theme.MarketSampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MarketSampleTheme {
                MainScreen()
            }
        }
    }
}
