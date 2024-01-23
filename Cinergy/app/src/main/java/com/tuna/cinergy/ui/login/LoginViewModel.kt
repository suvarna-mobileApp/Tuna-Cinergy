package com.tuna.cinergy.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.business.domain.utils.dataStore.BasePreferencesManager
import com.tuna.cinergy.business.domain.utils.exhaustive
import com.tuna.cinergy.business.repository.CineryInfoRepository
import com.tuna.cinergy.datasource.network.models.guestlogin.GuestLoginResponseClass
import com.tuna.cinergy.datasource.network.models.login.LoginResponseClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val cinergyInfoRepository: CineryInfoRepository,
    private val basePreferencesManager: BasePreferencesManager
) : ViewModel() {

    private val _myLoginDataSTate: MutableLiveData<DataState<LoginResponseClass>> = MutableLiveData()
    val myLoginDataSTate: LiveData<DataState<LoginResponseClass>> get() = _myLoginDataSTate

    private val _myGuestLoginDataSTate: MutableLiveData<DataState<GuestLoginResponseClass>> = MutableLiveData()
    val myGuestLoginDataSTate: LiveData<DataState<GuestLoginResponseClass>> get() = _myGuestLoginDataSTate

    fun loginRequest(secretKey: String,
                     deviceType: String,
                     deviceId: String,
                     pushToken: String) = viewModelScope.launch {
        setStateEvent(EventLogin.Login(secretKey,deviceType,deviceId,pushToken))
    }

    fun guestLoginRequest(deviceId: String,
                     deviceType: String,
                     loginType: String) = viewModelScope.launch {
        setStateEvent(EventLogin.GuestLogin(deviceId,deviceType,loginType))
    }

    fun updateAccessToken(token : String) {
        setStateEvent(EventLogin.ResetToken(token))
    }

    private fun setStateEvent(state: EventLogin) {
        viewModelScope.launch(Dispatchers.IO) {
            when (state) {
                is EventLogin.Login -> {
                    cinergyInfoRepository.guestToken(
                        state.secretKey,
                        state.deviceType,
                        state.deviceId,
                        state.pushToken
                    ).onEach {
                        _myLoginDataSTate.value = it
                    }.launchIn(viewModelScope)
                }
                is EventLogin.ResetToken -> {
                    basePreferencesManager.updateAccessToken(state.token)
                }
                is EventLogin.GuestLogin -> {
                    cinergyInfoRepository.guestLogin(
                        basePreferencesManager.getAccessToken().first(),
                        state.deviceId,
                        state.deviceType,
                        state.loginType
                    ).onEach {
                        _myGuestLoginDataSTate.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }.exhaustive
    }
}

sealed class EventLogin {
    data class Login(val secretKey: String,
                     val deviceType: String,
                     val deviceId: String,
                     val pushToken: String) : EventLogin()

    data class GuestLogin(val deviceType: String,
                     val deviceId: String,
                     val loginType: String) : EventLogin()

    data class ResetToken(val token: String): EventLogin()
}
