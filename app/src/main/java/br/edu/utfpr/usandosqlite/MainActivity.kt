package br.edu.utfpr.usandosqlite

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.entity.Cadastro

class MainActivity : AppCompatActivity() {

    private lateinit var etCod : EditText
    private lateinit var etNome : EditText
    private lateinit var etTelefone : EditText
    private lateinit var etCpf : EditText
    private lateinit var btPesquisar : Button

    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCod = findViewById( R.id.etCod )
        etNome = findViewById( R.id.etNome )
        etCpf = findViewById( R.id.etCpf )
        etTelefone = findViewById( R.id.etTelefone )
        btPesquisar = findViewById( R.id.btPesquisar )

        etCod.isEnabled = false

        banco = DatabaseHandler( this )

        if ( intent.getIntExtra( "cod", 0 ) != 0 ) {
            etCod.setText( intent.getIntExtra( "cod", 0 ).toString() )
            etNome.setText( intent.getStringExtra( "nome" ) )
            etCpf.setText( intent.getStringExtra( "cpf" ) )
            etTelefone.setText( intent.getStringExtra( "telefone" ) )
        } else {
            btPesquisar.visibility = View.GONE
        }

        println( "onCreate() executado" )
    }


    fun btAlterarOnClick(view: View) {

        if ( etCod.text.toString().isEmpty() ) {
            val cadastro = Cadastro(
                0,
                etNome.text.toString(),
                etCpf.text.toString(),
                etTelefone.text.toString()
            )
            banco.insert( cadastro )
        } else {
            val cadastro = Cadastro(
                etCod.text.toString().toInt(),
                etNome.text.toString(),
                etCpf.text.toString(),
                etTelefone.text.toString()
            )
            banco.update( cadastro )
        }

        Toast.makeText( this, "Sucesso!", Toast.LENGTH_LONG ).show()

        finish()
    }


    fun btPesquisarOnClick(view: View) {

        val etCodPesquisa = EditText( this )

        val builder = AlertDialog.Builder( this )
        builder.setTitle( "Cod. do Cadastro" )
        builder.setView( etCodPesquisa )
        builder.setCancelable( false )
        builder.setNegativeButton( "Fechar", null )
        builder.setPositiveButton( "Pesquisar",
            DialogInterface.OnClickListener { dialogInterface, i ->
                val cadastro = banco.find(etCodPesquisa.text.toString().toInt())

                if (cadastro != null) {
                    etCod.setText( etCodPesquisa.text.toString() )
                    etNome.setText(cadastro.nome)
                    etCpf.setText(cadastro.cpf)
                    etTelefone.setText(cadastro.telefone)
                } else {
                    Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_LONG).show()
                }
            }
        )
        builder.show()


        /*

*/
    }


    override fun onStart() {
        super.onStart()
        println( "onStart() executado" )
    }

    override fun onResume() {
        super.onResume()
        println( "onResume() executado" )
    }

    override fun onPause() {
        super.onPause()
        println( "onPause() executado" )
    }

    override fun onStop() {
        super.onStop()
        println( "onStop() executado" )
    }

    override fun onDestroy() {
        super.onDestroy()
        println( "onDestroy() executado" )
    }

    override fun onRestart() {
        super.onRestart()
        println( "onRestart() executado" )
    }

}