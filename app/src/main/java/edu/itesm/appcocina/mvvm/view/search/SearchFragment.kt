package edu.itesm.appcocina.mvvm.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import edu.itesm.appcocina.databinding.FragmentSearchBinding
import edu.itesm.appcocina.model.QueryRecipe
import kotlinx.android.synthetic.main.fragment_search.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentSearchBinding
    //private lateinit var info : List<String>
    lateinit var infoRecipe: List<QueryRecipe>
    lateinit var qr : QueryRecipe

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
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        creaIngredients()
        buttonSearchNutri.setOnClickListener(){
            //Navigation.findNavController(view).navigate(R.id.action_recetas_to_misRecetas)
            /*infoRecipe = ArrayList<QueryRecipe>()
            (infoRecipe as ArrayList<QueryRecipe>).add(QueryRecipe(binding.spnElementos.selectedItem.toString(), binding.spnElementosDiet.selectedItem.toString(), binding.spnElementosHealth.selectedItem.toString(), binding.spnElementosCT.selectedItem.toString()))
            Log.i("Calories", binding.spnElementos.selectedItem.toString())*/
            //info = listOf(binding.spnElementos.selectedItem.toString(), binding.spnElementosDiet.selectedItem.toString(), binding.spnElementosHealth.selectedItem.toString(), binding.spnElementosCT.selectedItem.toString())

            qr = QueryRecipe(binding.spnElementos.selectedItem.toString(), binding.spnElementosDiet.selectedItem.toString(), binding.spnElementosHealth.selectedItem.toString(), binding.spnElementosCT.selectedItem.toString())

            val action = SearchFragmentDirections.actionSearchFragmentToRecipeFragment(qr)
            view.findNavController().navigate(action)

            //Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_recipeFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun creaIngredients(){

        val spinner = binding.spnElementos
        val listCalories = mutableListOf<String>("500-1000", "1001-1500", "1501-3000", "3000-5000")
        val adaptador = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listCalories) }
        spinner.adapter = adaptador

        val spinnerHealth = binding.spnElementosHealth
        val listHealth = mutableListOf<String>("immuno-supportive", "gluten-free", "kosher", "low-sugar", "peanut-free")
        val adaptadorHealt = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listHealth) }
        spinnerHealth.adapter = adaptadorHealt

        val spinnerDiet = binding.spnElementosDiet
        val listDiet = mutableListOf<String>("balanced", "high-fiber", "high-protein", "low-carb", "low-fat", "low-sodium")
        val adaptadorDiet = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listDiet) }
        spinnerDiet.adapter = adaptadorDiet

        val spinnerCT = binding.spnElementosCT
        val listCT = mutableListOf<String>("american", "asian", "indian", "chinese", "french", "mexican")
        val adaptadorCT = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listCT) }
        spinnerCT.adapter = adaptadorCT

    }
}