package edu.itesm.appcocina.mvvm.view.detalle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import edu.itesm.appcocina.databinding.FragmentRecipeDetailBinding
import edu.itesm.appcocina.model.Ingredient
import kotlinx.android.synthetic.main.fragment_perfil_usuario.*
import kotlinx.android.synthetic.main.fragment_recipe_detail.*


class RecipeDetailFragment : Fragment() {
    private val args = navArgs<RecipeDetailFragmentArgs>()
    private lateinit var binding : FragmentRecipeDetailBinding
    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user : FirebaseUser
    private lateinit var currId : String
    private lateinit var currUser : DataSnapshot

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        auth = Firebase.auth
        reference = database.getReference("usuarios")
        user = FirebaseAuth.getInstance().currentUser
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Args", args.value.results.recipe.ingredientLines[0])
        nameDetailRecipe.text = args.value.results.recipe.label
        Picasso.get().load(args.value.results.recipe.image).into(imageDetail)
        calories.text = "Calories: ${args.value.results.recipe.calories.toString()}"
        totalTime.text = "Total Time : ${args.value.results.recipe.totalTime.toString()} m"

        reference.get().addOnSuccessListener {
            for (datas in it.children) {
                val keys = datas.key
                reference.child(keys!!).get().addOnSuccessListener {
                    var email = it.child("email").getValue().toString()
                    if (email == user.email){
                        currUser = it
                        currId = keys
                        var comida = args.value.results.recipe.label
                        var lista:MutableList<String> = it.child("favoritos").child("lista").getValue() as MutableList<String>
                        like.isChecked = lista.contains(comida)
                    }
                }.addOnFailureListener{
                    Toast.makeText(requireActivity(),"Error", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener{
            Toast.makeText(requireActivity(),"Error", Toast.LENGTH_SHORT).show()
        }
        url.setOnClickListener {

            val intento = Intent(Intent.ACTION_VIEW, Uri.parse(args.value.results.recipe.url.toString()))
            startActivity(intento)
            
        }
        like.setOnClickListener{

                reference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (datas in dataSnapshot.children) {
                            val keys = datas.key
                            reference.child(keys!!).get().addOnSuccessListener {
                                var email = it.child("email").getValue().toString()
                                Log.i("chwcar user", it.child("nombre").getValue().toString())
                                if (email == user.email){
                                    currUser = it
                                    currId = keys
                                    if(like.isChecked){ /// agregar a favoritos
                                        var num = it.child("favoritos").child("size").getValue().toString().toInt()
                                        var comida = args.value.results.recipe.label
                                        if (num == 0){

                                            var lista = mutableListOf(comida)
                                            reference.child(currId).child("favoritos").child("lista").setValue(lista)
                                            reference.child(currId).child("favoritos").child("size").setValue(num+1)
                                        }else{
                                            var lista:MutableList<String> = it.child("favoritos").child("lista").getValue() as MutableList<String>
                                            if(!lista.contains(comida)){
                                                lista.add(comida)
                                                reference.child(currId).child("favoritos").child("lista").setValue(lista)
                                                reference.child(currId).child("favoritos").child("size").setValue(num+1)
                                            }
                                        }
                                    }else{ /// Quitar de favoritos
                                        var num = it.child("favoritos").child("size").getValue().toString().toInt()
                                        var comida = args.value.results.recipe.label
                                        var lista:MutableList<String> = it.child("favoritos").child("lista").getValue() as MutableList<String>
                                        lista.remove(comida)
                                        reference.child(currId).child("favoritos").child("lista").setValue(lista)
                                        reference.child(currId).child("favoritos").child("size").setValue(num-1)
                                    }
                                }
                            }.addOnFailureListener{
                                Toast.makeText(requireActivity(),"Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })


        }

        Cart.setOnClickListener {
           addLista()
        }

    }


    private fun creaIngredients(){
        binding.ingredients.layoutManager = LinearLayoutManager(activity)

        ingredients.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = IngredientsAdapter(args.value.results.recipe.ingredientLines)
        }

    }

    fun addLista(){



        // Write a message to the database
        // Write a message to the database


        database = FirebaseDatabase.getInstance()
        reference = database.getReference("ingredients")
        //reference.child().key


        val usuario = Firebase.auth.currentUser
        Log.i("Boton", "Boton entro Cart")

        if(args.value.results.recipe.ingredientLines.size > 0){
            var sizeIng = args.value.results.recipe.ingredientLines.size


            for (i in 0..sizeIng-1){
                Log.i("Boton", args.value.results.recipe.ingredientLines[i].toString())
                var idIng = reference.push().key
                var ing = Ingredient(idIng!!,args.value.results.recipe.ingredientLines[i].toString(), usuario.email)
                reference.child(idIng!!).setValue(ing)
                //myRef.setValue(ing.toString())

            }




        }

    }
}