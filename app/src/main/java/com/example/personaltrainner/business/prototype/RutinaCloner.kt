package com.example.personaltrainner.business.prototype

import java.util.Date

class RutinaCloner {
    fun clonarRutina(prototype: Prototype, nuevoClienteId: Int, nuevaFecha: Date): Prototype {
        val cloned = prototype.clone()
        if (cloned is ConcretePrototype) {
            cloned.id = 0
            cloned.clienteId = nuevoClienteId
            cloned.fecha = nuevaFecha
        }
        return cloned
    }
}
