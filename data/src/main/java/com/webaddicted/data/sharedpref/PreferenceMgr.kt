package com.webaddicted.data.sharedPref

import com.webaddicted.data.sharedpref.PreferenceConstant
import com.webaddicted.model.sharedpref.PreferenceBean
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class PreferenceMgr constructor(var preferenceUtils: PreferenceUtils) {
    /**
     * set user session info
     */
    fun setUserInfo(preferenceBean: PreferenceBean) {
        preferenceUtils.setPreference(PreferenceConstant.PREF_USER_NAME, preferenceBean.name)
        preferenceUtils.setPreference(PreferenceConstant.PREF_USER_AGE, preferenceBean.age)
        preferenceUtils.setPreference(PreferenceConstant.PREF_USER_GENDER, preferenceBean.gender)
        preferenceUtils.setPreference(PreferenceConstant.PREF_USER_WEIGHT, preferenceBean.weight)
        preferenceUtils.setPreference(PreferenceConstant.PREF_USER_IS_MARRIED, preferenceBean.isMarried)
    }

    /**
     * get user session info
     */
    fun getUserInfo(): PreferenceBean {
        val preferenceBean = PreferenceBean()
        preferenceBean.name = preferenceUtils.getPreference(PreferenceConstant.PREF_USER_NAME, "")!!
        preferenceBean.gender = preferenceUtils.getPreference(PreferenceConstant.PREF_USER_GENDER, "")!!
        preferenceBean.age = preferenceUtils.getPreference(PreferenceConstant.PREF_USER_AGE, 0)!!
        preferenceBean.weight = preferenceUtils.getPreference(PreferenceConstant.PREF_USER_WEIGHT, 0L)!!
        preferenceBean.isMarried = preferenceUtils.getPreference(PreferenceConstant.PREF_USER_IS_MARRIED, false)!!
        return preferenceBean
    }
//
//    /**
//     * set user session info
//     */
//    fun setLanguage(languageBean: LanguageBean) {
//        preferenceUtils?.setPreference(PreferenceConstant.PREF_LANGUAGE_ID, languageBean.id)
//        preferenceUtils?.setPreference(PreferenceConstant.PREF_LANGUAGE_NAME, languageBean.languageName)
//        preferenceUtils?.setPreference(PreferenceConstant.PREF_LANGUAGE_CODE, languageBean.languageCode)
//    }
//
//    /**
//     * get user session info
//     */
//    fun getLanguageInfo(): LanguageBean {
//        return LanguageBean().apply {
//            id = preferenceUtils.getPreference(PreferenceConstant.PREF_LANGUAGE_ID, "")!!
//            languageName = preferenceUtils.getPreference(PreferenceConstant.PREF_LANGUAGE_NAME, "")!!
//            languageCode = preferenceUtils.getPreference(PreferenceConstant.PREF_LANGUAGE_CODE, "")!!
//        }
//    }


    fun removeKey(removeKey: String) {
        preferenceUtils.removeKey(removeKey)
    }
    fun clearPref() {
        preferenceUtils.clearAllPreferences()
    }
}