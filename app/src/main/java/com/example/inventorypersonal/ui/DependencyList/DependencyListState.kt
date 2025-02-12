package com.example.inventorypersonal.ui.DependencyList

import com.example.inventorypersonal.data.model.Dependencies.Dependency


sealed class  DependencyListState
{
    data object NoData: DependencyListState()
    data object  Loading: DependencyListState()
    data class Sucess(var datalist:List<Dependency>): DependencyListState()
}