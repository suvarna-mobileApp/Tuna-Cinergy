package com.tuna.cinergy.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.business.domain.utils.dataStore.BasePreferencesManager
import com.tuna.cinergy.business.domain.utils.exhaustive
import com.tuna.cinergy.business.repository.CineryInfoRepository
import com.tuna.cinergy.datasource.network.models.escaperoom.EscapeRoomResponseClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val cinergyInfoRepository: CineryInfoRepository,
    private val basePreferencesManager: BasePreferencesManager
) : ViewModel() {

    private val _myRoomDataSTate: MutableLiveData<DataState<EscapeRoomResponseClass>> = MutableLiveData()
    val myRoomDataSTate: LiveData<DataState<EscapeRoomResponseClass>> get() = _myRoomDataSTate

    fun escapeRoomRequest(userId:String,deviceId: String,memberId: String,deviceType: String,locationtype: String) = viewModelScope.launch {
        setStateEvent(EventMovies.EscapeRoom(userId,deviceId,memberId,deviceType,locationtype))
    }

    private fun setStateEvent(state: EventMovies) {
        viewModelScope.launch(Dispatchers.IO) {
            when (state) {
                is EventMovies.EscapeRoom -> {
                    cinergyInfoRepository.roomMovies(
                        state.userId,
                        basePreferencesManager.getAccessToken().first(),
                        state.deviceId,
                        state.memberId,
                        state.deviceType,
                        state.locationtype,
                    ).onEach {
                        _myRoomDataSTate.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }.exhaustive
    }
}

sealed class EventMovies {
    data class EscapeRoom(val userId:String,val deviceId: String,val memberId: String,val deviceType: String,val locationtype: String) : EventMovies()
}
