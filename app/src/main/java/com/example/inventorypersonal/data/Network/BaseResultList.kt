package com.example.inventorypersonal.data.Network

sealed class BaseResultList<T> {
    data class Sucess<T>(var data :ArrayList<T>?): BaseResult<T>()
    data class Error<T>(var exception :Exception): BaseResult<Nothing>()
}