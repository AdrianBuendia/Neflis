package com.neflis.neflis.cases.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neflis.neflis.core.data.LoginRepository
import com.neflis.neflis.core.data.TokenManager
import com.neflis.neflis.core.datasource.login.LoginDatasource

class LoginViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                LoginRepository(
                    LoginDatasource(),
                    TokenManager(context)
                ),
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}