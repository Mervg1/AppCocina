package edu.itesm.appcocina

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import edu.itesm.appcocina.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    // variables de binding y FirebaseAuth:
    private lateinit var bind : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("usuarios")

        // Para Binding con elementos del Layout
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        // Inicializa objetos:
        auth = Firebase.auth
        setLoginRegister() //sigue en la siguiente sección.
    }

    private fun setLoginRegister(){
        bind.registerbtn.setOnClickListener {

            //Crea un intento y entra a MainActivity.
            val intento = Intent(this, RegisterActivity::class.java)
            startActivity(intento)
            finish()
           
        }

        bind.loginbtn.setOnClickListener {
            //Para ingresar cambia al método de signInWithEmailAndPassword

            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                bind.correo.text.toString(),
                bind.password.text.toString()
            ).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this,"Bienvenido Maestro Pokemon!", Toast.LENGTH_LONG).show()
                    //Crea un intento y entra a MainActivity.
                    val intento = Intent(this, MainActivity::class.java)
                    startActivity(intento)
                    finish()

                }else{
                    Toast.makeText(this,"Error en los datos!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}