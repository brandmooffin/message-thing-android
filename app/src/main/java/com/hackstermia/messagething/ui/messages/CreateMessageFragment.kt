package com.hackstermia.messagething.ui.messages

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.hackstermia.messagething.R
import com.hackstermia.messagething.models.Message
import com.hackstermia.messagething.settings.AppSettings
import com.hackstermia.messagething.tasks.AddMessageAsyncTask

class CreateMessageFragment : Fragment() {

    private lateinit var mainProgressView: ProgressBar
    private lateinit var toDeviceNameEditText: EditText
    private lateinit var messageEditText: EditText
    private lateinit var sendMessageButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create_message, container, false)
        mainProgressView = root.findViewById(R.id.main_progress)
        toDeviceNameEditText = root.findViewById(R.id.device_name)
        messageEditText = root.findViewById(R.id.message)
        sendMessageButton = root.findViewById(R.id.send_message)
        sendMessageButton.setOnClickListener {
                showProgress(true)
                AddMessageAsyncTask(this, Message().apply {
                    fromDevice = AppSettings(context!!).deviceName
                    text = messageEditText.text.toString()
                    toDevice = toDeviceNameEditText.text.toString()
                }).execute()
            }
        return root
    }

    fun showProgress(show: Boolean) {
        val shortAnimTime =
            resources.getInteger(android.R.integer.config_shortAnimTime)
        sendMessageButton.visibility = if (show) View.GONE else View.VISIBLE
        sendMessageButton.animate().setDuration(shortAnimTime.toLong()).alpha(
            if (show) 0F else 1F
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                sendMessageButton.visibility = if (show) View.GONE else View.VISIBLE
            }
        })
        mainProgressView.visibility = if (show) View.VISIBLE else View.GONE
        mainProgressView.animate().setDuration(shortAnimTime.toLong()).alpha(
            if (show) 1F else 0F
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mainProgressView.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }
}