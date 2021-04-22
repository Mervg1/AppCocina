package edu.itesm.appcocina

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import edu.itesm.appcocina.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    // variables de binding y FirebaseAuth:
    private lateinit var bind : ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    //Agregando Analytics
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var bundle: Bundle
    //Base de datos
    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("usuarios")

        //inicializa las variables:
        analytics = FirebaseAnalytics.getInstance(this)
        bundle = Bundle()

        // Para Binding con elementos del Layout
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)

        // Inicializa objetos:
        auth = Firebase.auth
        setRegister() //sigue en la siguiente sección.
    }

    private fun setRegister(){

        bind.registerbtn.setOnClickListener {
            if (bind.correo.text.isNotEmpty() && bind.password.text.isNotEmpty() && bind.nombre.text.isNotEmpty() && bind.apellidos.text.isNotEmpty()){
                // utiliza la clase de FirebaseAuth:
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(

                    bind.correo.text.toString(), //usuario y password
                    bind.password.text.toString()

                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        usuarioCreado() //Viene más adelante la función


                        //Agregar Nombre y Apellidos a la información de usuario
                        // Evento hacia analytics
                        bundle.putString("edu_itesm_appcocina_main", "added_user")
                        analytics.logEvent("main",bundle)

                        val perfil = Perfil(bind.nombre.text.toString(), bind.apellidos.text.toString(), bind.correo.text.toString())
                        val id = reference.push().key
                        reference.child(id!!).setValue(perfil)

                        bind.correo.text.clear() //Limpiar las cajas de texto
                        bind.password.text.clear()
                        bind.nombre.text.clear()
                        bind.apellidos.text.clear()

                        //Crea un intento y entra a MainActivity.
                        val intento = Intent(this, MainActivity::class.java)
                        startActivity(intento)
                        finish()
                    }
                }.addOnFailureListener{
                    // en caso de error
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()

                }




            }/*else{
                Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
            }*/


        }

        /*bind.loginbtn.setOnClickListener {
            // Valida que correo y password no esten vacíos, incluye:

            //Para ingresar cambia al método de signInWithEmailAndPassword

            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                bind.correo.text.toString(),
                bind.password.text.toString()
            ).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this, "Bienvenido a Appetitoso", Toast.LENGTH_LONG).show()
                    //Crea un intento y entra a MainActivity.
                    Handler().postDelayed({
                        val intento = Intent(this, MainActivity::class.java)
                        startActivity(intento)
                        finish()
                    }, 3000)


                }else{
                    Toast.makeText(this, "Error en los datos!", Toast.LENGTH_LONG).show()
                }
            }
        }*/



    }

    private fun usuarioCreado(){
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle("Usuario Appetitoso")
            setMessage("Usuario creado con éxito!")
            show()
        }
    }
}