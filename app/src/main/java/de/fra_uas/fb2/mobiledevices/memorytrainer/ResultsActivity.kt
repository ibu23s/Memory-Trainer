package de.fra_uas.fb2.mobiledevices.memorytrainer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultsActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val btnbeenden = findViewById<Button>(R.id.buttonBeenden2)
        val resultName = findViewById<TextView>(R.id.Resultname)
        val menge = findViewById<TextView>(R.id.mengeRichtigeFragen)
        val resultLevel = findViewById<TextView>(R.id.ResultLevel)

        resultName.text = EasyLevelGame.MyApplication.name

        when (intent.getStringExtra("sourceActivity")) {
            "EasyLevelGame" -> {
                menge.text = "Du hast ${EasyLevelGame.MyApplication.richtigEasy} von 5 Bilder Richtig erraten!"
                resultLevel.text = "Easy Level"
            }
            "MediumLevelGame" -> {
                menge.text = "Du hast ${EasyLevelGame.MyApplication.richtigMedium} von 4 Bilder Richtig erraten!"
                resultLevel.text = "Medium Level"
            }
            "HardLevelGame" -> {
                menge.text = "Du hast ${EasyLevelGame.MyApplication.richtigHard} von 4 Bilder Richtig erraten!"
                resultLevel.text = "Hard Level"
            }
        }

        EasyLevelGame.MyApplication.richtigEasyKopie = EasyLevelGame.MyApplication.richtigEasy
        EasyLevelGame.MyApplication.richtigMediumKopie = EasyLevelGame.MyApplication.richtigMedium
        EasyLevelGame.MyApplication.richtigHardKopie = EasyLevelGame.MyApplication.richtigHard

        btnbeenden.setOnClickListener {
            val intent = Intent(this, ChooseLevel::class.java)
            startActivity(intent)
            EasyLevelGame.MyApplication.currentPosition = 0
            EasyLevelGame.MyApplication.counter = 0
            EasyLevelGame.MyApplication.richtigEasy = 0
            EasyLevelGame.MyApplication.richtigMedium = 0
            EasyLevelGame.MyApplication.richtigHard = 0
            }
    }
}
