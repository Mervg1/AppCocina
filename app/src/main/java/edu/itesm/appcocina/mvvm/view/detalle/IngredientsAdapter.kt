package edu.itesm.appcocina.mvvm.view.detalle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import edu.itesm.appcocina.databinding.FragmentRecipeDetailBinding
import edu.itesm.appcocina.databinding.RenglonIngredientsBinding
import edu.itesm.appcocina.databinding.RenglonListaSuperBinding
import edu.itesm.appcocina.model.Ingredient

class IngredientsAdapter(var ingList : List<String>) :
    RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>()  {
    //var ingredientList: List<RecipeResult> = emptyList<RecipeResult>()
    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    private lateinit var myRef : DatabaseReference
    private lateinit var binding : FragmentRecipeDetailBinding
    fun setData(list: List<String>){
        ingList = list
        notifyDataSetChanged()
    }

    inner class IngredientsViewHolder(val binding: RenglonIngredientsBinding): RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val binding = RenglonIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return IngredientsViewHolder(binding)
    }

    override fun getItemCount() = ingList.size

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val recipe = ingList[position]
        holder.binding.ingredientItem.text = recipe


        //addLista(recipe)

    }

    fun addLista(recipe : String){

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("ingredients")
        myRef = database.getReference("lista_ingredientes")

        val usuario = Firebase.auth.currentUser


        binding.Cart.setOnClickListener {
            if(ingList.size > 0){

                var ing = Ingredient(myRef.key.toString(),recipe.toString(), usuario.email)
                var idIng = reference.push().key
                reference.child(idIng!!).setValue(ing)
                myRef.setValue(ing.toString())

            }
        }

    }

}