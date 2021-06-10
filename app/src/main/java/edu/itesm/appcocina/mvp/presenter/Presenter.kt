package edu.itesm.appcocina.mvp.presenter

import edu.itesm.appcocina.mvp.contract.Contract

class Presenter(private var listaSuperView : Contract.View?,
                private var model: Contract.Model) : Contract.Presenter, Contract.Model.OnFinish {


    override fun onButtonClick() {
        model.getSiguienteMensaje(this)
        //model.ayuda()

    }

    override fun onFinished(mensaje: String) {
        if(listaSuperView != null){
            listaSuperView!!.setMensaje(mensaje)
        }

    }

    override fun getIng() {
        model.getData()

    }


}