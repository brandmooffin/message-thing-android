package com.hackstermia.messagething.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hackstermia.messagething.R
import com.hackstermia.messagething.settings.AppSettings

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val appSettings = AppSettings(context!!)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val deviceNameEditText = root.findViewById<EditText>(R.id.device_name)
        deviceNameEditText.setText(appSettings.deviceName, TextView.BufferType.EDITABLE)
        val saveButton = root.findViewById<Button>(R.id.save_changes)
        saveButton.setOnClickListener {
            val toast = Toast.makeText(context!!, "Changes saved", Toast.LENGTH_SHORT)
            appSettings.deviceName = deviceNameEditText.text.toString()
            appSettings.saveSettings()
            toast.show()
        }
        return root
    }
}