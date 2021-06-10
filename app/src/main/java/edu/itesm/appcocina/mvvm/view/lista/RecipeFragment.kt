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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecipeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecipeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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