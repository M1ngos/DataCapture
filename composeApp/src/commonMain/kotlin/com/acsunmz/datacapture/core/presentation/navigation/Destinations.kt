package com.acsunmz.datacapture.core.presentation.navigation

import kotlinx.serialization.Serializable

class Destinations {
    @Serializable
    object Onboarding

    @Serializable
    object AppointmentIdScreen

    @Serializable
    object CaptureImageScreen

    @Serializable
    object CaptureFingerprintScreen

    @Serializable
    object CaptureSignatureScreen

//    @Serializable
//    data class AllTasks(val type: String)
//
//    @Serializable
//    data class AddTask(val taskId: Int = -1)
//
//    @Serializable
//    object Calendar
//
//    @Serializable
//    object Statistics
//
//    @Serializable
//    object AllStatistics
//
//    @Serializable
//    object Settings
//
//    @Serializable
//    data class TaskProgress(val taskId: Int)
}