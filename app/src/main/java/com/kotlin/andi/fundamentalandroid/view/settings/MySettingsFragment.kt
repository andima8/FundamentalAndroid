package com.kotlin.andi.fundamentalandroid.view.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.kotlin.andi.fundamentalandroid.alarm.AlarmReceiver
import com.kotlin.andi.fundamentalandroid.R

class MySettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var switch: String
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var switchPreferenceCompat: SwitchPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        switch = resources.getString(R.string.key_switch)
        switchPreferenceCompat = findPreference<SwitchPreferenceCompat>(switch) as SwitchPreferenceCompat
        switchPreferenceCompat.onPreferenceChangeListener

        val sh = preferenceManager.sharedPreferences
        switchPreferenceCompat.isChecked = sh.getBoolean(switch, false)
        alarmReceiver = AlarmReceiver()

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == switch) {
            switchPreferenceCompat.isChecked = sharedPreferences.getBoolean(switch, false)
            val state =
                PreferenceManager.getDefaultSharedPreferences(context).getBoolean(switch, false)
            setReminder(state)
        }
    }

    private fun setReminder(state: Boolean) {
        if (state) {
            context?.let {
                val time = "09:00"
                alarmReceiver.setRepeatingAlarm(it, time)
        }
        } else {
            context?.let {
                alarmReceiver.cancelAlarm(it)
            }
        }
    }
}

