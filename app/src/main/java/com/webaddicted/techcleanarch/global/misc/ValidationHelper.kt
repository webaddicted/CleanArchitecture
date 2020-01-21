package com.webaddicted.techcleanarch.global.misc

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.webaddicted.techcleanarch.R
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class ValidationHelper {
    companion object {
        fun showSnackBar(parentLayout: View, msg: String) {
            val snackBar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT)
            snackBar.setActionTextColor(Color.WHITE)
            val view = snackBar.view
            val tv = view.findViewById(R.id.snackbar_text) as TextView
            tv.setTextColor(Color.WHITE)
            snackBar.show()

        }
    }
}
