package nonthpawit.borntodev.hackhack

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import nonthpawit.borntodev.hackhack.databinding.ActivityReadDataBinding
import java.util.Calendar


class ReadData : AppCompatActivity() {
    var binding: ActivityReadDataBinding? = null
    var reference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_data)
        binding = ActivityReadDataBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.readdataBtn.setOnClickListener(View.OnClickListener {
            val username: String = binding!!.etusername.text.toString()
            if (username.isNotEmpty()) {
                readData(username)
            } else {
                Toast.makeText(this@ReadData, "PLease Enter Username", Toast.LENGTH_SHORT).show()
            }
        })



    }

    private fun readData(username: String) {
        reference = FirebaseDatabase.getInstance().getReference("Users")
        reference!!.child(username).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result.exists()) {
                    Toast.makeText(this@ReadData, "Successfully Read", Toast.LENGTH_SHORT).show()
                    val dataSnapshot = task.result
                    val firstName = dataSnapshot.child("firstName").value.toString()
                    val lastName = dataSnapshot.child("lastName").value.toString()
                    val age = dataSnapshot.child("age").value.toString()
                    binding?.tvFirstName?.text = firstName
                    binding?.tvLastName?.text = lastName
                    binding?.tvAge?.text = age
                } else {
                    Toast.makeText(this@ReadData, "User Doesn't Exist", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@ReadData, "Failed to read", Toast.LENGTH_SHORT).show()
            }
        }
    }

}