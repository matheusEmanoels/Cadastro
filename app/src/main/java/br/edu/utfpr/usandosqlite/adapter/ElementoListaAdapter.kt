package br.edu.utfpr.usandosqlite.adapter

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import br.edu.utfpr.usandosqlite.ListarActivity
import br.edu.utfpr.usandosqlite.MainActivity
import br.edu.utfpr.usandosqlite.R
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.database.DatabaseHandler.Companion.ID
import br.edu.utfpr.usandosqlite.database.DatabaseHandler.Companion.NOME
import br.edu.utfpr.usandosqlite.database.DatabaseHandler.Companion.TELEFONE
import br.edu.utfpr.usandosqlite.entity.Cadastro

class ElementoListaAdapter (val context : Context, val cursor : Cursor) : BaseAdapter() {

    private lateinit var banco : DatabaseHandler

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(position: Int): Any {
        cursor.moveToPosition(position)

        return Cadastro(
            cursor.getInt(ID),
            cursor.getString(NOME),
            cursor.getString(TELEFONE)
        )

    }

    override fun getItemId(position: Int): Long {
        cursor.moveToPosition(position)

        return cursor.getInt(ID).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v = inflater.inflate(R.layout.elemento_lista, null)

        val tvNomeElementoLista = v.findViewById<TextView>(R.id.tvElementoLista)
        val tvTelefoneElementoLita = v.findViewById<TextView>(R.id.tvTelefoneElementoLista)
        val btExcluirElementoLista = v.findViewById<ImageButton>(R.id.btExcluirElementoLista)
        val btEditarElementoLista = v.findViewById<ImageButton>(R.id.btEditarElementoLista)

        banco = DatabaseHandler( context )


        cursor.moveToPosition(position)

        tvNomeElementoLista.setText( cursor.getString(NOME))
        tvTelefoneElementoLita.setText( cursor.getString(TELEFONE))

        btExcluirElementoLista.setOnClickListener{
            val intent  = Intent( context, ListarActivity::class.java)

            banco.delete(cursor.getInt(ID))

            Toast.makeText( context, "Sucesso!", Toast.LENGTH_LONG ).show()

            context.startActivity(intent)

        }

        btEditarElementoLista.setOnClickListener{
            val intent  = Intent( context, MainActivity::class.java)


            cursor.moveToPosition(position)

            intent.putExtra("cod", cursor.getInt(ID))
            intent.putExtra("nome", cursor.getInt(NOME))
            intent.putExtra("telefone", cursor.getInt(TELEFONE))

            context.startActivity(intent)
        }

        return v
    }
}