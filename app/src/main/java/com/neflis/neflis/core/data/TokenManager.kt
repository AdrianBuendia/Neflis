package com.neflis.neflis.core.data

import android.content.Context
import com.neflis.neflis.core.util.PersistData

private const val USER_AUTHENTICATED_KEY = "isAuthenticated"
private const val USER_ACCESS_TOKEN = "accessToken"
private const val USER_NAME = "username"

class TokenManager(
    private val context: Context?
) {
    private val persistData = PersistData(context)

    fun persistTokenData(tokenValue: String, username: String? = null) {
        persistData.setPreferenceBooleanByKey(USER_AUTHENTICATED_KEY, true)
        persistData.setPreferenceStringByKey(USER_ACCESS_TOKEN, tokenValue)
        //persistData.setPreferenceStringByKey(USER_NAME, username)
    }

    fun isTokenAuthenticated(): Boolean {
        return persistData.getPreferenceBooleanByKey(USER_AUTHENTICATED_KEY)
    }


    fun getTokenAuthenticated(): String {
        return persistData.getPreferenceStringByKey(USER_ACCESS_TOKEN)
    }

//    fun getUserName(): String? {
//        return persistData.getPreferenceStringByKey(USER_NAME)
//    }

    fun clearAuthenticatedToken() {
        //val username = persistData.getPreferenceStringByKey(USER_NAME)
        persistData.clearPreferences()
        //persistData.setPreferenceStringByKey(USER_NAME, username)
    }
}