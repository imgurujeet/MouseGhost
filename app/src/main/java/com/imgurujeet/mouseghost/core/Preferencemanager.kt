package com.imgurujeet.mouseghost.core

import android.content.Context

object PrefKeys {
    const val PATTERN_TYPE = "pattern_type" // "list" or "grid"
    const val VIBRATION_ENABLED = "vibration_enabled"
    const val VIBRATION_DURATION = "vibration_duration"
    const val BRIGHTNESS_PAUSE = "brightness_pause"
}

class Prefs(context: Context) {
    private val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    var patternType: String
        get() = prefs.getString(PrefKeys.PATTERN_TYPE, "list") ?: "list"
        set(value) = prefs.edit().putString(PrefKeys.PATTERN_TYPE, value).apply()

    var vibrationEnabled: Boolean
        get() = prefs.getBoolean(PrefKeys.VIBRATION_ENABLED, false)
        set(value) = prefs.edit().putBoolean(PrefKeys.VIBRATION_ENABLED, value).apply()

    var vibrationDuration: Long
        get() = prefs.getLong(PrefKeys.VIBRATION_DURATION, 500L)
        set(value) = prefs.edit().putLong(PrefKeys.VIBRATION_DURATION, value).apply()

    var brightnessPause: Long
        get() = prefs.getLong(PrefKeys.BRIGHTNESS_PAUSE, 3000L)
        set(value) = prefs.edit().putLong(PrefKeys.BRIGHTNESS_PAUSE, value).apply()
}
