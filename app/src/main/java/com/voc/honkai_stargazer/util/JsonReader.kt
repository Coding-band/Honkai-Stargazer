package com.voc.honkai_stargazer.util

import android.content.Context
import com.voc.honkai_stargazer.R
import java.io.InputStream

class JsonReader {
    fun convertJsonToObject(context: Context) : String {
        return context.resources.openRawResource(R.raw.character_data)
            .bufferedReader().use { it.readText() }
    }
}