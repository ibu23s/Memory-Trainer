package de.fra_uas.fb2.mobiledevices.memorytrainer

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText = findViewById<EditText>(R.id.etName)
        val btnStart = findViewById<Button>(R.id.btnStart)

        btnStart.setOnClickListener {
            EasyLevelGame.MyApplication.name = nameEditText.text.toString()
            val intent = Intent(this, ChooseLevel::class.java)
            intent.putExtra("name", EasyLevelGame.MyApplication.name)
            startActivity(intent)
        }
    }
}





