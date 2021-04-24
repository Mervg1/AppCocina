package edu.itesm.appcocina

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_mis_recetas.*
import kotlinx.android.synthetic.main.renglon_recetas_layout.*
import kotlinx.coroutines.delay


class MisRecetas : Fragment() {

    private val url = "https://api.edamam.com/search?q=all&app_id=7721e889&app_key=33ebd1f304fac28cd7809a27ade6ce9b&imagesize=thumbnail"
    val recetas = mutableListOf<Receta>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i("This is the info of URL: ", url)

        return inflater.inflate(R.layout.fragment_mis_recetas, container, false)
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initInteractions()
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createData()

    }

   // private lateinit var recetas : ArrayList<Receta>

    /*fun initInteractions(){
        recetas = ArrayList<Receta>()
        recetas.add(Receta("Prueba", "hola",R.drawable.logofondo))

        val recetaa = recetas[0]
        Log.i("Esto es receta",recetaa.toString())
        //imageView.setImageResource(recetaa.imagen)
        //name.text = recetaa.name
        //labels.text = recetaa.labels

        Log.i("esto es name", recetaa.name)
        name.text = recetaa.name

    }*/


    fun createData(){

        val requestQueue = Volley.newRequestQueue(activity)
        val peticion = JsonObjectRequest(Request.Method.GET,url,null, Response.Listener {
            val jsonArray  = it

            val recipes = jsonArray.getJSONArray("hits")

            for (i in 0 until recipes.length()){
                val re = recipes.getJSONObject(i)

                val recipeInfoData = re.getJSONObject("recipe")

                val label = recipeInfoData.getString("label")
                val image = recipeInfoData.getString("image")
                val mientras = recipeInfoData.getString("source")
                recetas.add(Receta(label, mientras, image))

            }

            recyclerview.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = LinearLayoutManager(activity)
                // set the custom adapter to the RecyclerView

                adapter = MisRecetasAdapter(recetas)
            }
        }, Response.ErrorListener {
            Toast.makeText(activity,"Error al leer los datos json!", Toast.LENGTH_LONG).show()
        })

        requestQueue.add(peticion)


    }


}


