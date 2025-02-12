

package com.example.inventorypersonal.data.repository.Sections


import com.example.inventorypersonal.data.model.Dependencies.DepenedencyException
import com.example.inventorypersonal.data.model.Sections.Section
import com.example.inventorypersonal.data.repository.Dependencies.DependencyRepository
import com.example.inventorypersonal.data.Network.BaseResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

object SectionRepository {
    private var dataSection :MutableList<Section> = mutableListOf()

    init{
        initialize()
    }
    private fun initialize() {
        // Obtener dependencias del repositorio
        val dependencies = DependencyRepository.dataDependency

        // Validar que existan dependencias en el repositorio
        if (dependencies.isNotEmpty()) {
            // Sección 1: Asociada a la dependencia con id 1
            val dependency1 = dependencies.firstOrNull { it.id == 1 }
            if (dependency1 != null) {
                dataSection.add(
                    Section(
                        id = 1,
                        name = "Chair",
                        nameshort = "cha",
                        description = "It is an object to sit",
                        dependencia = dependency1,
                        Image = "",
                        fecha = LocalDateTime.now()
                    )
                )
            }

            // Sección 2: Asociada a la dependencia con id 2
            val dependency2 = dependencies.firstOrNull { it.id == 2 }
            if (dependency2 != null) {
                dataSection.add(
                    Section(
                        id = 2,
                        name = "Desk",
                        nameshort = "dsk",
                        description = "A table for working",
                        dependencia = dependency2,
                        Image = "",
                        fecha = LocalDateTime.now()
                    )
                )
            }

            // Sección 3: Asociada a la dependencia con id 3
            val dependency3 = dependencies.firstOrNull { it.id == 3 }
            if (dependency3 != null) {
                dataSection.add(
                    Section(
                        id = 3,
                        name = "Treadmill",
                        nameshort = "trd",
                        description = "A machine for running indoors",
                        dependencia = dependency3,
                        Image = "",
                        fecha = LocalDateTime.now()
                    )
                )
            }
        } else {
            println("No dependencies found in DependencyRepository.")
        }
    }


    fun getSectionById(id: Int): Section? {
        return dataSection.firstOrNull { it.id == id }
    }
    fun addSection(section: Section): BaseResult<String> {
        val newId = dataSection.size + 1
        section.id=newId
        return if (dataSection.any { it.id == section.id }) {
            BaseResult.Error(DepenedencyException.DuplicateId)
        } else {
            dataSection.add(section)
            BaseResult.Sucess("Section added successfully")
        }
    }


     fun getData(): Flow<List<Section>> = flow {
        delay(2000)
        emit(dataSection) // Emite los datos correctamente
    }
}

