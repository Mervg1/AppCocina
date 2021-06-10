package edu.itesm.appcocina.mvvm.Service
import edu.itesm.appcocina.model.RecipeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {


    @GET("search?q=all&app_id=7721e889&app_key=33ebd1f304fac28cd7809a27ade6ce9b&imagesize=thumbnail")
    fun getARecipe(@Query("calories") calories: String, @Query("health") health : String, @Query("diet") diet : String, @Query("cuisineType") cuisineType : String): Call<RecipeResponse>

    @GET("{query}")
    fun getARecipeList(@Path("query") queries: String): Call<RecipeResponse>

    @GET("search?q=all&app_id=7721e889&app_key=33ebd1f304fac28cd7809a27ade6ce9b&imagesize=thumbnail")
    fun getRecipeList(): Call<RecipeResponse>

}