package de.fra_uas.fb2.mobiledevices.memorytrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val btnbeenden = findViewById<Button>(R.id.buttonBeenden)
        val resultName = findViewById<TextView>(R.id.Resultname)
        val menge = findViewById<TextView>(R.id.mengeRichtigeFragen)

        val name = intent.getStringExtra("name")
        resultName.text = EasyLevelGame.MyApplication.name
        menge.text = "Du hast ${EasyLevelGame.MyApplication.richtig} von ${EasyLevelGame.MyApplication.counter} Bilder Richtig erraten!"


        btnbeenden.setOnClickListener {
            val intent = Intent(this, ChooseLevel::class.java)
            startActivity(intent)
            EasyLevelGame.MyApplication.currentPosition = 0
            EasyLevelGame.MyApplication.counter = 0
            EasyLevelGame.MyApplication.richtig = 0
            EasyLevelGame.MyApplication.richtig = 0
            }
        }
}
