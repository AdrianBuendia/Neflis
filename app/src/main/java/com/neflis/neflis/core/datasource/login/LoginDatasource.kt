package com.neflis.neflis.core.datasource.login

import com.neflis.neflis.core.models.login.LoginResponse
import com.neflis.neflis.core.util.Resource

class LoginDatasource {
    suspend fun login(
        //h: String,
        //loginRequest: LoginRequest
    ): Resource<LoginResponse> {
        return Resource.success(LoginResponse(true))
    }
}