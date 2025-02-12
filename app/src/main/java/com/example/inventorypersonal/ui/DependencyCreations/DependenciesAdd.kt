package com.example.inventorypersonal.ui.DependencyCreations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventorypersonal.ui.Common.Loading


data class DependenciesAddEvents(
    val  onNameChange: (String) -> Unit={},
    val  onShortNameChange: (String) -> Unit={},
    val   onDescriptionChange: (String) -> Unit={},
        val onAddClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun DependencyPreview() {
    DependenciesScreen(
        modifier = Modifier,
        viewModel = DependencyViewModel(),
        goToDependencyList = {},
    )
}

//Forma2 con view Model
@Composable
fun DependenciesScreen(modifier: Modifier = Modifier,
                       goToDependencyList: () -> Unit,
                viewModel: DependencyViewModel
) {
    // Utilizamos el estado del ViewModel en lugar de un estado local
    DependencyAddViewModel(
        modifier = modifier,
        state = viewModel.state, // El estado del ViewModel
        goToDependencyList=goToDependencyList,
        events = DependenciesAddEvents(
            onNameChange = { name -> viewModel.validateName(name) },
            onShortNameChange = { shortName -> viewModel.validateNameShort(shortName) },
            onDescriptionChange = {  description-> viewModel.validatDescription(description) },
            onAddClick = viewModel::onAddClick
        )
        )
}
//Recibe logica y la paso a la clase tonta
@Composable
fun DependencyAddViewModel(
    modifier: Modifier = Modifier,
    goToDependencyList: () -> Unit,
    state: DependencyState,
    events: DependenciesAddEvents
) {
    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        when {
            state.isLoading -> {
                state.isLoading =false
                Loading(modifier = modifier)
            }
            state.success ->{
                state.success =false
                goToDependencyList()
            }
                else-> DependencyAddScreenContent(
                modifier = Modifier.padding(paddingValues),
                    goToDependencyList,
                state = state,
                events = events
            )
        }
    }
}
@Composable
fun DependencyAddScreenContent(
    modifier: Modifier = Modifier,
    goToDependencyList: () -> Unit,
    state: DependencyState,
    events: DependenciesAddEvents,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "AÑADIR DEPENDENCIA",
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
            Button  (
                onClick = {
                    // Aquí puedes agregar la lógica para agregar la dependencia si no hay errores
                    events.onAddClick()
                }
            ) {
                Text(
                    text = "Añadir".uppercase(),
                    style = TextStyle(color = Color.White)
                )
            }

        }


    }
}





/*
@Composable
fun DependenciesScreenAñadirDemo(modifier: Modifier = Modifier){
    DependenciesContentAñadirDemo(modifier)
}

@Composable
fun DependenciesContentAñadirDemo(modifier: Modifier)
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
                text = "AÑADIR DEPENDENCIA",
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
*/




