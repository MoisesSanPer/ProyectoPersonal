package com.example.inventorypersonal.ui.SectionLists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.inventorypersonal.data.model.Sections.Section
import com.example.inventorypersonal.data.repository.Sections.SectionRepository
import kotlinx.coroutines.launch

class SectionListViewModel(): ViewModel() {
    var state by mutableStateOf<SectionListState>(SectionListState.NoData)
    var list :List<Section> by mutableStateOf(emptyList())
    fun getList(){
        state = SectionListState.Loading
        //Se inicia la corrutina
        viewModelScope.launch {
            SectionRepository.getData().collect{ section ->
                if(section.isNotEmpty()) {
                    //1 Si hay datos
                    list = section
                    state = SectionListState.Sucess(list)
                }
                else
                //2 Si no hay datos
                    state= SectionListState.NoData
            }

        }
    }
}