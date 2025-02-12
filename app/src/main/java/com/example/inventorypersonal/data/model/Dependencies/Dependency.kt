package com.example.inventorypersonal.data.model.Dependencies

import java.time.LocalDateTime


data class Dependency(
    var id:Int,
    val name: String,
    val nameshort: String,
    val description: String,
    val Image:Any,
    val Date: LocalDateTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dependency) return false

        return id == other.id &&
                name == other.name &&
                nameshort == other.nameshort &&
                description == other.description &&
                Image == other.Image &&
                Date == other.Date
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}


