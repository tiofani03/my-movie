package com.tiooooo.core.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.flow.Flow
import kotlin.math.abs

fun <T> AppCompatActivity.collectFlow(flow: Flow<T>, action: ((T) -> Unit)){
    getLaunch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collect{
                action.invoke(it)
            }
        }
    }
}

fun AppCompatActivity.hideKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.window.attributes.token, 0)
}

fun AppCompatActivity.setCollapsing(
    title: String? = null,
    collapsingToolbar: CollapsingToolbarLayout,
    tvTitle: TextView,
    appbar: AppBarLayout,
) {
    collapsingToolbar.title = ""
    tvTitle.text = " "
    val hexColor = "#FFFFFF"
    val color = Color.parseColor(hexColor)
    collapsingToolbar.setCollapsedTitleTextColor(color)

    appbar.setExpanded(true)
    appbar.addOnOffsetChangedListener(object :
        AppBarLayout.OnOffsetChangedListener {
        var isShow = false
        var scrollRange = -1


        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            (appBarLayout!!.totalScrollRange - abs(n = verticalOffset).toFloat()) / appBarLayout.totalScrollRange

            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                collapsingToolbar.title = title
                tvTitle.text = title
                isShow = true
            } else if (isShow) {
                collapsingToolbar.title = " "
                tvTitle.text = " "
                isShow = false
            }
        }
    })

}
