package edu.itesm.appcocina

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_mis_recetas.*
import kotlinx.android.synthetic.main.fragment_recipe_detail.*


class MisRecetas : Fragment() {


    val recetas = mutableListOf<Receta>()
    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user : FirebaseUser
    private lateinit var currId : String
    private lateinit var currUser : DataSnapshot
    private lateinit var lista:MutableList<String>

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = FirebaseDatabase.getInstance()
        auth = Firebase.auth
        reference = database.getReference("usuarios")
        user = FirebaseAuth.getInstance().currentUser
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_mis_recetas, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reference.get().addOnSuccessListener {
            for (datas in it.children) {
                val keys = datas.key
                reference.child(keys!!).get().addOnSuccessListener {
                    var email = it.child("email").getValue().toString()
                    if (email == user.email){
                        lista = it.child("favoritos").child("lista").getValue() as MutableList<String>
                        createData()
                    }
                }.addOnFailureListener{
                    Toast.makeText(requireActivity(),"Error", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener{
            Toast.makeText(requireActivity(),"Error", Toast.LENGTH_SHORT).show()
        }
        super.onViewCreated(view, savedInstanceState)

    }

    fun createData(){

        val requestQueue = Volley.newRequestQueue(activity)
        for(receta in lista){
            val url =
                "https://api.edamam.com/search?q=$receta&app_id=7721e889&app_key=33ebd1f304fac28cd7809a27ade6ce9b&imagesize=thumbnail"
            val peticion = JsonObjectRequest(Request.Method.GET,url,null, Response.Listener {
                val jsonArray  = it

                val recipes = jsonArray.getJSONArray("hits")

                for (i in 0 until recipes.length()){

                    val re = recipes.getJSONObject(i)

                    val recipeInfoData = re.getJSONObject("recipe")

                    val label = recipeInfoData.getString("label")
                    Log.i("///////////LABEL///////////////",label)
                    Log.i("///////////CONTAINS?///////////////",lista.contains(label).toString())
                    val image = recipeInfoData.getString("image")
                    val mientras = recipeInfoData.getString("source")
                    if(lista.contains(label)){
                        recetas.add(Receta(label, mientras, image))
                        Log.i("----------RECIPES------------",recetas.toString())
                        recyclerview.apply {
                            layoutManager = LinearLayoutManager(activity)
                            adapter = MisRecetasAdapter(recetas)
                        }
                    }
                }


            }, Response.ErrorListener {
                Toast.makeText(activity,"Error al leer los datos json!", Toast.LENGTH_LONG).show()
            })
            requestQueue.add(peticion)
        }

    }
}


