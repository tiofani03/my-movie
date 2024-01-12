package com.tiooooo.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T> Fragment.collectFlow(flow: Flow<T>, action: ((T) -> Unit)) {
    getLaunch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            flow.collect {
                action.invoke(it)
            }
        }
    }
}

fun Fragment.getLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit,
) =
    viewLifecycleOwner.lifecycleScope.launch(context = context) { block.invoke(this) }

fun Fragment.removeFragment(fragment: Fragment? = null) {
    childFragmentManager.beginTransaction().remove(fragment ?: this).commit()
}

fun Fragment.getLifeCycle() = viewLifecycleOwner.lifecycle

fun Fragment.getFragmentManagers() = childFragmentManager

inline fun <reified T> Fragment.findActivityListener(): T? {
    return activity as? T
}

inline fun <reified T> Fragment.findFragmentListener(tag: String): T? {
    return childFragmentManager.findFragmentByTag(tag) as? T
}
