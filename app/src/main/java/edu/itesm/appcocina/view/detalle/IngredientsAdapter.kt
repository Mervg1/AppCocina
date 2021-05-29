package edu.itesm.appcocina.view.detalle

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.itesm.appcocina.databinding.RenglonIngredientsBinding
import edu.itesm.appcocina.databinding.RenglonRecetasLayoutBinding
import edu.itesm.appcocina.model.RecipeResult
import edu.itesm.appcocina.view.lista.RecipeAdapter
import edu.itesm.appcocina.view.lista.RecipeFragmentDirections

class IngredientsAdapter(var ingList : List<String>) :
    RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>()  {
    //var ingredientList: List<RecipeResult> = emptyList<RecipeResult>()

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


    }
}