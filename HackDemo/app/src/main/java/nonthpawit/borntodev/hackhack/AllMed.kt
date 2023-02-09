package nonthpawit.borntodev.hackhack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AllMed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_med)
    }

    fun btnMed1OnClick(view: View){
        //Navigate from MainActivity to MedLeft
        var medas1Intent = Intent(this,medas1::class.java)
        startActivity(medas1Intent)
    }
    fun btnMed2OnClick(view: View){
        //Navigate from MainActivity to MedLeft
        var medkao2Intent = Intent(this,medkao2::class.java)
        startActivity(medkao2Intent)
    }
    fun btnMed3OnClick(view: View){
        //Navigate from MainActivity to MedLeft
        var medpep3Intent = Intent(this,medpep3::class.java)
        startActivity(medpep3Intent)
    }
    fun btnMed4OnClick(view: View){
        //Navigate from MainActivity to MedLeft
        var medcar4Intent = Intent(this,medcar4::class.java)
        startActivity(medcar4Intent)
    }
}

