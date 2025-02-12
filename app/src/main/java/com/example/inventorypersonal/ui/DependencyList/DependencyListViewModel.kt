package com.example.inventorypersonal.ui.DependencyList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorypersonal.data.model.Dependencies.Dependency
import com.example.inventorypersonal.data.repository.Dependencies.DependencyRepository
import kotlinx.coroutines.launch

class DependencyListViewModel(): ViewModel() {
    var state by mutableStateOf<DependencyListState>(DependencyListState.NoData)
    var list :List<Dependency> by mutableStateOf(emptyList())
    fun getList(){
        state = DependencyListState.Loading
        //Se inicia la corrutina
        viewModelScope.launch {
            DependencyRepository.getData().collect{ dependency ->
                if(dependency.isNotEmpty()) {
                    //1 Si hay datos
                    list = dependency
                    state = DependencyListState.Sucess(list)
                }
                else
                //2 Si no hay datos
                    state= DependencyListState.NoData
            }

        }
    }
}