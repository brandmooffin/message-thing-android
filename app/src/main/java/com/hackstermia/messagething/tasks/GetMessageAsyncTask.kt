package com.hackstermia.messagething.tasks

import android.os.AsyncTask
import com.google.gson.Gson
import com.hackstermia.messagething.constants.AppConstants
import com.hackstermia.messagething.models.Message
import com.hackstermia.messagething.settings.AppSettings
import com.hackstermia.messagething.ui.home.HomeFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class GetMessageAsyncTask(private val homeFragment: HomeFragment) :
    AsyncTask<Void?, Void?, Boolean>() {
    private var message = Message()
    private val appSettings = AppSettings(homeFragment.context!!)
    override fun doInBackground(vararg params: Void?): Boolean? {
        try {
            val gson = Gson()

            val url =
                URL(AppConstants.ApiUrl + "/api/messaging/" + appSettings.deviceName)
            val connection = url.openConnection()
            connection.setRequestProperty("Content-Type", "application/json")
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val `in` =
                BufferedReader(InputStreamReader(connection.getInputStream()))
            val sb = StringBuilder()
            var line: String?
            while (`in`.readLine().also { line = it } != null) {
                sb.append(line)
            }
            `in`.close()
            message = gson.fromJson<Message>(
                sb.toString(),
                Message::class.java
            )

            return true
        } catch (e: Exception) {
            println("\nError while calling service")
            println(e)
        }
        return false
    }

    override fun onPostExecute(success: Boolean) {
        homeFragment.showProgress(false)
        if (success) {
            homeFragment.updateLatestMessage(message)
        }
    }

    override fun onCancelled() {
        homeFragment.showProgress(false)
    }

}