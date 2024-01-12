package com.tiooooo.core.common

import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import android.telephony.TelephonyManager

class ContextProvider(
    private val context: Context,
) {

    fun getContext(): Context = context

    fun getString(id: Int): String = context.getString(id)

    fun getPackageName(): String = context.packageName

    fun getPackageManager(): PackageManager = context.packageManager

    fun getAndroidId(): String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    fun getTelephonyManager(): TelephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

}
