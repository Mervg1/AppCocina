package edu.itesm.appcocina

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class MisRecetasAdapter(private val recetas : List<Receta>) :
    RecyclerView.Adapter<MisRecetasAdapter.MisRecetasViewHolder>() {
    inner class MisRecetasViewHolder(renglon: View) : RecyclerView.ViewHolder(renglon){
        var name = renglon.findViewById<TextView>(R.id.name)
        var labels = renglon.findViewById<TextView>(R.id.labels)
        var imagen = renglon.findViewById<ImageView>(R.id.imagen_analizar)
    }

    //Crea el renglon
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisRecetasViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val renglon_layout = inflater.inflate(R.layout.renglon_recetas_layout, parent, false)
        return MisRecetasViewHolder(renglon_layout)
    }

    //Asocia datos con los elementos del renglon
    override fun onBindViewHolder(holder: MisRecetasViewHolder, position: Int) {
        val dato = recetas[position]
        val nombre = dato.name
        val labels = dato.labels
        val imagenes = dato.imagen
        //holder.imagen.setImageResource(dato.imagen)
        Picasso.get().load(dato.imagen).into(holder.imagen)
        holder.name.text = dato.name
        holder.labels.text = dato.labels
    }

    //Cuantos elementos tiene la lista
    override fun getItemCount(): Int {
        return recetas.size
    }
}