package com.example.inventorypersonal.data.model.Sections


import com.example.inventorypersonal.data.model.Dependencies.Dependency
import java.time.LocalDateTime

data class Section(
    var id:Int,
    val name: String,
    val nameshort: String,
    val dependencia: Dependency,
    val description: String,
    val Image:Any,
    val fecha: LocalDateTime

)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Section) return false

        return id == other.id &&
                name == other.name &&
                nameshort == other.nameshort &&
                description == other.description &&
                Image == other.Image &&
                description ==other.description &&
                fecha == other.fecha
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

