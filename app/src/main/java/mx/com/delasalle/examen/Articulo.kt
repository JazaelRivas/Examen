package mx.com.delasalle.examen

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
class Articulo (
    var id: Int,
    var autor: Int,
    var titulo: String,
    var articuloText: String,
    var imagen: Int,
    var userLikes: Array<User> = arrayOf()
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
class lstArticles(
    var lstArticulos: Array<Articulo> = arrayOf()
) : Parcelable {

    fun add(articles: Articulo) {
        lstArticulos = arrayOf(*lstArticulos, articles)
    }

    fun delete(articles: Articulo) {
        val auxArr = lstArticulos.filter { it.id != articles.id }
        lstArticulos = auxArr.toTypedArray()
    }

    fun update(articles: Articulo) {
        val auxArr = lstArticulos.filter { it.id != articles.id }
        lstArticulos = auxArr.toTypedArray()
        articles.id = if ( lstArticulos.size == 0 ) 0 else lstArticulos[lstArticulos.size-1].id++
        add(articles)
    }


}