@file:Suppress("UNCHECKED_CAST")

package com.webaddicted.data.sharedpref

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import java.util.concurrent.ConcurrentHashMap
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class PreferenceUtils {

    companion object {
        private const val PREFS_NAME = "local_pref"
        private const val GLOBAL_PREFS_NAME = "global_pref"
        private var mLocalPreferences: SharedPreferences? = null
        private var mGlobalPreferences: SharedPreferences? = null
        fun getInstance(context: Context) {
            mLocalPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            mGlobalPreferences = context.getSharedPreferences(GLOBAL_PREFS_NAME, Context.MODE_PRIVATE)
        }
    }
//    {START LOCAL PREFERENCE  SAVE}

    /**
     * Get data from mPreferenceUtil with key {key} & of type {obj}
     *
     * @param key          preference key
     * @param defautlValue default key for preference
     * @param <T>
     * @return
    </T> */
    fun <T> getPreference(key: String, defautlValue: T): T? {
        try {
            when (defautlValue) {
                is String -> return mLocalPreferences?.getString(key, defautlValue as String) as T
                is Int -> return mLocalPreferences?.getInt(key, defautlValue as Int) as T
                is Boolean -> return mLocalPreferences?.getBoolean(key, defautlValue as Boolean) as T
                is Float -> return mLocalPreferences?.getFloat(key, defautlValue as Float) as T
                is Long -> return mLocalPreferences?.getLong(key, defautlValue as Long) as T
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * Save data to mPreferenceUtil with key {key} & of type {obj}
     *
     * @param key
     * @param value
     * @param <T>
     * @return
    </T> */
    fun <T> setPreference(key: String, value: T) {
        try {
            val editor = mLocalPreferences?.edit()
            when (value) {
                is String -> editor?.putString(key, value as String)
                is Int -> editor?.putInt(key, value as Int)
                is Boolean -> editor?.putBoolean(key, value as Boolean)
                is Float -> editor?.putFloat(key, value as Float)
                is Long -> editor?.putLong(key, value as Long)
            }
            editor?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * clear key preference when required
     */
    fun removeKey(key: String) {
        if (mLocalPreferences != null)
            mLocalPreferences?.edit()?.remove(key)?.apply()
    }

    /**
     * clear preference when required
     */
    fun clearAllPreferences() {
        if (mLocalPreferences != null)
            mLocalPreferences?.edit()?.clear()?.apply()
    }

    /**
     * Clear all Preference accept keyToBeSaved
     *
     * @param keyToBeSaved
     */
    fun clearAllPreferences(keyToBeSaved: Array<String>) {
        if (mLocalPreferences != null) {
            val map = ConcurrentHashMap(mLocalPreferences?.all)
            for (stringObjectEntry in map.keys) {
                if (!listOf(*keyToBeSaved).contains(stringObjectEntry)) {
                    val editor = mLocalPreferences?.edit()
                    editor?.remove(stringObjectEntry)?.apply()
                }
            }
        }
    }
//    {END LOCAL PREFERENCE  SAVE}

//    {START LOCAL PREFERENCE  SAVE}

    /**
     * Get data from mPreferenceUtil with key {key} & of type {obj}
     *
     * @param key          preference key
     * @param defautlValue default key for preference
     * @param <T>
     * @return
    </T> */
    fun <T> getGlobalPreference(key: String, defautlValue: T): T? {
        try {
            when (defautlValue) {
                is String -> return mGlobalPreferences?.getString(key, defautlValue as String) as T
                is Int -> return mGlobalPreferences?.getInt(key, defautlValue as Int) as T
                is Boolean -> return mGlobalPreferences?.getBoolean(key, defautlValue as Boolean) as T
                is Float -> return mGlobalPreferences?.getFloat(key, defautlValue as Float) as T
                is Long -> return mGlobalPreferences?.getLong(key, defautlValue as Long) as T
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * Save data to mPreferenceUtil with key {key} & of type {obj}
     *
     * @param key
     * @param value
     * @param <T>
     * @return
    </T> */
    fun <T> setGlobalPreference(key: String, value: T) {
        try {
            val editor = mGlobalPreferences?.edit()
            when (value) {
                is String -> editor?.putString(key, value as String)
                is Int -> editor?.putInt(key, value as Int)
                is Boolean -> editor?.putBoolean(key, value as Boolean)
                is Float -> editor?.putFloat(key, value as Float)
                is Long -> editor?.putLong(key, value as Long)
            }
            editor?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * clear key preference when required
     */
    fun removeGlobalKey(key: String) {
        if (mGlobalPreferences != null)
            mGlobalPreferences?.edit()?.remove(key)?.apply()
    }

    /**
     * clear preference when required
     */
    fun clearAllGlobalPreferences() {
        if (mGlobalPreferences != null)
            mGlobalPreferences?.edit()?.clear()?.apply()
    }

    /**
     * Clear all Preference accept keyToBeSaved
     *
     * @param keyToBeSaved
     */
    fun clearAllGlobalPreferences(keyToBeSaved: Array<String>) {
        if (mGlobalPreferences != null) {
            val map = ConcurrentHashMap(mGlobalPreferences?.all)
            for (stringObjectEntry in map.keys) {
                if (!listOf(*keyToBeSaved).contains(stringObjectEntry)) {
                    val editor = mGlobalPreferences?.edit()
                    editor?.remove(stringObjectEntry)?.apply()
                }
            }
        }
    }
//    {END GLOBAL PREFERENCE  SAVE}

}