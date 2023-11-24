package com.payment.ayushdigitils.ui.utils

import android.content.Context
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.epoxy.Carousel

/**
 * Created by Rinav Gangar <rinav.dev> on 17/11/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
class LinearSnapHelperFactory() : Carousel.SnapHelperFactory() {

    override fun buildSnapHelper(context: Context): SnapHelper {
        return LinearSnapHelper()
    }
}