package edu.itesm.appcocina.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Recipe(
    val id: Int,
    val name: String,
    val labels: String,
    val imagen: String?

)

data class Ingredient(
    val id : String,
    val ingredient : String,
    val email : String
){
    constructor():this("","","")
}

@Parcelize
data class QueryRecipe(
    var calories : String,
    var diet : String,
    var health : String,
    var cuisineType : String
) : Parcelable

@Parcelize
data class RecipeResponse (
    val hits: List<RecipeResult>
): Parcelable

@Parcelize
data class RecipeResult (
    val recipe: RecipeInfo
): Parcelable

@Parcelize
data class RecipeInfo(
    var label: String,
    val calories: Double,
    var image : String,
    var ingredientLines : List<String>,
    var totalTime : Double,
    var url : String
): Parcelable

