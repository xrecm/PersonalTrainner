package com.example.personaltrainner.business.prototype

import com.example.personaltrainner.data.RutinaEntity
import java.util.Date

open class ConcretePrototype(
    var id: Int,
    var clienteId: Int,
    var ejercicioId: Int,
    var fecha: Date,
    var repeticiones: Int,
    var series: Int
) : Prototype {
    constructor(prototype: ConcretePrototype) : this(
        id = prototype.id,
        clienteId = prototype.clienteId,
        ejercicioId = prototype.ejercicioId,
        fecha = prototype.fecha,
        repeticiones = prototype.repeticiones,
        series = prototype.series
    )

    override fun clone(): ConcretePrototype {
        return ConcretePrototype(this)
    }

    fun toRutinaEntity(): RutinaEntity {
        return RutinaEntity(
            id = this.id,
            clienteId = this.clienteId,
            ejercicioId = this.ejercicioId,
            fecha = this.fecha,
            repeticiones = this.repeticiones,
            series = this.series
        )
    }
}


