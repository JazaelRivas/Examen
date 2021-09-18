package mx.com.delasalle.examen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView


class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var usrIcon: ImageView
    lateinit var usrChange: EditText
    lateinit var usrPassw: EditText
    lateinit var loginBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entrada, container, false)
    }
    private val wtch = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            usarios.forEach {
                if ( it.nickname == p0.toString() ) {
                    iconUser.setImageResource(it.avatar)
                    edtPass.isEnabled = false
                    btnLogin.isEnabled = false
                } else {
                    iconUser.setImageResource(R.drawable.ic_launcher_foreground)
                    edtPass.isEnabled = true
                    btnLogin.isEnabled = true
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }

    private fun views(){
        usrIcon = requireView().findViewById(R.id.usrIcon)
        usrChange = requireView().findViewById(R.id.usrChange)
        usrPassw = requireView().findViewById(R.id.usrPassw)
        loginBtn = requireView().findViewById(R.id.loginbth)
    }


}