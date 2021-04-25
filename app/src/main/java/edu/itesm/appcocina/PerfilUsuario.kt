package edu.itesm.appcocina

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_perfil_usuario.*


class PerfilUsuario : Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    private lateinit var user : FirebaseUser
    private lateinit var currId : String
    private lateinit var currUser : DataSnapshot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance()
        auth = Firebase.auth
        reference = database.getReference("usuarios")

        user = FirebaseAuth.getInstance().currentUser

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_usuario, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (datas in dataSnapshot.children) {
                    val keys = datas.key
                    reference.child(keys!!).get().addOnSuccessListener {
                        var foundmail = it.child("email").getValue().toString()
                        if (foundmail == user.email){
                            currUser = it
                            currId = keys;
                            nombrePerfil.setText(it.child("nombre").getValue().toString())
                            apellidoPerfil.setText(it.child("apellidos").getValue().toString())
                            correoPerfil.setText(it.child("email").getValue().toString())
                        }
                    }.addOnFailureListener{
                        Toast.makeText(requireActivity(),"Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
        buttonGuardarPerfil.setOnClickListener(){
            if (correoPerfil.text.isNotEmpty() && passPerfil.text.isNotEmpty() && nombrePerfil.text.isNotEmpty() && apellidoPerfil.text.isNotEmpty()){
                var user = auth.currentUser
                user.updatePassword(passPerfil.text.toString())
                user.updateEmail(correoPerfil.text.toString())
                var nuevoPerfil = Perfil(nombrePerfil.text.toString(),apellidoPerfil.text.toString(),correoPerfil.text.toString())
                reference.child(currId).setValue(nuevoPerfil)

            }else{
                Toast.makeText(requireActivity(), "Favor de llenar todos los campos", Toast.LENGTH_LONG).show()
            }
        }
    }
}