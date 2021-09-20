package mx.com.delasalle.examen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible


class UserFragment : Fragment(R.layout.fragment_user) {

    lateinit var iconUser: ImageView
    lateinit var txtVUserType: TextView
    lateinit var txtVUsername: TextView
    lateinit var txtVCounter: TextView
    lateinit var buttonDlt: Button
    lateinit var buttonUpdt: Button
    lateinit var buttonCrt: Button
    lateinit var imgVArticle: ImageView
    lateinit var buttonNext: Button
    lateinit var buttonBack: Button
    lateinit var txtVTitle: TextView
    lateinit var logOut: Button

    lateinit var user: User
    lateinit var lst: lstArticles
    var index = 0
    lateinit var main: MainActivity
    lateinit var array: Array<Articulo>

    override fun onResume() {
        super.onResume()
        user = requireArguments().getParcelable("user")!!
        main = activity as MainActivity
        lst = getArticles()
        runViews()
    }

    private fun getArticles(): lstArticles {
        return main.sharedPreferences.getString(main.ART_REF, null)?.let {
            return@let try {
                main.moshi.adapter(lstArticles::class.java).fromJson(it)
            } catch (e: Exception) {
                lstArticles()
            }
        } ?: lstArticles()
    }

    private fun arAutor(): Array<Articulo> {
        return lst.lstArticulos.filter { it.autor == user.id }.toTypedArray()
    }


    private fun runViews() {
        if ( user.usuType == UsrType.AUTOR ) {
            array = arAutor()
        } else {
            array = lst.lstArticulos
        }

        iconUser = requireView().findViewById(R.id.imageViewArticle)
        txtVUserType = requireView().findViewById(R.id.usernam)
        txtVUsername = requireView().findViewById(R.id.tipousuario)
        txtVCounter = requireView().findViewById(R.id.CounterFav)
        buttonDlt = requireView().findViewById(R.id.btnDelete)
        buttonUpdt = requireView().findViewById(R.id.btnEditar)
        buttonCrt = requireView().findViewById(R.id.btnAgregar)
        imgVArticle = requireView().findViewById(R.id.imagen)
        buttonNext = requireView().findViewById(R.id.siguiente)
        buttonBack = requireView().findViewById(R.id.anterior)
        txtVTitle = requireView().findViewById(R.id.titleArticle)
        logOut = requireView().findViewById(R.id.logout)


        logOut.setOnClickListener {
            main.sharedPreferences.edit().remove(main.US_REF).commit()
            main.supportFragmentManager.beginTransaction().replace(R.id.frame, EntradaFragment()).commit()
        }


        iconUser.setImageResource(user.usuFoto)
        txtVUserType.text = user.usuType.toString()
        txtVUsername.text = user.usuario

        if ( array.size > 0 ) {
            txtVTitle.text = array[index].titulo
            imgVArticle.setImageResource(array[index].imagen)
        } else {
            txtVTitle.isVisible = false
            imgVArticle.isVisible = false
            buttonBack.isVisible = false
            if ( user.usuType == UsrType.AUTOR ) {
                buttonDlt.isVisible = false
                buttonUpdt.isVisible = false
            }
            buttonNext.isVisible = false
            requireView().findViewById<ImageView>(R.id.empty).isVisible = true
        }

        if ( user.usuType == UsrType.AUTOR ) {
            txtVCounter.text = array.size.toString()
        } else {
            var likes = 0
            array.forEach {
                it.userLikes.forEach {
                    if ( it.id == user.id )
                        likes++
                }
            }
            txtVCounter.text = likes.toString()
            buttonCrt.isVisible = false
            buttonDlt.isVisible = false
            buttonUpdt.isVisible = false
        }

        buttonBack.setOnClickListener {
            if ( array.size == 0 ) return@setOnClickListener
            if ( index == 0 )
                index = array.size-1
            else
                index--
            txtVTitle.text = array[index].titulo
            imgVArticle.setImageResource(array[index].imagen)
        }

        buttonNext.setOnClickListener {
            if ( array.size == 0 ) return@setOnClickListener
            if ( index == (array.size-1) )
                index = 0
            else
                index++
            txtVTitle.text = array[index].titulo
            imgVArticle.setImageResource(array[index].imagen)
        }

        buttonCrt.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame, ArticuloFragment().apply {
                arguments = Bundle().apply {
                    putString("tipo", "crear")
                    putParcelable("class", lst)
                    putParcelable("user", user)
                }
            }).addToBackStack(ArticuloFragment().tag).commit()
        }

        buttonUpdt.setOnClickListener {
            val i = index
            if ( array.size == 0 ) return@setOnClickListener
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame, ArticuloFragment().apply {
                arguments = Bundle().apply {
                    putString("tipo", "actualizar")
                    putParcelable("articulo", array[i])
                    putParcelable("class", lst)
                }
            }).addToBackStack(ArticuloFragment().tag).commit()
        }

        imgVArticle.setOnClickListener {
            if ( user.usuType == UsrType.LECTOR )
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame, ArticuloFragment().apply {
                    arguments = Bundle().apply {
                        putString("tipo", "ver")
                        putParcelable("articulo", array[index])
                        putParcelable("class", lst)
                        putParcelable("user", user)
                    }
                }).addToBackStack(ArticuloFragment().tag).commit()
        }

        buttonDlt.setOnClickListener {
            if ( array.size == 0 ) return@setOnClickListener
            lst.delete(array[index])
            index = 0
            main.sharedPreferences.edit().putString(main.ART_REF, main.moshi.adapter(lstArticles::class.java).toJson(lst)).commit()
            onResume()
        }

    }

}