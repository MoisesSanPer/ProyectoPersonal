package com.example.inventorypersonal.data.model.Sections



sealed class SectionException(message:String):Exception(message){
    data object DuplicateId: SectionException("El id esta duplicado")

}