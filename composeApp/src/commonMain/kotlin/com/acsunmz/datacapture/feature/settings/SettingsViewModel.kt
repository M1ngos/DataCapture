package com.acsunmz.datacapture.feature.settings

import androidx.lifecycle.ViewModel
import com.acsunmz.datacapture.core.domain.repository.settings.SettingsRepository
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    val appTheme: StateFlow<Int?> = settingsRepository.getAppTheme()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null,
        )

    fun setAppTheme(appTheme: Int) {
        viewModelScope.launch {
            settingsRepository.saveAppTheme(appTheme)
        }
    }

}