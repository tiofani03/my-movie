package com.tiooooo.core.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

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
