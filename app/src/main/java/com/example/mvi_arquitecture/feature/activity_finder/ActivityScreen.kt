package com.example.mvi_arquitecture.features.activity_finder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// V: VIEW
@Composable
fun ActivityScreen(
    modifier: Modifier = Modifier,
    viewModel: ActivityViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val activity = state.currentActivity

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            val displayText = when {
                activity == null -> "Ingresa un ID (1-100) y presiona 'Cargar'"
                state.showDetails -> "Actividad: ${activity.title} (User: ${activity.userId})"
                else -> activity.title
            }
            Text(
                text = displayText,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = state.inputId,
            onValueChange = { newText ->
                viewModel.processIntent(ActivityIntent.UpdateInputId(newText))
            },
            label = { Text("Post ID (ej: 1, 2, 3...)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.processIntent(ActivityIntent.LoadNewActivity) },
            enabled = !state.isLoading
        ) {
            Text("Use Case 1: Cargar Post por ID (API)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.processIntent(ActivityIntent.ToggleShowDetails) },
            enabled = !state.isLoading && state.currentActivity != null
        ) {
            Text("Use Case 2: ${if (state.showDetails) "Ocultar" else "Mostrar"} Detalles (UI)")
        }
    }
}