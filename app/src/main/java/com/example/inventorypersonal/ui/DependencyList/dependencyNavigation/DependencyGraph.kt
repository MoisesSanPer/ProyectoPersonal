package app.features.categorylist.ui.dependencyNavigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.inventorypersonal.ui.DependencyCreations.DependencyViewModel
import com.example.inventorypersonal.ui.DependencyList.DependencyListViewModel
import com.example.inventorypersonal.ui.DependencyList.DependenciesContentPrincipal
import com.example.inventorypersonal.ui.DependencyCreations.DependenciesScreen

object DependencyGraph {

    const val ROUTE = "dependency_graph"

    fun addDependency() = "$ROUTE/addDependency"
    fun listDependency() = "$ROUTE/listDependency"
}



fun NavGraphBuilder.dependencyGraph(
    navController: NavController,
    creationViewModel: DependencyViewModel,
    listViewModel: DependencyListViewModel
) {
    navigation(startDestination = DependencyGraph.listDependency(), route = DependencyGraph.ROUTE){
        addDependency(navController, creationViewModel)
        listDependency(navController, listViewModel)
    }
}
private fun NavGraphBuilder.addDependency(navController: NavController, viewModel: DependencyViewModel) {
    composable(DependencyGraph.addDependency()) {
        DependenciesScreen( goToDependencyList = {navController.navigate(DependencyGraph.listDependency())}, viewModel = viewModel)
    }

}

fun NavGraphBuilder.listDependency(navController: NavController, viewModel: DependencyListViewModel) {
    composable(route = DependencyGraph.listDependency()) {
        DependenciesContentPrincipal(modifier = Modifier, viewModel = viewModel, goToAddDependency = {navController.navigate(
            DependencyGraph.addDependency()
        ){popUpToRoute} })
    }
}