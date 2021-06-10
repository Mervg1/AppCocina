package edu.itesm.appcocina.mvvm.view.lista

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.itesm.appcocina.databinding.RenglonRecetasLayoutBinding
import edu.itesm.appcocina.model.RecipeResult

class RecipeAdapter(val recipeClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    var recipeList: List<RecipeResult> = emptyList<RecipeResult>()

    fun setData(list: List<RecipeResult>){
        recipeList = list
        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(val binding: RenglonRecetasLayoutBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RenglonRecetasLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount() = recipeList.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.binding.name.text = "${recipe.recipe.label}"
        holder.binding.labels.text = "Calories: ${recipe.recipe.calories.toString()}"
        Picasso.get().load(recipe.recipe.image).into(holder.binding.imagen)
        Log.i("Esto es recipe ",recipeList.toString())

        holder.itemView.setOnClickListener{
            val action = RecipeFragmentDirections.actionRecipeFragmentToRecipeDetailFragment(recipe)
            holder.itemView.findNavController().navigate(action)
        }
    }
}