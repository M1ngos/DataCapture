
package com.acsunmz.datacapture.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acsunmz.datacapture.core.domain.repository.settings.SettingsRepository
import com.acsunmz.datacapture.core.utils.UiEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _eventsFlow = Channel<UiEvents>(Channel.UNLIMITED)
    val eventsFlow = _eventsFlow.receiveAsFlow()


    private val _appointmentId = MutableStateFlow(0)
    val appointmentId = _appointmentId.asStateFlow()
    fun setAppointmentId(appointmentId: Int) {
        _appointmentId.value = appointmentId
    }

    fun saveAppointmentId() {
        viewModelScope.launch {
            settingsRepository.saveAppointmentId(appointmentId.value)
//            settingsRepository.toggleReminder(1)
            _eventsFlow.send(UiEvents.Navigation)
        }
    }

    val typeWriterTextParts = listOf(
        "to collect biometric data",
        "to process the data",
        "to temporarily store said data",
        "to get praise from project manager",
    )
}
