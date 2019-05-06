package com.example.storylist.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.example.storylist.R
import com.example.storylist.util.CustomFontCache


class CustomFontTextView : AppCompatTextView {
    private val TAG = "SimplTextView"

    constructor(context: Context) : this (context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0) {
        setCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        setCustomFont(context, attrs)
    }

    private fun setCustomFont(ctx: Context, attrs: AttributeSet?) {
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.com_example_storylist_ui_view_CustomFontTextView)
        try {
            val customFont = a.getString(R.styleable.com_example_storylist_ui_view_CustomFontTextView_customFontTypeFace)
            val tf = CustomFontCache.lookForTypeface(ctx, customFont)
            if (tf != null) {
                setTypeface(tf)
            }
        } catch (e: Exception) {
            Log.e(TAG, "unable to set custom font")
        } finally {
            a.recycle()
        }
    }
}