package nonthpawit.borntodev.hackhack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btnNavigationOnClick(view: View){
        //Navigate from MainActivity to SecondActivity
        var ReadDataIntent = Intent(this,ReadData::class.java)
        startActivity(ReadDataIntent)
    }

    fun btnCameraOnClick(view: View){
        //Navigate from MainActivity to SecondActivity
        var MainActivity2Intent = Intent(this,MainActivity2::class.java)
        startActivity(MainActivity2Intent)
    }
}

