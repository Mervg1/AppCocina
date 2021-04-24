package edu.itesm.appcocina

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import edu.itesm.appcocina.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_perfil_usuario.*


class PerfilUsuario : Fragment() {

    private lateinit var bind : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    private lateinit var user : FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance()
        auth = Firebase.auth
        reference = database.getReference("usuarios")

        user = FirebaseAuth.getInstance().currentUser

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_usuario, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonGuardarPerfil.setOnClickListener(){
            var userid = user.uid
            var perfil = reference.child(userid).get()

            println(perfil)
        }
    }

}