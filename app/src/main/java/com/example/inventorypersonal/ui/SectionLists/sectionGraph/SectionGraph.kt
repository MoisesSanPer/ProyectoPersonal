package app.features.categorylist.ui.sectionGraph

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.inventorypersonal.ui.SectionCreations.SectionScreen
import com.example.inventorypersonal.ui.SectionCreations.SectionViewModel
import com.example.inventorypersonal.ui.SectionLists.SectionContentPrincipal
import com.example.inventorypersonal.ui.SectionLists.SectionListViewModel

object SectionGraph {

    const val ROUTE = "section_graph"

    fun addSection() = "$ROUTE/addSection"
    fun listSection() = "$ROUTE/listSection"
}



fun NavGraphBuilder.SectionGraph(
    navController: NavController,
    creationViewModel: SectionViewModel,
    listViewModel: SectionListViewModel
) {
    navigation(startDestination = SectionGraph.listSection(), route = SectionGraph.ROUTE){
        addSection(navController, creationViewModel)
        listSection(navController, listViewModel)
    }
}
private fun NavGraphBuilder.addSection(navController: NavController, viewModel: SectionViewModel) {
    composable(SectionGraph.addSection()) {
        SectionScreen( goToSectionList = {navController.navigate(SectionGraph.listSection())}, viewModel = viewModel)
    }

}

fun NavGraphBuilder.listSection(navController: NavController, viewModel: SectionListViewModel) {
    composable(route = SectionGraph.listSection()) {
        SectionContentPrincipal(modifier = Modifier, viewModel = viewModel, goTOAddSection = {navController.navigate(
            SectionGraph.addSection()
        ){popUpToRoute} })
    }
}