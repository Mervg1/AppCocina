package edu.itesm.appcocina.mvp.contract

interface Contract {
    interface View{
        fun setMensaje(mensaje:String)
    }

    interface Model{
        interface OnFinish{
            fun onFinished(mensaje: String)
        }
        fun getSiguienteMensaje(onFinishListener:OnFinish)
        fun getData()
    }

    interface Presenter{
        fun onButtonClick()
        fun getIng()
    }
}