package mx.com.delasalle.examen

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.moshi.Moshi

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    var PREF = "PREFERENDE_DOC_REF"
    var ART_REF = "ARTIC"
    var US_REF = "USER"
    var moshi = Moshi.Builder().build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE)

        supportFragmentManager.beginTransaction().add(R.id.frame, EntradaFragment()).commit()

    }
}