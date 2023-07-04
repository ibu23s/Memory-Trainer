package de.fra_uas.fb2.mobiledevices.memorytrainer

import BilderSpeicher
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import kotlin.random.Random

class MediumLevel : AppCompatActivity() {

    private val delayTime: Long = 5000 // Zeit in Millisekunden (hier: 5 Sekunden)
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private lateinit var imageButtons: Array<ImageView>
    private val aktuelleBilder = IntArray(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_level)

        val randomIndex = Random.nextInt(BilderSpeicher.bilder.size)

        // Zugriff auf das zufällige Element
        val randomBild = BilderSpeicher.bilder[randomIndex]


        // Das Bild in einer ImageView anzeigen
        val bildEins = findViewById<ImageView>(R.id.bildEinsMedium)
        val bildZwei = findViewById<ImageView>(R.id.bildZweiMedium)
        val bildDrei = findViewById<ImageView>(R.id.bildDreiMedium)
        val bildVier = findViewById<ImageView>(R.id.bildVierMedium)


        imageButtons = arrayOf(
            bildEins,
            bildZwei,
            bildDrei,
            bildVier
        )

        val bilderList = BilderSpeicher.bilder.toMutableList()
        bilderList.shuffle()

        val anzahlBilder = minOf(4, bilderList.size)
        repeat(anzahlBilder) { index ->
            val selectedBild = bilderList[index]
            aktuelleBilder[index] = selectedBild
        }

        BilderSpeicher.bilder.shuffle()

        imageButtons.forEachIndexed { index, imageButton ->
            var selectedBild = BilderSpeicher.bilder[index]

            // Überprüfen, ob das ausgewählte Bild bereits an anderer Stelle im Array vorkommt
            if (BilderSpeicher.bilder.count { it == selectedBild } > 1) {
                // Neues Bild auswählen, das noch nicht im Array vorkommt
                val uniqueBilder = BilderSpeicher.bilder.distinct()
                val remainingBilder = bilderList.filter { it !in uniqueBilder }
                selectedBild = if (remainingBilder.isNotEmpty()) {
                    remainingBilder.random()
                } else {
                    bilderList.random()
                }
                BilderSpeicher.bilder[index] = selectedBild
            }

            imageButton.setImageResource(selectedBild)
            aktuelleBilder[index] = selectedBild
        }
        // Handler und Runnable initialisieren
        handler = Handler()
        runnable = Runnable {
            // Code, der nach Ablauf der Zeit ausgeführt wird (Activity-Wechsel)
            val intent = Intent(this, MediumLevelGame::class.java)
            intent.putExtra("randomBild", randomBild)
            intent.putExtra("randomBild", randomBild)
            intent.putExtra("bildEins", aktuelleBilder[0])
            intent.putExtra("bildZwei", aktuelleBilder[1])
            intent.putExtra("bildDrei", aktuelleBilder[2])
            intent.putExtra("bildVier", aktuelleBilder[3])
            EasyLevelGame.MyApplication.currentPosition++
            startActivity(intent)
            EasyLevelGame.MyApplication.currentPosition++
            finish() // Optional: Aktuelle Activity beenden, um nicht zurückkehren zu können
        }

        // Timer starten
        handler.postDelayed(runnable, delayTime)

    }
}