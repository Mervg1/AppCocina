package edu.itesm.appcocina

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_recetas.*


class Recetas : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recetas, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonMisRecetas.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_recetas_to_misRecetas)
        }
        buttonFavoritos.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_recetas_to_favoritos)
        }
    }
}