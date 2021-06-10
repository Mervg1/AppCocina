package edu.itesm.appcocina.mvp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import edu.itesm.appcocina.R
import edu.itesm.appcocina.databinding.FragmentListaSuperBinding
import edu.itesm.appcocina.databinding.FragmentRecipeBinding
import edu.itesm.appcocina.model.Ingredient
import edu.itesm.appcocina.mvp.contract.Contract
import edu.itesm.appcocina.mvp.model.Model
import edu.itesm.appcocina.mvp.presenter.Presenter
import edu.itesm.appcocina.mvvm.view.detalle.IngredientsAdapter
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import java.util.ArrayList


class ListaSuper : Fragment(), Contract.View {
    private lateinit var binding: FragmentListaSuperBinding
    //private lateinit var viewModel : ListaSuperViewModel
    private var presenter : Contract.Presenter? = null
    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    private lateinit var user : FirebaseUser
    private lateinit var currId : String
    var lista = ArrayList<Ingredient>()
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_lista_super, container, false)
        binding = FragmentListaSuperBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = Presenter(this, Model())
        //Log.i("entro a ayuda", "ayuda entro")
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("message")
        user = FirebaseAuth.getInstance().currentUser
        ayuda()
        binding.button!!.setOnClickListener {
            presenter!!.onButtonClick()
        }

        Log.i("prse", presenter!!.getIng().toString())

        getData()
    }


    override fun setMensaje(mensaje: String) {
        binding.textView12!!.text = mensaje
    }

    /*private fun creaIngredients(){
        binding.ingredientes.layoutManager = LinearLayoutManager(activity)


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

    }*/
    private fun ayuda(){

        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("entro a ayuda", "ayuda entro2")
                Log.i("valoresIngSS", snapshot.getValue().toString())

                /*for (ing in snapshot.children) {

                    var objeto = ing.getValue(Ingredient::class.java)
                    /*if(usuarioActivo.uid == objeto?.usuario){
                        lista.add(objeto as ComicRT)
                    }*/
                    Log.i("valoresIng", objeto.toString())
                }*/
                /*if (lista.isEmpty()){
                    Toast.makeText(activity, "Your cart is empty", Toast.LENGTH_SHORT).show()
                }
                recyclerView.apply{
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CarritoAdapter(lista)
                }*/
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("valoresIngError", "objeto.toString()")
            }
        })

    }

    fun getData(){

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
                if (lista.isEmpty()){
                    Toast.makeText(activity, "Your cart is empty", Toast.LENGTH_SHORT).show()
                }

                binding.ingredientes.apply{
                    layoutManager = LinearLayoutManager(activity)
                    adapter = ListaSuperAdapter(lista)
                }

                //adapter = ListaSuperAdapter(lista)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("valoresIngError", "objeto.toString()")
            }
        })



    }


}