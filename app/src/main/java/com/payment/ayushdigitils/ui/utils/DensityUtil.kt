package com.payment.ayushdigitils.ui.utils

import android.content.Context
import android.graphics.Point
import android.util.TypedValue
import android.view.WindowManager


class DensityUtil private constructor() {
    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {
        private var screenWidth = 0
        private var screenHeight = 0
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun sp2px(context: Context, spVal: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spVal, context.resources.displayMetrics
            ).toInt()
        }

        fun px2sp(context: Context, pxVal: Float): Float {
            return pxVal / context.resources.displayMetrics.scaledDensity
        }

        fun getScreenHeight(c: Context): Int {
            if (screenHeight == 0) {
                val wm = c.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = wm.defaultDisplay
                val size = Point()
                display.getSize(size)
                screenHeight = size.y
            }
            return screenHeight
        }

        fun getScreenWidth(c: Context): Int {
            if (screenWidth == 0) {
                val wm = c.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = wm.defaultDisplay
                val size = Point()
                display.getSize(size)
                screenWidth = size.x
            }
            return screenWidth
        }
    }
}