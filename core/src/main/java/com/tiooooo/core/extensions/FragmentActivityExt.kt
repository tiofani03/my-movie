package com.tiooooo.core.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun FragmentActivity.getLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit,
) =
    lifecycleScope.launch(context = context) { block.invoke(this) }


fun FragmentActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().remove(fragment).commit()
}

fun FragmentActivity.getLifeCycle() = lifecycle

fun FragmentActivity.getFragmentManagers() = supportFragmentManager

inline fun <reified T> FragmentActivity.findFragmentListener(tag: String): T? {
    return supportFragmentManager.findFragmentByTag(tag) as? T
}
