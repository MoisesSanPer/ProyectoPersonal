package com.example.inventorypersonal.data.repository.Dependencies

import com.example.inventorypersonal.data.model.Dependencies.Dependency
import com.example.inventorypersonal.data.model.Dependencies.DepenedencyException
import com.example.inventorypersonal.data.Network.BaseResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

object DependencyRepository {
     var dataDependency :MutableList<Dependency> = mutableListOf()

    init{
        initialize()
    }
    private fun initialize(
    )
    {
        dataDependency.add(
            Dependency(
                id = 1,
                name ="CafeZone",
                nameshort = "cafe",
                description = "It is a place",
                Image = "",
                Date = LocalDateTime.now()
            )
        )
        dataDependency.add(
        Dependency(
            id = 2,
            name = "LibrarySpace",
            nameshort = "library",
            description = "A quiet place to read and study",
            Image = "",
            Date = LocalDateTime.now()
        )
        )
        dataDependency.add(
            Dependency(
                id = 3,
                name = "GymZone",
                nameshort = "gym",
                description = "A space equipped for physical exercise",
                Image = "",
                Date = LocalDateTime.now()
            )
        )

    }
    fun getDependencyById(id: Int): Dependency? {

        return dataDependency.firstOrNull { it.id == id }
    }
    fun addDependency(dependency: Dependency): BaseResult<String> {
        val newId = dataDependency.size + 1
        dependency.id=newId
        return if (dataDependency.any { it.id == dependency.id }) {
            BaseResult.Error(DepenedencyException.DuplicateId)
        } else {
            dataDependency.add(dependency)
            return BaseResult.Sucess("Dependency added successfully")
        }
    }
    //Para pedir datos
        fun getData(): Flow<List<Dependency>> = flow {
        delay(2000)
        emit(dataDependency) // Emite los datos correctamente
    }

}
