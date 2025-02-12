package com.example.inventorypersonal.data.model.Dependencies


sealed class DepenedencyException(message:String):Exception(message){
    data object DuplicateId:DepenedencyException("El id esta duplicado")
   data object  InvalidDependency:DepenedencyException("La dependencia  no es valida")
}