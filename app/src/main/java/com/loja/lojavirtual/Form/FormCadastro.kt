package com.loja.lojavirtual.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.loja.lojavirtual.R
import kotlinx.android.synthetic.main.activity_form_cadastro.*



class FormCadastro : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)

        supportActionBar!!.hide()

        bt_cadastrar.setOnClickListener {
            CadastrarUsuario()
        }

    }

    private fun CadastrarUsuario(){

        val email = edit_email.text.toString()
        val senha = edit_senha.text.toString()

        if(email.isEmpty() || senha.isEmpty()){

            var snackbar = Snackbar.make(layout_cadastro,"Coloque o seu e-mail e sua senha!",Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("OK", View.OnClickListener {

                })
            snackbar.show()

        }else{

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener {

                if (it.isSuccessful){
                    var snackbar = Snackbar.make(layout_cadastro,"Cadastro Realizado com Sucesso!",Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("OK", View.OnClickListener {
                             VoltarParaFormLogin()
                        })
                    snackbar.show()
                }
            }.addOnFailureListener {

                var snackbar = Snackbar.make(layout_cadastro,"Erro ao cadastrar usuário",Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("OK", View.OnClickListener {

                    })
                snackbar.show()
            }
        }
    }

    private fun VoltarParaFormLogin(){

        var intent = Intent(this,FormLogin::class.java)
        startActivity(intent)
        finish()
    }


}
