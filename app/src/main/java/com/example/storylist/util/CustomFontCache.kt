package com.example.storylist.util

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import java.util.HashMap

object CustomFontCache {
    private val TAG = "CustomFontCache"

    private val name2Typeface = HashMap<String, Typeface>()

    fun lookForTypeface(ctx: Context, fontName: String?): Typeface? {
        try {
            if (fontName == null)
                return null
            var loadedInstance = name2Typeface[fontName]
            if (loadedInstance == null) {
                var assetName: String = fontName
                while (true) {
                    try {
                        loadedInstance = Typeface.createFromAsset(ctx.assets, assetName)
                        if (loadedInstance != null) {
                            name2Typeface[fontName] = loadedInstance
                            break
                        }
                    } catch (ex: Throwable) {
                        //probably asset not found
                        Log.e(TAG, "Asset not found: " + ex.message)
                    }

                    if (loadedInstance == null) {
                        if (!assetName.endsWith(".ttf")) {
                            assetName = "$fontName.ttf"
                        } else
                            break
                    }
                }
            }
            return loadedInstance
        } catch (e: Exception) {
            Log.e(TAG, "Custom font not found: " + e.message)
            return null
        }

    }
}