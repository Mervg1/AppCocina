package edu.itesm.appcocina

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import edu.itesm.appcocina.ml.Model
import kotlinx.android.synthetic.main.fragment_image_recognition.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import kotlin.math.round


class ImageRecognition : Fragment() {

    lateinit var bitmap :Bitmap
    lateinit var img:ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_recognition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            img.setImageURI(uri)
            bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, uri!!))
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true)
        }
        seleccionarButton.setOnClickListener {
            img = imagen_analizar
            getContent.launch("image/*")
            analizarButton.isEnabled = true
            analizarButton.isClickable = true
        }

        analizarButton.setOnClickListener{
            var resized = Bitmap.createScaledBitmap(bitmap,224,224,true)
            var model = Model.newInstance(requireContext())
            var inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1,224,224,3), DataType.UINT8)
            var tBuffer = TensorImage.fromBitmap(resized)
            var byteBuffer = tBuffer.buffer
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val output = outputs.probabilityAsCategoryList
            texto_analizar.text = ""
            var texto = ""
            var max = 0.0
            for (alimento in output){
                if (alimento.score*100 > max){
                    max = String.format("%.3f",alimento.score*100).toDouble()
                    texto = alimento.label
                }
            }
            texto_analizar.text = texto + " con una certeza de " + max + " %"
            model.close()
        }
    }

}