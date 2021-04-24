package edu.itesm.appcocina

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import edu.itesm.appcocina.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    //Ejemplo de API
    private val url = "https://api.edamam.com/search?q=all&app_id=7721e889&app_key=33ebd1f304fac28cd7809a27ade6ce9b"
    //private val url="https://disease.sh/v3/covid-19/countries"

    // variables de binding y FirebaseAuth:
    private lateinit var bind : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        //cargaDatos()

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