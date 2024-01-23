package com.tuna.cinergy.ui.movieinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.business.domain.utils.dataStore.BasePreferencesManager
import com.tuna.cinergy.business.domain.utils.exhaustive
import com.tuna.cinergy.business.repository.CineryInfoRepository
import com.tuna.cinergy.datasource.network.models.escaperoom.EscapeRoomResponseClass
import com.tuna.cinergy.datasource.network.models.movieinfo.MovieInfoResponseClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val cinergyInfoRepository: CineryInfoRepository,
    private val basePreferencesManager: BasePreferencesManager
) : ViewModel() {

    private val _myRoomDataSTate: MutableLiveData<DataState<MovieInfoResponseClass>> = MutableLiveData()
    val myRoomDataSTate: LiveData<DataState<MovieInfoResponseClass>> get() = _myRoomDataSTate

    fun movieInfoRequest(userId:String,deviceId: String,deviceType: String,locationtype: String,movieId: String) = viewModelScope.launch {
        setStateEvent(EventMovies.EscapeRoom(userId,deviceId,deviceType,locationtype,movieId))
    }

    private fun setStateEvent(state: EventMovies) {
        viewModelScope.launch(Dispatchers.IO) {
            when (state) {
                is EventMovies.EscapeRoom -> {
                    cinergyInfoRepository.movieInfo(
                        state.userId,
                        basePreferencesManager.getAccessToken().first(),
                        state.deviceId,
                        state.deviceType,
                        state.locationId,
                        state.movieId,
                    ).onEach {
                        _myRoomDataSTate.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }.exhaustive
    }
}

sealed class EventMovies {
    data class EscapeRoom(val userId:String,val deviceId: String,val deviceType: String,val locationId: String,val movieId: String) : EventMovies()
}
