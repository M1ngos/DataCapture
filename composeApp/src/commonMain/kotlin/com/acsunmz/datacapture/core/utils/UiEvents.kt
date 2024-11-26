
package com.acsunmz.datacapture.core.utils

sealed class UiEvents {
    data class ShowSnackbar(val message: String) : UiEvents()
    data object Navigation : UiEvents()
    data object NavigateBack : UiEvents()
}
