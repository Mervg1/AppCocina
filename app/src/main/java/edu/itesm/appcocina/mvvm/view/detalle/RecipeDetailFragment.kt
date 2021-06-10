package edu.itesm.appcocina.mvvm.view.detalle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import edu.itesm.appcocina.databinding.FragmentRecipeDetailBinding
import edu.itesm.appcocina.model.Ingredient
import kotlinx.android.synthetic.main.fragment_recipe_detail.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val args = navArgs<RecipeDetailFragmentArgs>()
    private lateinit var binding : FragmentRecipeDetailBinding
    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    private lateinit var myRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

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

        creaIngredients()



        url.setOnClickListener {

            val intento = Intent(Intent.ACTION_VIEW, Uri.parse(args.value.results.recipe.url.toString()))
            startActivity(intento)
            
        }

        Cart.setOnClickListener {
           addLista()
            /*if (bind.correo.text.isNotEmpty() && bind.password.text.isNotEmpty() && bind.nombre.text.isNotEmpty() && bind.apellidos.text.isNotEmpty()){
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

                }*/
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecipeDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecipeDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun creaIngredients(){
        binding.ingredients.layoutManager = LinearLayoutManager(activity)


        //binding.recyclerView.adapter = RecipeAdapter{
       /* binding.ingredients.adapter = IngredientsAdapter{
            val intent = Intent(activity, HomeActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }*/

        ingredients.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView

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