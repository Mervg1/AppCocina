package edu.itesm.appcocina.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import edu.itesm.appcocina.R
import edu.itesm.appcocina.databinding.FragmentSearchBinding
import edu.itesm.appcocina.databinding.FragmentSearchNutriInfoBinding
import kotlinx.android.synthetic.main.fragment_recetas.*
import kotlinx.android.synthetic.main.fragment_search.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchNutriInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchNutriInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentSearchNutriInfoBinding

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
        // Inflate the layout for this fragment
        binding = FragmentSearchNutriInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        creaInfo()

        buttonSearchNutri.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_searchNutriInfoFragment_to_nutriInfoFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchNutriInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchNutriInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun creaInfo(){

        val spinner = binding.amount
        val listAmount = mutableListOf<String>("1", "2", "3", "4","5","6","7","8","9","10")
        val adaptadorAmount = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listAmount) }
        spinner.adapter = adaptadorAmount

        val spinnerMeasure = binding.mesure
        val listMeasure = mutableListOf<String>("small", "medium", "large", "cup", "spoon")
        val adaptadorMeasure = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listMeasure) }
        spinnerMeasure.adapter = adaptadorMeasure

        val spinnerIngredient = binding.ingredient
        val listIngredient = mutableListOf<String>("apple", "sugar", "banana", "flour", "milk", "salt")
        val adaptadorIngredient = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listIngredient) }
        spinnerIngredient.adapter = adaptadorIngredient


    }
}