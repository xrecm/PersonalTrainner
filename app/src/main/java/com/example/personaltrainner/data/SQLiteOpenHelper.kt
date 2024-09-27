package com.example.personaltrainner.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tablas
        db.execSQL(CREATE_TABLE_CLIENTE)
        db.execSQL(CREATE_TABLE_EJERCICIO)
        db.execSQL(CREATE_TABLE_RUTINA)
        db.execSQL(CREATE_TABLE_MEMBRESIA)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Si cambias el esquema, aquí puedes manejar las migraciones
        db.execSQL("DROP TABLE IF EXISTS clientes")
        db.execSQL("DROP TABLE IF EXISTS ejercicios")
        db.execSQL("DROP TABLE IF EXISTS rutinas")
        db.execSQL("DROP TABLE IF EXISTS membresia")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "personaltrainer.db"
        private const val DATABASE_VERSION = 1

        // Creación de tablas
        private const val CREATE_TABLE_CLIENTE = """
            CREATE TABLE clientes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                apellido TEXT,
                telefono INTEGER,
                edad INTEGER,
                sexo TEXT,
                tamaño REAL,
                peso REAL,
                membresiaId INTEGER,
                fechaInicioMembresia TEXT,
                fechaFinMembresia TEXT
            )
        """

        private const val CREATE_TABLE_EJERCICIO = """
            CREATE TABLE ejercicios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                descripcion TEXT,
                tipo TEXT
            )
        """

        private const val CREATE_TABLE_RUTINA = """
            CREATE TABLE rutinas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                clienteId INTEGER,
                ejercicioId INTEGER,
                fecha TEXT,
                repeticiones INTEGER,
                series INTEGER
            )
        """

        private const val CREATE_TABLE_MEMBRESIA = """
            CREATE TABLE membresia (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                tipo TEXT,
                nombre TEXT,
                descripcion TEXT,
                precio REAL
            )
        """
    }
}
