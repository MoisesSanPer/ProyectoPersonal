package com.example.inventorypersonal.ui.DependencyList

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.inventorypersonal.ui.DependencyCreations.DependencyViewModel
import app.features.categorylist.ui.dependencyNavigation.DependencyGraph
import app.features.categorylist.ui.dependencyNavigation.dependencyGraph
import com.example.inventorypersonal.R
import com.example.inventorypersonal.data.model.Dependencies.Dependency

@Preview
@Composable
fun DependenciesScreenPrincipalListarPreview() {

    var dependencyListViewModel = DependencyListViewModel()
    var DependencyViewModel = DependencyViewModel()
    dependencyListViewModel.getList()
    val navController = rememberNavController()
    NavHost(navController = navController,startDestination = DependencyGraph.ROUTE){
        dependencyGraph(navController, DependencyViewModel,dependencyListViewModel)
    }
}

@Composable
fun DependenciesContentPrincipal(
    modifier: Modifier =Modifier,
    viewModel: DependencyListViewModel,
    goToAddDependency: () -> Unit
) {
    when (viewModel.state) {
        is DependencyListState.Sucess -> {
            DependencyListContent(dependency = viewModel.list,
                goToAddDependency = goToAddDependency)
        }
        DependencyListState.NoData -> {
            Text(
                text = "No hay dependencias disponibles.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        DependencyListState.Loading -> {
            Text(
                text = "Cargando dependencias...",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun DependencyListContent(modifier: Modifier = Modifier,
                          dependency: List<Dependency>,
                          goToAddDependency: () -> Unit) {
    Column {
        HorizontalDivider()
        Row {
            TopBarWithMenuIcon( )
        }
        HorizontalDivider()
        Box(
            modifier = Modifier.fillMaxSize(),
            )
        {
            Box(
                modifier.padding(vertical = 6.dp)
            )
            {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    modifier = modifier
                ) {
                    items(dependency) { item ->
                        DependencyItem(text = item.name)
                    }
                }
            }
            FloatingActionButton(
                onClick = { goToAddDependency() },
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                containerColor = Color.Black,
                contentColor = Color.White,
            ) {
                Icon(Icons.Filled.Add,"Añadir Dependencia")
            }
        }

    }

}

@Composable
fun DependencyItem(modifier: Modifier = Modifier, text: String) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 6.dp,
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {  },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Editar"
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithMenuIcon(modifier: Modifier=Modifier) {
    TopAppBar(
        title = {   Text(
            text = "Dependencias",
            fontSize = 20.sp,
        ) },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menú"
            )
        },
        actions =
        {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Filtrar"
                )
            }
            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sort),
                    contentDescription = "Ordenar"
                )
            }
        }
    )
}


/**
 * Este de aqui era  el anterior sin view Model lo que he hecho
 * es modificarlo al nuevo basandome he dejado el circulo para la imagen que pondremos mas adelante
 * el tema de las imagenes me he basado del login que hemos hecho en clase
 */

var List = mutableListOf<String>("Dependencia1","Dependencia2","Dependencia3")

@Composable
@Preview
fun DependenciesScreenPrincipalListarDemo(modifier: Modifier=Modifier){
    DependenciesDemoContentPrincipal(modifier)
}


@Composable
private fun DependenciesDemoContentPrincipal(modifier: Modifier=Modifier)
{
    Column {
        HorizontalDivider()
        Row {
            TopBarWithMenuIcon( )
        }
        HorizontalDivider()
        Box(
            modifier = Modifier.fillMaxSize(),
            )
        {
            Box(
                modifier.padding(vertical = 6.dp)
            )
            {
                ItemListDemo(List)
            }
            FloatingActionButton(
                onClick = { },
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                containerColor = Color.Black,
                contentColor = Color.White,
            ) {
                Icon(Icons.Filled.Add,"Añadir Dependencia")
            }
        }

    }
}

@Composable
fun ItemListDemo(items: List<String>) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(30) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Hola",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .weight(1f) // Empuja el resto del contenido hacia la derecha
                        .align(Alignment.CenterVertically)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "10 productos",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(end = 2.dp)
                    )
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Editar"
                        )
                    }
                }
            }
        }
    }
}






