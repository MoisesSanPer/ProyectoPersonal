package com.example.inventorypersonal.ui.SectionLists




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
import androidx.navigation.compose.rememberNavController import com.example.inventorypersonal.ui.SectionCreations.SectionViewModel
import app.features.categorylist.ui.sectionGraph.SectionGraph
import com.example.inventorypersonal.R
import com.example.inventorypersonal.data.model.Sections.Section


@Preview
@Composable
fun SectionScreenPrincipalListarPreview() {
    // Crear un estado simulado con datos desde el repositorio
    var SectionListViewModel = SectionListViewModel()
    var SectionViewModel = SectionViewModel()
    SectionListViewModel.getList()
    val navController = rememberNavController()
    NavHost(navController = navController,startDestination = SectionGraph.ROUTE){
        SectionGraph(navController, SectionViewModel,SectionListViewModel)
    }
}

@Composable
fun SectionContentPrincipal(modifier:Modifier=Modifier,
                            viewModel: SectionListViewModel,
                            goTOAddSection: () -> Unit
) {
    when (viewModel.state) {
        is SectionListState.Sucess -> {
            SectionListContent(section = viewModel.list,
                goTOAddSection = goTOAddSection)
        }
        SectionListState.NoData -> {
            Text(
                text = "No hay secciones disponibles.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        SectionListState.Loading -> {
            Text(
                text = "Cargando secciones...",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun SectionListContent(modifier: Modifier = Modifier, section: List<Section>,
                       goTOAddSection: () -> Unit) {
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
                    items(section) { item ->
                        SectionItem(text = item.name)
                    }
                }
            }
            FloatingActionButton(
                onClick = { goTOAddSection()},
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
fun SectionItem(modifier: Modifier = Modifier, text: String) {
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
            text = "Sections",
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

var List = mutableListOf<String>("Seccion1","Seccion2","Seccion3")

@Composable
@Preview
fun SeccionesListarPrincipalDemo(modifier: Modifier=Modifier){
    SeccionesListarContentDemo(modifier)
}


@Composable
private fun SeccionesListarContentDemo(modifier: Modifier=Modifier)
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
        items(10) { item ->
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

