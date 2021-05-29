package edu.itesm.appcocina.view.detalle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import edu.itesm.appcocina.HomeActivity
import edu.itesm.appcocina.MisRecetasAdapter
import edu.itesm.appcocina.R
import edu.itesm.appcocina.RegisterActivity
import edu.itesm.appcocina.databinding.FragmentRecipeBinding
import edu.itesm.appcocina.databinding.FragmentRecipeDetailBinding
import edu.itesm.appcocina.view.lista.RecipeAdapter
import kotlinx.android.synthetic.main.fragment_mis_recetas.*
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
}