package mx.com.delasalle.examen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView


class EntradaFragment : Fragment(R.layout.fragment_entrada) {
   lateinit var usrIcon: ImageView
   lateinit var usrEdt: EditText
   lateinit var edtPass: EditText
   lateinit var log: Button

   override fun onResume() {
      super.onResume()
      runViews()
      events()
   }

   private fun events(){
      TODO()
   }

   private fun runViews(){
      usrIcon = requireView().findViewById(R.id.usrIcon)
      usrEdt = requireView().findViewById(R.id.usrEdt)
      edtPass = requireView().findViewById(R.id.edtPass)
      log = requireView().findViewById(R.id.log)
   }
}