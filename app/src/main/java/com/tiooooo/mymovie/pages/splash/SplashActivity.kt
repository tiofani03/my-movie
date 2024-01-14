package com.tiooooo.mymovie.pages.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.mymovie.databinding.ActivitySplashBinding
import com.tiooooo.mymovie.pages.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }


}
