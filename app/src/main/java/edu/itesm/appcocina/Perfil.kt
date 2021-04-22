package edu.itesm.appcocina

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


data class Perfil(val nombre: String?, val apellidos: String?, val email: String?){
    constructor() : this("","","")
}