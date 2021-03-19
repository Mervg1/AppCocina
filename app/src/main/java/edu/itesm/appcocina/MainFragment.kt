package edu.itesm.appcocina

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonPerfil.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_perfil)
        }
        buttonSuper.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_listaSuper)
        }
        buttonRecetas.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_recetas)
        }
    }
}