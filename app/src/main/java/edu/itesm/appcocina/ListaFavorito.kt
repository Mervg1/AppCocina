package edu.itesm.appcocina

import edu.itesm.appcocina.model.RecipeInfo

data class ListaFavorito(val size: Int, val lista: List<String>){
    constructor() : this(0, emptyList())
}