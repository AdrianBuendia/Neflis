package com.neflis.neflis.core.data

import com.neflis.neflis.core.Constants
import com.neflis.neflis.core.datasource.login.LoginDatasource
import com.neflis.neflis.core.models.login.LoginResponse
import com.neflis.neflis.core.util.Resource
import com.neflis.neflis.core.util.Status

class LoginRepository(
    private val loginDatasource: LoginDatasource,
    private val tokenManager: TokenManager,
//    private val logoutDatasource: LogoutDatasource
) {

    // in-memory cache of the loggedInUser object
    var token: String? = null
        private set

    val isLoggedIn: Boolean
        get() = token != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        token = null
    }


    suspend fun logged(
        //loginRequest,
    ): Resource<LoginResponse> {
        // handle login
        val result = loginDatasource.login(
            //loginRequest
        )
        if (result.status == Status.SUCCESS) {
            val loginResponse = result.data
                setLoggedInUser(
                    Constants.TEST_API_TOKEN
                )
        }
        return result
    }

//    suspend fun logoutOutUser():Resource<LogoutResponse> {
//        // handle logout
//        val result = logoutDatasource.logout()
//        if (result.status == Status.SUCCESS ) {
//            tokenManager.clearAuthenticatedToken()
//            token = null
//        }
//        return result
//    }

    private fun setLoggedInUser(tokenValue: String) {
        this.token = tokenValue
        tokenManager.persistTokenData(
            tokenValue = tokenValue
        )
    }

}