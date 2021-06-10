package edu.itesm.appcocina.mvp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.itesm.appcocina.databinding.FragmentRecipeDetailBinding
import edu.itesm.appcocina.databinding.RenglonIngredientsBinding
import edu.itesm.appcocina.databinding.RenglonListaSuperBinding
import edu.itesm.appcocina.model.Ingredient
import edu.itesm.appcocina.mvvm.view.detalle.IngredientsAdapter

class ListaSuperAdapter(var listaSuper : List<Ingredient>) :
    RecyclerView.Adapter<ListaSuperAdapter.ListaSuperViewHolder>() {
    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    private lateinit var myRef : DatabaseReference
    private lateinit var binding : FragmentRecipeDetailBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var user : FirebaseUser

    inner class ListaSuperViewHolder(val binding: RenglonListaSuperBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaSuperAdapter.ListaSuperViewHolder {
        val binding = RenglonListaSuperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListaSuperViewHolder(binding)
    }

    override fun getItemCount() = listaSuper.size

    override fun onBindViewHolder(holder: ListaSuperAdapter.ListaSuperViewHolder, position: Int) {
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("ingredients")

        val recipe = listaSuper[position]
        holder.binding.infoIngrediente.text = recipe.ingredient
        //Log.i("Info del Adapter", recipe.toString())

        holder.binding.checkBox.setOnClickListener {
            Log.i("checkd", recipe.id)
            var idIng = recipe.id
            reference.child(idIng!!).removeValue()
        }


        //addLista(recipe)

    }
}