package com.example.inventorypersonal.ui.DependencyCreations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorypersonal.data.Network.BaseResult
import com.example.inventorypersonal.data.model.Dependencies.Dependency
import com.example.inventorypersonal.data.repository.Dependencies.DependencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class DependencyViewModel() : ViewModel() {
    var state by mutableStateOf(DependencyState())
        private set
    private val _dependencies = MutableStateFlow<List<Dependency>>(emptyList())

    init {
        loadDependencies()
    }

    fun validateName(name: String?) {
        //Limpia los errores de antes
        state = state.copy(nameErrorFormat = "", isNameError = false)

        if (name.isNullOrBlank()) {
            state = state.copy(
                isNameError = true,
                nameErrorFormat = "El nombre no puede estar vacío."
            )
            return
        } else {
            state = state.copy(
                isNameError = false,
                nameErrorFormat = "",
                name = name
            )
        }
    }

    fun validateNameShort(shortName: String?) {
        state = state.copy(shortName = shortName ?: "")

        if (shortName.isNullOrBlank() || shortName.length < 3) {
            state = state.copy(
                isShortNameError = true,
                shortNameErrorFormat = "El nombre corto debe tener al menos 3 caracteres."
            )
            return
        } else {
            // Si el nombre es válido, actualizar el estado
            state = state.copy(
                isShortNameError = false,
                shortNameErrorFormat = "",
                shortName = shortName
            )
        }
    }

    fun validatDescription(Description: String?) {
        //Limpia los errores de antes
        state = state.copy(descriptionErrorFormat = "", isdescriptionError = false)

        if (Description.isNullOrBlank()) {
            state = state.copy(
                isdescriptionError = true,
                descriptionErrorFormat = "La descripción no puede estar vacía."
            )
            return
        } else {
            state = state.copy(
                isdescriptionError = false,
                descriptionErrorFormat = "",
                description = Description
            )
        }
    }

    private fun loadDependencies() {
        viewModelScope.launch {
            // Recoge los datos emitidos por el Flow
            DependencyRepository.getData().collect { repoDependencies ->
                _dependencies.value = repoDependencies
            }
        }
    }

    fun onAddClick() {
        state = state.copy(isLoading = true)
        if (!validateFields()) {
            state = state.copy(
                isError = true,
                isEmpty = "Hay campos vacíos",
                isLoading = false
            )
            return
        }

        // Si no están vacíos, solo continuamos si no hay un estado de carga
        if (!state.isNameError && !state.isShortNameError && !state.isdescriptionError ) {

            viewModelScope.launch {

                val dependencyaddResponse = DependencyRepository.addDependency(
                    Dependency(
                        state.id,
                        state.name,
                        state.shortName,
                        state.description,
                        state.image,
                        state.fecha
                    )
                )

                when (dependencyaddResponse) {
                    is BaseResult.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            isEmpty = dependencyaddResponse.exception.message
                                ?: "Error al agregar dependencia"
                        )
                    }

                    is BaseResult.Sucess -> {
                        // En caso de éxito, cambiar el estado y mostrar el éxito
                        state = state.copy(
                            name = "",
                            shortName = "",
                            description = "",
                            fecha = LocalDateTime.now(),
                            isNameError = false,
                            success = true,
                            shortNameErrorFormat = "",
                            nameErrorFormat = "",
                            isdescriptionError =false,
                            descriptionErrorFormat = "",
                            image = "",
                            isEmpty = "",
                            isError = false,
                            isShortNameError = false,
                            isLoading = false
                        )

                    }

                    else -> {}
                }
            }
        }

    }
    private fun validateFields(): Boolean {
        var isValid = true

        if (state.name.isEmpty()) {
            state = state.copy(
                isNameError = true,
                nameErrorFormat = "El nombre no puede estar vacío."
            )
            isValid = false
        }
        if (state.shortName.isEmpty()) {
            state = state.copy(
                isShortNameError = true,
                shortNameErrorFormat = "El nombre corto no puede estar vacío."
            )
            isValid = false
        }
        if (state.description.isEmpty()) {
            state = state.copy(
                isdescriptionError = true,
                descriptionErrorFormat = "La descripción no puede estar vacía."
            )
            isValid = false
        }
        return isValid
    }


}




