package mx.com.delasalle.examen

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class User (
    var id: Int,
    var usuario: String,
    var usuType: UsrType = UsrType.AUTOR,
    var password: String,
    var usuFoto: Int,

    ) : Parcelable{
        companion object{
            val lstUser = arrayOf(
                User(1,"Jaza",UsrType.AUTOR,"123",R.drawable.m1),
                User(2,"Juan",UsrType.AUTOR,"123",R.drawable.m2),
                User(3,"Hiram",UsrType.LECTOR,"123",R.drawable.m3),
                User(4,"Jeremy",UsrType.LECTOR,"123",R.drawable.m4),
            )

        }

    }