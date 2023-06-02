package de.fra_uas.fb2.mobiledevices.memorytrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.random.Random

class EasyLevel : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_level)

        val randomIndex = Random.nextInt(BilderSpeicher.bilder.size)

        // Zugriff auf das zufällige Element
        val randomBild = BilderSpeicher.bilder[randomIndex]

        val btnStart = findViewById<Button>(R.id.btnStartEasy)

        // Das Bild in einer ImageView anzeigen
        val imageView = findViewById<ImageView>(R.id.bildEinsEasy)
        imageView.setImageResource(randomBild)



        val progressbar = findViewById<ProgressBar>(R.id.progressBar)
        val progress = findViewById<TextView>(R.id.progess)
        progressbar.progress = EasyLevelGame.MyApplication.currentPosition
        progress.text = "${EasyLevelGame.MyApplication.currentPosition}/" + progressbar.max





        btnStart.setOnClickListener {
            val intent = Intent(this, EasyLevelGame::class.java)
            intent.putExtra("randomBild", randomBild)
            EasyLevelGame.MyApplication.currentPosition++
            startActivity(intent)
        }



    }
}