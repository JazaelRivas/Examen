package mx.com.delasalle.examen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User (
    var usuario: String,
    var usuType: UsrType = UsrType.AUTOR,
    var password: String,
    var usuFoto: Int,

    ) : Parcelable{
        companion object{
        val lstUser = arrayOf(
            User("")
        )

        }

    }