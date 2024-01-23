package com.tuna.cinergy.business.domain.utils.exceptionView

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.textview.MaterialTextView
import com.tuna.cinergy.R

fun createExceptionView(
    type: String,
    layoutInflater: LayoutInflater,
    container: FrameLayout
) {

    when (type) {
        ExceptionItems.EMPTY.label -> {

            val emptyView: View =
                LayoutInflater.from(layoutInflater.context)
                    .inflate(R.layout.layout_no_data, container, false)
            container.addView(emptyView)

        }
        ExceptionItems.NO_NETWORK.label -> {

        }
        ExceptionItems.SOMETHING_WENT_WRONG.label -> {

        }
    }
}