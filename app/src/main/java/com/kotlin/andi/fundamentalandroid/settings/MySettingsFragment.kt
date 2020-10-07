package com.kotlin.andi.fundamentalandroid.settings

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.alarm.AlarmReceiver
import java.util.*


@Suppress("DEPRECATION")
class MySettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var switch: String
    private lateinit var list: String
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var switchPreferenceCompat: SwitchPreferenceCompat
    private lateinit var listPreference: ListPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        switch = resources.getString(R.string.key_switch)
        list = resources.getString(R.string.list)

        switchPreferenceCompat = findPreference<SwitchPreferenceCompat>(switch) as SwitchPreferenceCompat
        switchPreferenceCompat.onPreferenceChangeListener

        listPreference = findPreference<ListPreference>(list) as ListPreference

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
        if (key == list){
            val listLanguage = resources.getStringArray(R.array.Language)
            when(sharedPreferences.getString(list, listLanguage[0])) {
                listLanguage[0] -> appLanguage("en")
                listLanguage[1] -> appLanguage("id")
            }
        }

    }

    private fun appLanguage(localeCode: String) {
        val resources: Resources = resources
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val configuration: Configuration = resources.configuration
        configuration.setLocale(Locale(localeCode.toLowerCase(Locale.getDefault())))
        resources.updateConfiguration(configuration, displayMetrics)
        configuration.locale = Locale(localeCode.toLowerCase(Locale.getDefault()))
        resources.updateConfiguration(configuration, displayMetrics)
        restartActivity()
    }

    private fun restartActivity() {
        val intent = Intent(activity, SettingActivity::class.java)
        activity?.finish()
        activity?.overridePendingTransition(0, 0)
        startActivity(intent)
    }

    private fun setReminder(state: Boolean) {
        if (state) {
            context?.let {
                val time = resources.getString(R.string.time)
                alarmReceiver.setRepeatingAlarm(it, time)
        }
        } else {
            context?.let {
                alarmReceiver.cancelAlarm(it)
            }
        }
    }
}

