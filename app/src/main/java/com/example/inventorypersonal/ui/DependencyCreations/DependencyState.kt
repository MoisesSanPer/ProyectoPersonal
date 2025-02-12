package com.example.inventorypersonal.ui.DependencyCreations

import java.time.LocalDateTime

data class  DependencyState(
    val id:Int=0,
    val name :String="",
    val shortName:String="",
    val description:String="",
    val image:String="",
    val fecha : LocalDateTime = LocalDateTime.now(),


    val nameErrorFormat: String? = null,
    val shortNameErrorFormat: String?=null,
    val descriptionErrorFormat: String?=null,
    var isEmpty:String = "",



    val isNameError:Boolean=false,
    val isShortNameError:Boolean=false,
    val isdescriptionError:Boolean=false,
    var isError: Boolean = false,




    var success: Boolean = false,
    var isLoading: Boolean = false,
    )