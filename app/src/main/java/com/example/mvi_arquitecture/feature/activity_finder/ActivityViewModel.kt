package com.example.mvi_arquitecture.features.activity_finder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_arquitecture.data.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// El ViewModel
class ActivityViewModel(
    private val api: ApiService = ApiService
) : ViewModel() {

    // MODEL (Estado)
    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState: StateFlow<ActivityUiState> = _uiState.asStateFlow()

    // INTENTS
    fun processIntent(intent: ActivityIntent) {
        when (intent) {
            is ActivityIntent.LoadNewActivity -> loadActivity()
            is ActivityIntent.ToggleShowDetails -> toggleDetails()
            is ActivityIntent.UpdateInputId -> updateInput(intent.text)
        }
    }
    private fun updateInput(text: String) {
        _uiState.update { it.copy(inputId = text) }
    }
    private fun loadActivity() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // --- CORRECCIÓN ---
            // 2. Leemos el ID desde el estado actual
            val idToLoad = _uiState.value.inputId

            val activity = api.getActivity(idToLoad)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    currentActivity = activity
                )
            }
        }
    }
    private fun toggleDetails() {
        _uiState.update { currentState ->
            currentState.copy(showDetails = !currentState.showDetails)
        }
    }
}