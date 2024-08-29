package br.edu.utfpr.usandosqlite

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite.adapter.ElementoListaAdapter
import br.edu.utfpr.usandosqlite.database.DatabaseHandler

class ListarActivity : AppCompatActivity() {

    private lateinit var lvRegistros : ListView
    private lateinit var banco  :DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        lvRegistros = findViewById( R.id.lvRegistros )
        banco = DatabaseHandler(this)

        val registros = banco.listCursor()

        val adapter = ElementoListaAdapter(this,registros)

        lvRegistros.adapter = adapter
    }
}