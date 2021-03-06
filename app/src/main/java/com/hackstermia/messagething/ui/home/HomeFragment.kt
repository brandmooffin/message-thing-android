package com.hackstermia.messagething.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hackstermia.messagething.R
import com.hackstermia.messagething.models.Message
import com.hackstermia.messagething.settings.AppSettings
import com.hackstermia.messagething.tasks.GetMessageAsyncTask

class HomeFragment : Fragment() {

    private lateinit var mainProgressView: ProgressBar
    private lateinit var latestMessageTextView: TextView
    private lateinit var fromDeviceName: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.device_name)
        mainProgressView = root.findViewById(R.id.main_progress)
        latestMessageTextView = root.findViewById(R.id.latest_message)
        fromDeviceName = root.findViewById(R.id.from_device_name)
        textView.text = AppSettings(context!!).deviceName

        val checkMessageButton = root.findViewById<Button>(R.id.check_messages)
        checkMessageButton.setOnClickListener {
            showProgress(true)
            GetMessageAsyncTask(this).execute()
        }

        return root
    }

    fun updateLatestMessage(message:Message) {
        latestMessageTextView.text = message.text
        fromDeviceName.text = message.fromDevice
    }

    fun showProgress(show: Boolean) {
        val shortAnimTime =
            resources.getInteger(android.R.integer.config_shortAnimTime)
        latestMessageTextView.visibility = if (show) View.GONE else View.VISIBLE
        latestMessageTextView.animate().setDuration(shortAnimTime.toLong()).alpha(
            if (show) 0F else 1F
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                latestMessageTextView.visibility = if (show) View.GONE else View.VISIBLE
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