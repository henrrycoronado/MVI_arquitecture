package com.example.mvi_arquitecture.features.activity_finder
import com.example.mvi_arquitecture.data.network.dto.PostsUser
// M: MODEL (El Estado Único de la UI)
data class ActivityUiState(
    val isLoading: Boolean = false,
    val currentActivity: PostsUser? = null,
    val showDetails: Boolean = false,
    val inputId: String = "1"
)

// I: INTENT (Acciones del Usuario)
sealed interface ActivityIntent {
    data object LoadNewActivity : ActivityIntent
    data object ToggleShowDetails : ActivityIntent
    data class UpdateInputId(val text: String) : ActivityIntent
}