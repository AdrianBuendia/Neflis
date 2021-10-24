package com.neflis.neflis.cases.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neflis.neflis.core.data.LoginRepository
import com.neflis.neflis.core.data.TokenManager
import com.neflis.neflis.core.models.login.LoginResponse
import com.neflis.neflis.core.util.Event
import com.neflis.neflis.core.util.Resource
import com.neflis.neflis.core.util.Status
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
) : ViewModel() {
    private val _loginResource = MutableLiveData<Event<Resource<LoginResponse>>>()
    val loginResource: LiveData<Event<Resource<LoginResponse>>> = _loginResource

    fun login() {
        _loginResource.value = Event(Resource(Status.LOADING, null))
        viewModelScope.launch {

            val result = loginRepository.logged(
            )
            _loginResource.value = Event(result)
        }
    }

    fun isLoggin() = loginRepository.isLoggedIn

    fun getToken() = loginRepository.token


}