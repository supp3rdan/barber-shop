package com.danrley.barbershop.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.danrley.barbershop.databinding.ActivityCadastroBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class Cadastro : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private val usuario = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val senha = binding.editSenha.text.toString()

            when{
                nome.isEmpty() -> {
                    mensagem(it, "Informe o seu nome")
                }senha.isEmpty() -> {
                mensagem(it, "Preencha a senha")
            }senha.length <=5 -> {
                mensagem(it, "A senha deve ter pelo menos 6 caracteres")
            }else -> {
                usuario.createUserWithEmailAndPassword(nome, senha)
                    .addOnCompleteListener(this,
                        OnCompleteListener<AuthResult?> { task ->
                            if (task.isSuccessful) {
                                Log.i("CreateUser", "Sucesso ao cadastrar usuário!")
                                Toast.makeText(
                                    applicationContext,
                                    "Usuário criado com sucesso",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Log.i("CreateUser", "Erro ao cadastrar usuário!")
                                Toast.makeText(applicationContext, "Algo deu errado", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })

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
}