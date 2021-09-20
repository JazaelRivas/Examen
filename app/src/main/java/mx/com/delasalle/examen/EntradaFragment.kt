package mx.com.delasalle.examen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener


class EntradaFragment : Fragment(R.layout.fragment_entrada) {
   lateinit var usrIcon: ImageView
   lateinit var usrEdt: EditText
   lateinit var edtPass: EditText
   lateinit var log: Button

   override fun onResume() {
      super.onResume()

      val main = activity as MainActivity
      val user = main.sharedPreferences.getString(main.US_REF, null)?.let {
         return@let try {
            main.moshi.adapter(User::class.java).fromJson(it)
         } catch (e: Exception) {
            null
         }
      }

      if ( user != null ) {
         println(user)
         main.supportFragmentManager.beginTransaction().replace(R.id.frame, UserFragment().apply {
            arguments = Bundle().apply {
               putParcelable("user", user)
            }
         })
      }

      runViews()
      events()
   }



   private fun events(){
      usrEdt.addTextChangedListener(wtch)
      val main = activity as MainActivity
      log.setOnClickListener {
         User.lstUser.forEach {
            if ( it.usuario == usrEdt.text.toString() && it.password == edtPass.text.toString() ) {
               main.sharedPreferences.edit().putString(main.US_REF, main.moshi.adapter(User::class.java).toJson(it)).commit()
               main.supportFragmentManager.beginTransaction().replace(R.id.frame, UserFragment().apply {
                  arguments = Bundle().apply {
                     putParcelable("user", it)
                  }
               }).commit()
            }
         }
      }
   }

   private val wtch = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

      }

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         var user: User? = null
         User.lstUser.forEach {
            if ( it.usuario == p0.toString() ) {
               user = it
            }
         }
         if ( user != null ) {
            log.isEnabled = true
            edtPass.isEnabled = true
            usrIcon.setImageResource(user!!.usuFoto)
         } else {
            log.isEnabled = false
            edtPass.isEnabled = false
         }
      }

      override fun afterTextChanged(p0: Editable?) {

      }

   }


   private fun runViews(){
      usrIcon = requireView().findViewById(R.id.avatar)
      usrEdt = requireView().findViewById(R.id.usernam)
      edtPass = requireView().findViewById(R.id.edtPass)
      log = requireView().findViewById(R.id.login)
   }
}