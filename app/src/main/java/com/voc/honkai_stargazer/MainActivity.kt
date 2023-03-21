package com.voc.honkai_stargazer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.voc.honkai_stargazer.ui.AppScaffold
import com.voc.honkai_stargazer.ui.theme.HonkaiStargazerTheme
import com.voc.honkai_stargazer.util.JsonReader
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HonkaiStargazerTheme {
                AppScaffold(JsonReader().convertJsonToObject(this))
            }
        }
    }

}
