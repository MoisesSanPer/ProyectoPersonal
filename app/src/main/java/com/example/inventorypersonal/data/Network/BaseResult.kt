package com.example.inventorypersonal.data.Network

sealed class BaseResult <out T>{
    //Esto engloba y son las 2 opciones posible que obtienes al darle a validar a el usuario
    data class Sucess<T>(var data:T) : BaseResult<T>()
    data class Error(var exception:Exception): BaseResult<Nothing>()
}
