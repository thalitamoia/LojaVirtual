package com.loja.lojavirtual.Fragments


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.loja.lojavirtual.Model.Dados
import com.loja.lojavirtual.R
import kotlinx.android.synthetic.main.activity_cadastro_produtos.*
import kotlinx.android.synthetic.main.activity_cadastro_produtos.view.*
import java.util.*

class CadastroProdutos : AppCompatActivity() {

    private var SelecionarUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_produtos)

        bt_selecionar_foto.setOnClickListener {

            SelecionarFotoDaGaleria()
        }

        bt_cadastrar_produto.setOnClickListener {

            SalvarDadosNoFirebase()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0){
          SelecionarUri = data?.data

          val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,SelecionarUri)
          foto_produto.setImageBitmap(bitmap)
          bt_selecionar_foto.alpha = 0f
        }
    }

    private fun SelecionarFotoDaGaleria(){

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,0)
    }

    private fun SalvarDadosNoFirebase(){

        val nomeArquivo = UUID.randomUUID().toString()
        val referencia = FirebaseStorage.getInstance().getReference(
            "/imagens/${nomeArquivo}")

        SelecionarUri?.let {

            referencia.putFile(it)
                .addOnSuccessListener {
                    referencia.downloadUrl.addOnSuccessListener {

                        val url = it.toString()
                        val nome = edit_nome.text.toString()
                        val preco = edit_preco.text.toString()
                        val uid = FirebaseAuth.getInstance().uid

                        val Produtos = Dados(url,nome,preco)
                        FirebaseFirestore.getInstance().collection("Produtos")
                            .add(Produtos)
                            .addOnSuccessListener {

                                Toast.makeText(this,"Produto cadastrado com sucesso!",Toast.LENGTH_SHORT).show()

                            }.addOnFailureListener {

                            }


                    }
                }
        }

    }

}
