package nonthpawit.borntodev.hackhack

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import nonthpawit.borntodev.hackhack.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


class MainActivity2 : AppCompatActivity() {
    var result: TextView? = null
    var details: TextView? = null
    var imageView: ImageView? = null
    var imageSize = 224
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        result = findViewById(R.id.result)
        imageView = findViewById(R.id.imageView)
        details = findViewById(R.id.details)
        val picture1 = findViewById(R.id.button) as Button
        picture1.setOnClickListener(View.OnClickListener {
            // Launch camera if we have permission
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 1)
            } else {
                //Request camera permission if we don't have it.
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun classifyImage(image: Bitmap?) {
        try {
            val model: Model = Model.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            // get 1D array of 224 * 224 pixels in image
            val intValues = IntArray(imageSize * imageSize)
            image!!.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            var pixel = 0
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: Model.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray
            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf(
                "KaoKui Water",
                "Sara(Paracetamol)",
                "Paracetamol"
            )

            val truery = arrayOf(
                "ยาน้ำแก้ร้อนในเขากุย ยาน้ำแก้ร้อนในเขากุยเป็นยาตำรับแผนโบราณสูตรเฉพาะของอ้วยอันโอสถ  ผลิตจากสมุนไพรธรรมชาติ  ทีมีสรรพคุณในการแก้ร้อนใน  แผลร้อนใน  หรือสำหรับผู้ที่มีอาการปวดหัว  เป็นไข้  ตัวร้อน  ประกอบด้วยสมุนไพรหลายชนิด  อาทิเช่น  เขากุย เต็งซิม กั๊วกิง เลี้ยงเคี้ยว กิกแก้ กิมงิ่งฮวย ที่มีฤทธิ์เย็น  ช่วยในการลดไข้  ลดความร้อนในร่างกาย",
                "Sara(Paracetamol)",
                "เด็ก 10-15 มิลลิกรัม/กิโลกรัม ทุก 4-6 ชั่วโมง (หากจำเป็น) ไม่เกิน 5 ครั้งภายใน 24 ชั่วโมง ผู้ใหญ่ 500 มิลลิกรัม ทุก 4-6 ชั่วโมง ไม่เกิน 4,000 มิลลิกรัม ต่อวันไม่ควรใช้ยาเกินครั้งละ 500-1,000 มิลลิกรัม ต่อ 4-6 ชั่วโมง และไม่ควรเกิน 4,000 มิลลิกรัมต่อวัน ผลข้างเคียงท้องเสีย เหงื่อออกมากผิดปกติ เบื่ออาหาร คลื่นไส้หรืออาเจียน ปวดท้องอย่างรุนแรง "
            )
            result!!.text = classes[maxPos]
            var s = ""
            for (i in classes.indices) {
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100)
                when (classes[maxPos]) {
                    classes[0] -> details!!.text = truery[0]
                    classes[1] -> details!!.text = truery[2]
                    classes[2] -> details!!.text = truery[2]
                }
                }



            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            var image = data!!.extras!!["data"] as Bitmap?
            val dimension = Math.min(image!!.width, image.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            imageView!!.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
            classifyImage(image)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}