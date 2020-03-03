package com.hackstermia.messagething.settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class AppSettings(context: Context) {
    private val deviceNameSetting = "DeviceName"
    var deviceName = ""
    private val appPreferences: SharedPreferences
    fun saveSettings() {
        val editor = appPreferences.edit()
        editor.putString(deviceNameSetting, deviceName)
        editor.apply()
    }

    private fun loadSettings() {
        deviceName = appPreferences.getString(deviceNameSetting, "") ?: ""
    }

    companion object {
        var AppSettingsName = "MessageThingAppSettings"
    }

    init {
        appPreferences = context.getSharedPreferences(
            AppSettingsName,
            Activity.MODE_PRIVATE
        )
        loadSettings()
    }
}