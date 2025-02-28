package com.voc.stargazer3

import App
import ContextFactory
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(ContextFactory(this))
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT,
            )
        )
    }

    /**
     * Prevent Android Text Scaling
     */
    override fun attachBaseContext(newBase: Context) {
        val newOverride = newBase.createConfigurationContext(newBase.resources.configuration.apply {
            fontScale = 1f
        })
        super.attachBaseContext(newOverride)
    }



}