package com.example.inventorypersonal.ui.SectionCreations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventorypersonal.data.model.Dependencies.Dependency
import com.example.inventorypersonal.data.repository.Dependencies.DependencyRepository
import com.example.inventorypersonal.ui.Common.Loading

data class SectionsAddEvents(
    val  onNameChange: (String) -> Unit={},
    val  onShortNameChange: (String) -> Unit={},
    val   onDescriptionChange: (String) -> Unit={},
    val onDependenciaChange: (Dependency) -> Unit = {},
    val onAddClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun SecctionAddScreenPreview() {
    SectionScreen(
        modifier = Modifier,
        viewModel = SectionViewModel(),
        goToSectionList={},
        )
}


@Composable
fun SectionScreen(modifier: Modifier = Modifier,
                  goToSectionList: () -> Unit,
                  viewModel: SectionViewModel
) {
    // Utilizamos el estado del ViewModel en lugar de un estado local
    SectionAddViewModel(
        modifier = modifier,
        goToSectionList=goToSectionList,
        state = viewModel.state, // El estado del ViewModel
        events = SectionsAddEvents(
            onNameChange = { name -> viewModel.validateName(name) },
            onShortNameChange = { shortName -> viewModel.validateNameShort(shortName) },
            onDescriptionChange = {  description-> viewModel.validatDescription(description) },
            onDependenciaChange = { dependencia -> viewModel.onDependencySelected(dependencia) },
            onAddClick = viewModel::onAddClick
        )
    )
}

@Composable
fun SectionAddViewModel(
    modifier: Modifier = Modifier,
    goToSectionList: () -> Unit,
    state: SectionState,
    events: SectionsAddEvents
) {
    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        when {
            state.isLoading -> {
                state.isLoading =false
                Loading(modifier = modifier)
            }
            state.success ->{
                state.success =false
                goToSectionList()
            }
            else -> DependencyAddScreenContent(
                modifier = Modifier.padding(paddingValues),
                goToSectionList=goToSectionList,
                state = state,
                events = events
            )
        }
    }
}
@Composable
fun DependencyAddScreenContent(
    modifier: Modifier = Modifier,
    goToSectionList: () -> Unit,
    state: SectionState,
    events: SectionsAddEvents,
){
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "AÑADIR SECCION",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 23.dp)
            )
            TextField(
                value = state.name,
                onValueChange = events.onNameChange,
                label = { Text("Nombre") },
                isError = state.isNameError,
                singleLine = true,
                //Sacado de Internet he preguntado para saber tonalidades profesionales
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F1F1),
                    focusedTextColor = Color.Black,
                    focusedLabelColor = Color(0xFF6200EE)
                ),
                supportingText = {
                    if (state.isNameError) {
                        Text(text = state.nameErrorFormat!!, color = Color.Red)
                    }
                }
            )



            Spacer(modifier.size(10.dp))

            TextField(
                value = state.shortName,
                onValueChange =  events.onShortNameChange ,
                label = { Text("Nombre Corto") },
                singleLine = true,
                isError = state.isShortNameError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F1F1),
                    focusedTextColor = Color.Black,
                    focusedLabelColor = Color(0xFF6200EE)
                ),
                supportingText = {
                    if (state.isShortNameError) {
                        Text(text = state.shortNameErrorFormat!!, color = Color.Red)
                    }
                }
            )
            Spacer(modifier.size(10.dp))
            SimpleExposedDropdownMenu{
                selectedDependency ->
                events.onDependenciaChange(selectedDependency)
            }
            Box(
                modifier = modifier.size(270.dp)
            )
            {
                OutlinedTextField(
                    value = state.description,
                    onValueChange =  events.onDescriptionChange ,
                    isError = state.isdescriptionError,
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxSize(),
                    maxLines = 3,
                    supportingText = {
                        if (state.isdescriptionError) {
                            Text(text = state.descriptionErrorFormat!!, color = Color.Red)
                        }
                    }
                )
            }
            Spacer(modifier.size(8.dp))
            Button  (
                onClick = {
                    events.onAddClick()
                    // Aquí puedes agregar la lógica para agregar la dependencia si no hay errores

                }
            ) {
                Text(
                    text = "Añadir".uppercase(),
                    style = TextStyle(color = Color.White)
                )
            }
        }


    }
}@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleExposedDropdownMenu(
    selectedDependency: (Dependency) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<Dependency?>(null) }
    val dependencies = DependencyRepository.dataDependency

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedOption?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Selecciona una opción") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            dependencies.forEach { dependency ->
                Text(
                    text = dependency.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedOption = dependency
                            selectedDependency(dependency)
                            expanded = false
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}



/*
@Composable
@Preview
fun SeccionesScreenAñadirDemo(modifier: Modifier = Modifier){
    SeccionesContentAñadirDemo(modifier)
}

@Composable
fun SeccionesContentAñadirDemo(modifier: Modifier)
{
    //Esta mal puesto esto aqui solo lo he puesto para ver que escribe
    var Nombre by remember { mutableStateOf("") }
    var NombreCorto by remember { mutableStateOf("") }
    var Descripcion by remember { mutableStateOf("") }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "AÑADIR SECCION",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 23.dp)
            )
            TextField(
                value = Nombre,
                onValueChange = { newNom -> Nombre = newNom },
                label = { Text("Nombre") },
                singleLine = true,
                //Sacado de Internet he preguntado para saber tonalidades profesionales
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F1F1),
                    focusedTextColor = Color.Black,
                    focusedLabelColor = Color(0xFF6200EE)
                )
            )



            Spacer(modifier.size(10.dp))

            TextField(
                value = NombreCorto,
                onValueChange = { newNomCor -> NombreCorto = newNomCor },
                label = { Text("Nombre Corto") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F1F1),
                    focusedTextColor = Color.Black,
                    focusedLabelColor = Color(0xFF6200EE)
                )
            )
            Spacer(modifier.size(10.dp))
            SimpleExposedDropdownMenuDemo()
            Box(
                modifier = modifier.size(300.dp)
            )

            {
                OutlinedTextField(
                    value = Descripcion,
                    onValueChange = { newDesc -> Descripcion = newDesc  },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxSize(),
                    maxLines = 3,
                )
            }
            Spacer(modifier.size(8.dp))
            TextButton(
                onClick = {},
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Green,
                    contentColor = Color.DarkGray
                ),
                shape = RoundedCornerShape(8.dp)

            ) {
                Text(
                    text = "Añadir",
                )
            }

        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleExposedDropdownMenuDemo() {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Selecciona una opción") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Text("Dependencia 1", modifier = Modifier.padding(16.dp))
            Text("Dependencia 2", modifier = Modifier.padding(16.dp))
            Text("Dependencia 3", modifier = Modifier.padding(16.dp))
        }
    }
}
*/
