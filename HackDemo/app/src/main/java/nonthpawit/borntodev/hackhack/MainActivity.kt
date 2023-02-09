package nonthpawit.borntodev.hackhack

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView


class MainActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<AppCompatTextView>(R.id.textView2)
        val imgBtt = findViewById<AppCompatImageButton>(R.id.imageButton8)

        timer = object : CountDownTimer(5_000, 1){
            override fun onTick(remaining: Long) {
                textView.text = remaining.toString()
            }

            override fun onFinish() {
                textView.text = "Done"
            }
        }
    }



    fun btnTimerOnClick(view: View){
        timer.start()
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

    fun btnMedLeftOnClick(view: View){
        //Navigate from MainActivity to MedLeft
        var MedLeftIntent = Intent(this,MedLeft::class.java)
        startActivity(MedLeftIntent)
    }

    fun btnMedRightOnClick(view: View){
        //Navigate from MainActivity to MedRight
        var MedRightIntent = Intent(this,MedRight::class.java)
        startActivity(MedRightIntent)
    }

    fun btnAllMedOnClick(view: View){
        //Navigate from MainActivity to MedRight
        var AllMedIntent = Intent(this,AllMed::class.java)
        startActivity(AllMedIntent)
    }
}

























