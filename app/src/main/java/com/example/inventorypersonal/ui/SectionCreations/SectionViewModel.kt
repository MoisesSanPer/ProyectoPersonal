package com.example.inventorypersonal.ui.SectionCreations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorypersonal.data.Network.BaseResult
import com.example.inventorypersonal.data.model.Dependencies.Dependency
import com.example.inventorypersonal.data.model.Sections.Section
import com.example.inventorypersonal.data.repository.Sections.SectionRepository

import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SectionViewModel(): ViewModel()
{
    var state by  mutableStateOf(SectionState())
        private set

    fun validateName(name: String?) {
        //Limpia los errores de antes
        state =state.copy(nameErrorFormat =  "", isNameError = false)

        if( name.isNullOrBlank())
        {
            state = state.copy(
                isNameError = true,
                nameErrorFormat = "El nombre no puede estar vacío."
            )
            return
        }
        else{
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
        }
        else{
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
        state =state.copy(descriptionErrorFormat =  "", isdescriptionError = false)

        if( Description.isNullOrBlank())
        {
            state = state.copy(
                isdescriptionError = true,
                descriptionErrorFormat = "La descripción no puede estar vacía."
            )
            return
        }
        else{
            state = state.copy(
                isdescriptionError = false,
                descriptionErrorFormat = "",
                description = Description
            )
        }
    }
    fun onDependencySelected(selectedDependency: Dependency) {
        state = state.copy(dependencia = selectedDependency)
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

                val section = Section(
                    id = state.id,
                    name = state.name,
                    nameshort = state.shortName,
                    description = state.description,
                    Image = state.image,
                    fecha = state.fecha,
                    dependencia = state.dependencia!!
                )

                val sectionAddResponse = SectionRepository.addSection(section)

                when (sectionAddResponse) {
                    is BaseResult.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            isEmpty = sectionAddResponse.exception.message
                                ?: "Error al agregar seccion"
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