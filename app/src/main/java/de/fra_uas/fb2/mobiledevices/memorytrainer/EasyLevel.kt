package de.fra_uas.fb2.mobiledevices.memorytrainer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.random.Random

@Suppress("DEPRECATION")
class EasyLevel : AppCompatActivity() {

    private val delayTime: Long = 2000 // Zeit in Millisekunden (hier: 2 Sekunden)
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_level)

        val randomIndex = Random.nextInt(BilderSpeicher.bilder.size)

        // Zugriff auf das zufällige Element
        val randomBild = BilderSpeicher.bilder[randomIndex]


        // Das Bild in einer ImageView anzeigen
        val imageView = findViewById<ImageView>(R.id.bildEinsEasy)
        imageView.setImageResource(randomBild)

        // Handler und Runnable initialisieren
        handler = Handler()
        runnable = Runnable {
            // Code, der nach Ablauf der Zeit ausgeführt wird (Activity-Wechsel)
            val intent = Intent(this, EasyLevelGame::class.java)
            intent.putExtra("randomBild", randomBild)
            startActivity(intent)
            EasyLevelGame.MyApplication.currentPosition++
            finish() // Optional: Aktuelle Activity beenden, um nicht zurückkehren zu können
        }

        // Timer starten
        handler.postDelayed(runnable, delayTime)



        val progressbar = findViewById<ProgressBar>(R.id.progressBar)
        val progress = findViewById<TextView>(R.id.progess)
        progressbar.progress = EasyLevelGame.MyApplication.currentPosition
        progress.text = "${EasyLevelGame.MyApplication.currentPosition}/" + progressbar.max

    }
}