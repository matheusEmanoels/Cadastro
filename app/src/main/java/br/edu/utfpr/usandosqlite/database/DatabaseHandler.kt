package br.edu.utfpr.usandosqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import br.edu.utfpr.usandosqlite.entity.Cadastro


class DatabaseHandler (context : Context) : SQLiteOpenHelper(context, DATABESE_NAME,null, DATABESE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL( "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT, cpf TEXT, telefone TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(db)
    }

    companion object{
        private const val  DATABESE_NAME = "dbfile.sqlite"
        private const val  DATABESE_VERSION = 2
        private const val  TABLE_NAME = "cadastro"
        public const val  ID = 0
        public const val  NOME = 1
        public const val  CPF = 2
        public const val  TELEFONE = 3
    }

    fun insert(cadastro : Cadastro){
        val db = this.writableDatabase

        val registro = ContentValues()

        registro.put("nome", cadastro.nome)
        registro.put("cpf", cadastro.cpf)
        registro.put("telefone", cadastro.telefone)

        db.insert("cadastro", null, registro)


    }

    fun update(cadastro : Cadastro){
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put("nome", cadastro.nome)
        registro.put("cpf", cadastro.cpf)
        registro.put("telefone", cadastro.telefone)

        db.update(TABLE_NAME, registro, "_id=${cadastro._id}",null)

    }

    fun delete(id : Int){
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "_id=${id}", null)
    }
    fun find(id : Int) : Cadastro?{
        val db = this.writableDatabase
        val registro = db.query(TABLE_NAME, null, "_id = ${id}", null, null, null, null)

        if (registro.moveToNext()){
            val cadastro = Cadastro(id,registro.getString(NOME),
                registro.getString(CPF),
                registro.getString(TELEFONE))
            return cadastro

        } else {
            return null
        }
    }
    fun list() : MutableList<Cadastro>{
        val db = this.writableDatabase
        val registro = db.query(TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null)

        var registros = mutableListOf<Cadastro>()

        while (registro.moveToNext()){
            val cadastro = Cadastro(registro.getInt(ID), registro.getString(NOME),
                registro.getString(CPF),
                registro.getString(TELEFONE))
            registros.add(cadastro)
        }

        return registros
    }

    fun listCursor() : Cursor{
        val db = this.writableDatabase
        val registro = db.query(TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null)

        return registro
    }
}