package com.tiooooo.core.base

import android.content.pm.ActivityInfo
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.tiooooo.core.network.data.States

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB

    abstract fun getViewBinding(): VB

    protected open fun initView() {}
    protected open fun initListener() {}
    protected open fun setViewModelObservable() {}

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        performViewBinding()
        requestedOrientation.apply {
            ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        initView()
        initListener()
        setViewModelObservable()
    }

    private fun performViewBinding() {
        binding = getViewBinding()
        setContentView(binding.root)
    }

    fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun <T> handleDataState(
        state: States<T>,
        loadingBlock: () -> Unit,
        successBlock: (T) -> Unit,
        emptyBlock: () -> Unit,
        errorBlock: (String?) -> Unit
    ) {
        when (state) {
            is States.Loading -> loadingBlock.invoke()
            is States.Success -> successBlock.invoke(state.data)
            is States.Empty -> emptyBlock.invoke()
            is States.Error -> errorBlock.invoke(state.message)
        }
    }

    fun List<View>.isVisible(state: Boolean) {
        if (state) this.show()
        else this.gone()
    }

    fun List<View>.show() {
        forEach { it.visibility = View.VISIBLE }
    }

    fun List<View>.gone() {
        forEach { it.visibility = View.GONE }
    }

}
