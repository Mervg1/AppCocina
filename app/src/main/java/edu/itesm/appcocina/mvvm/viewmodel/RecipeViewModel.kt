package edu.itesm.appcocina.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.itesm.appcocina.mvvm.Service.RecipeService
import edu.itesm.appcocina.model.RecipeResponse
import edu.itesm.appcocina.model.RecipeResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeViewModel() : ViewModel() {



    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.edamam.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()




    private val service: RecipeService = retrofit.create(RecipeService::class.java)

    val recipeList = MutableLiveData<List<RecipeResult>>()

    fun getRecipeList(){
        val call = service.getRecipeList()
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                response.body()?.hits?.let { lista ->
                    recipeList.postValue(lista)
                }

            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                call.cancel()
            }

        })

    }

    fun getARecipe(calories : String, diet : String, health : String, cuisineType : String){
        val call = service.getARecipe(calories, health, diet, cuisineType)
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                response.body()?.hits?.let { lista ->
                    recipeList.postValue(lista)
                }

            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                call.cancel()
            }

        })

    }

    fun getARecipeList(query : String){
        val call = service.getARecipeList(query)
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                response.body()?.hits?.let { lista ->
                    recipeList.postValue(lista)
                }

            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                call.cancel()
            }

        })

    }

}