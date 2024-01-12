package com.tiooooo.core.base

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding

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
}
