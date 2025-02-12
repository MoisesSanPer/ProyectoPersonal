package com.example.inventorypersonal.ui.SectionLists


import com.example.inventorypersonal.data.model.Sections.Section

sealed class  SectionListState
{
    data object NoData: SectionListState()
    data object  Loading: SectionListState()
    data class Sucess(var datalist:List<Section>): SectionListState()
}