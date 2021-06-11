package edu.itesm.appcocina

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.recyclerview.widget.LinearLayoutManager
import edu.itesm.appcocina.model.RecipeResult
import kotlinx.android.synthetic.main.fragment_mis_recetas.*

class MainActivity : AppCompatActivity() {
    //private lateinit var misRecetasAdapter: MisRecetasAdapter
    //private lateinit var receta : ArrayList<Receta>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initRecycler()
    }

    public fun logout(view: View){
        Firebase.auth.signOut()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}