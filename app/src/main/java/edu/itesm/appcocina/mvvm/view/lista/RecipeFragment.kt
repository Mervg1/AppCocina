package edu.itesm.appcocina.mvvm.view.lista

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import edu.itesm.appcocina.HomeActivity
import edu.itesm.appcocina.databinding.FragmentRecipeBinding
import edu.itesm.appcocina.mvvm.viewmodel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_mis_recetas.*


class RecipeFragment : Fragment() {

    private lateinit var binding : FragmentRecipeBinding
    private lateinit var viewModel : RecipeViewModel
    private val args = navArgs<RecipeFragmentArgs>()
    private var query : String = String()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        query = "search?q=all&app_id=7721e889&app_key=33ebd1f304fac28cd7809a27ade6ce9b&imagesize=thumbnail&calories=${args.value.queryRecipe.calories.toString()}&diet=${args.value.queryRecipe.diet.toString()}"
        Log.i("InfoSearch", query)
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        creaUI()
    }


    private fun creaUI() {
        binding.recyclerviewRecipe.layoutManager = LinearLayoutManager(activity)


        //binding.recyclerView.adapter = RecipeAdapter{
        binding.recyclerviewRecipe.adapter = RecipeAdapter{
            val intent = Intent(activity, HomeActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }

        //viewModel.getRecipeList()
        viewModel.getARecipe(args.value.queryRecipe.calories.toString(), args.value.queryRecipe.diet.toString(),args.value.queryRecipe.health.toString(),args.value.queryRecipe.cuisineType.toString())
        //viewModel.getARecipeList(query)

        viewModel.recipeList.observe(viewLifecycleOwner, Observer { recipeList ->
            (binding.recyclerviewRecipe.adapter as RecipeAdapter).setData(recipeList)
        })

    }


}