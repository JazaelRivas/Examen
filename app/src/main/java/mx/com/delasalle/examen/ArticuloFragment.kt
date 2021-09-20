package mx.com.delasalle.examen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible

class ArticuloFragment : Fragment(R.layout.fragment_articulo) {

    lateinit var imgVArticle: ImageView
    lateinit var buttonNext: Button
    lateinit var buttonBack: Button
    lateinit var edtTxtTitle: EditText
    lateinit var edtTxtContent: EditText
    lateinit var buttonDo: Button

    lateinit var article: Articulo

    var index = 0
    lateinit var lst2: lstArticles
    lateinit var main: MainActivity
    lateinit var user2: User

    var images = arrayOf(
        R.drawable.ic_bitcoin,
        R.drawable.ic_game_console,
        R.drawable.ic_ghost,
        R.drawable.ic_nvidia,
        R.drawable.ic_pikachu
    )

    private fun Lc() {
        buttonDo.isVisible = false
        buttonNext.isVisible = false
        buttonBack.isVisible = false
        edtTxtTitle.isEnabled = false
        edtTxtContent.isEnabled = false
    }

    override fun onResume() {
        super.onResume()

        lst2 = requireArguments().getParcelable("class")!!
        main = activity as MainActivity

        runViews()
    }

    private fun runViews() {
        val tipo = requireArguments().getString("tipo")
        imgVArticle = requireView().findViewById(R.id.carousel)
        buttonNext = requireView().findViewById(R.id.btnContinue)
        buttonBack = requireView().findViewById(R.id.btnBack)
        edtTxtTitle = requireView().findViewById(R.id.editTitulo)
        edtTxtContent = requireView().findViewById(R.id.editContenidoArticulo)
        buttonDo = requireView().findViewById(R.id.btnGuardar)

        buttonBack.setOnClickListener {
            if ( index == 0 )
                index = images.size-1
            else
                index--
            imgVArticle.setImageResource(images[index])
        }

        buttonNext.setOnClickListener {
            if ( index == (images.size-1) )
                index = 0
            else
                index++
            imgVArticle.setImageResource(images[index])
        }
        if ( tipo == "crear" ) {
            user2 = requireArguments().getParcelable("user")!!
            buttonDo.setOnClickListener {
                if ( edtTxtTitle.text.isEmpty() || edtTxtContent.text.isEmpty() )
                    Toast.makeText(context, "No deje campos vacios", Toast.LENGTH_LONG).show()
                else {
                    var id = 0
                    if ( lst2.lstArticulos.size > 0 )
                        id = lst2.lstArticulos[lst2.lstArticulos.size-1].id
                    val newArticle = Articulo(
                        ++id,
                        user2.id,
                        edtTxtTitle.text.toString(),
                        edtTxtContent.text.toString(),
                        images[index]
                    )
                    lst2.add(newArticle)
                    main.sharedPreferences.edit().putString(main.ART_REF, main.moshi.adapter(lstArticles::class.java).toJson(lst2)).commit()
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
            imgVArticle.setImageResource(images[index])
        } else if ( tipo == "actualizar" ) {
            article = requireArguments().getParcelable("articulo")!!
            edtTxtTitle.setText(article.titulo.toString())
            edtTxtContent.setText(article.articuloText.toString())

            index = images.indexOf(article.imagen)

            imgVArticle.setImageResource(article.imagen)

            buttonDo.setOnClickListener {
                if ( edtTxtTitle.text.isEmpty() || edtTxtContent.text.isEmpty() )
                    Toast.makeText(context, "No deje campos vacios", Toast.LENGTH_LONG).show()
                else {
                    article.titulo = edtTxtTitle.text.toString()
                    article.articuloText = edtTxtContent.text.toString()
                    article.imagen = images[index]
                    lst2.update(article)
                    main.sharedPreferences.edit().putString(main.ART_REF, main.moshi.adapter(lstArticles::class.java).toJson(lst2)).commit()
                    main.supportFragmentManager.popBackStack()
                }
            }
        } else if ( tipo == "ver" )  {
            user2 = requireArguments().getParcelable("user")!!
            article = requireArguments().getParcelable("articulo")!!
            Lc()
        }
    }

}