package com.hackstermia.messagething.tasks

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.hackstermia.messagething.constants.AppConstants
import com.hackstermia.messagething.models.Message
import com.hackstermia.messagething.ui.messages.CreateMessageFragment
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class AddMessageAsyncTask(
    private val createMessageFragment: CreateMessageFragment,
    private val message: Message
) :
    AsyncTask<Void?, Void?, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean? {
        try {
            val url =
                URL(AppConstants.ApiUrl + "/api/messaging")
            val connection =
                url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            val gson = Gson()
            val wr =
                DataOutputStream(connection.outputStream)
            val parsed: String = gson.toJson(message, Message::class.java)
            wr.writeBytes(parsed)
            wr.flush()
            wr.close()
            val `in` =
                BufferedReader(InputStreamReader(connection.inputStream))
            val sb = StringBuilder()
            var line: String?
            while (`in`.readLine().also { line = it } != null) {
                sb.append(line)
            }
            `in`.close()
            Log.d("MESSAGE-THING", sb.toString())
            return true
        } catch (e: Exception) {
            println("\nError while calling service")
            println(e)
        }
        return false
    }

    override fun onPostExecute(success: Boolean) {
        createMessageFragment.showProgress(false)
        if (success) {
            val toast = Toast.makeText(createMessageFragment.context!!, "Message sent!", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onCancelled() {
        createMessageFragment.showProgress(false)
    }

}