package com.danrley.barbershop

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.danrley.barbershop.databinding.ActivityMainBinding
import com.danrley.barbershop.view.Cadastro
import com.danrley.barbershop.view.Home
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val referencia: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private val usuario = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.btRegistrar.setOnClickListener {
            mensagem(it, "Teste")
            navegarCadastro()
        }
        binding.btLogin.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val senha = binding.editSenha.text.toString()

            when{
                nome.isEmpty() -> {
                    mensagem(it, "Informe o seu Email")
                }senha.isEmpty() -> {
                    mensagem(it, "Preencha a senha")
                }senha.length <=5 -> {
                    mensagem(it, "A senha deve ter pelo menos 6 caracteres")
                }else -> {
                    usuario.signInWithEmailAndPassword(nome, senha)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.i("signIn", "Sucesso ao logar usuário!")
                                navegarHome(nome)
                            } else {
                                mensagem(it, "Usuário ou senha incorreto(a)")
                                Log.i("signIn", "Erro logar usuário!")
                            }
                    }

                }
            }
        }

    }
    private fun mensagem(view: View, mensagem: String){
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }
    private fun navegarHome(nome: String){
        val intent = Intent(this, Home::class.java)
        intent.putExtra("nome", nome)
        startActivity(intent)
    }
    private fun navegarCadastro(){
        val intent = Intent(this, Cadastro::class.java)
        startActivity(intent)
    }
}