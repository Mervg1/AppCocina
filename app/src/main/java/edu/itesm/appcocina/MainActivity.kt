package edu.itesm.appcocina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_mis_recetas.*

class MainActivity : AppCompatActivity() {
    //private lateinit var misRecetasAdapter: MisRecetasAdapter
    //private lateinit var receta : ArrayList<Receta>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initRecycler()
    }

    /*fun initRecycler(){
        receta = ArrayList<Receta>()
        misRecetasAdapter = MisRecetasAdapter(receta)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = misRecetasAdapter
        for (i in 0..30){

        }
        


    }*/
}