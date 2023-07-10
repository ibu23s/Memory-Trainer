package de.fra_uas.fb2.mobiledevices.memorytrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText = findViewById<EditText>(R.id.etName)
        val btnStart = findViewById<Button>(R.id.btnStart)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        btnStart.setOnClickListener {
            val name = nameEditText.text.toString()
            editor.putString("name", name)
            editor.apply()

            val intent = Intent(this, ChooseLevel::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }

    }
}

