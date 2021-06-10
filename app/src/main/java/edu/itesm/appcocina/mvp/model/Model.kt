package edu.itesm.appcocina.mvp.model

import androidx.core.os.HandlerCompat.postDelayed
import edu.itesm.appcocina.mvp.contract.Contract
import java.util.*
import android.os.Handler;
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import edu.itesm.appcocina.model.Ingredient
import edu.itesm.appcocina.mvp.view.ListaSuperAdapter
import edu.itesm.appcocina.mvvm.view.detalle.IngredientsAdapter

class Model : Contract.Model {
    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    private lateinit var user : FirebaseUser
    private lateinit var currId : String
    var lista = ArrayList<Ingredient>()



    private val listaMensajes = Arrays.asList(
        "Come saludable",
        "Tu puedes",
        "Es tiempo de brillar",
        "Empieza hoy",
        "Vive fit"
    )

    override fun getData(){

        Log.i("entro a ayuda", "ayuda entro")
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("ingredients")
        user = FirebaseAuth.getInstance().currentUser

        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                /*Log.i("entro a ayuda", "ayuda entro2")
                Log.i("valoresIngSS", snapshot.getValue().toString())*/

                for (ing in snapshot.children) {
                    Log.i("valoresIng", ing.getValue().toString())
                    var objeto = ing.getValue(Ingredient::class.java)
                    if(user.email == objeto?.email){
                        lista.add(objeto as Ingredient)
                    }
                    Log.i("valoresIng", objeto.toString())
                }
                /*if (lista.isEmpty()){
                    //Toast.makeText(getActivity(), "Your cart is empty", Toast.LENGTH_SHORT).show()
                }*/
                /*recyclerView.apply{
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CarritoAdapter(lista)
                }*/

                //adapter = ListaSuperAdapter(lista)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("valoresIngError", "objeto.toString()")
            }
        })



    }




    private fun randomMensaje() : String {
        val posicion = (0..listaMensajes.size-1).random()
        return listaMensajes[posicion]
    }

    override fun getSiguienteMensaje(onFinishListener : Contract.Model.OnFinish) {
        //ayuda()
        Handler().postDelayed({onFinishListener.onFinished(randomMensaje())}, 1500)


    }


}