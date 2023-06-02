package de.fra_uas.fb2.mobiledevices.memorytrainer

import BilderSpeicher
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import kotlin.random.Random

class MediumLevel : AppCompatActivity() {

    private lateinit var imageButtons: Array<ImageView>
    private val aktuelleBilder = IntArray(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_level)

        val randomIndex = Random.nextInt(BilderSpeicher.bilder.size)

        // Zugriff auf das zufällige Element
        val randomBild = BilderSpeicher.bilder[randomIndex]

        val btnStart = findViewById<Button>(R.id.btnStartMedium)
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
        repeat(anzahlBilder) {
            val randomIndex = Random.nextInt(bilderList.size)
            val randomBild = bilderList[randomIndex]
            BilderSpeicher.bilder.add(randomBild)
            bilderList.remove(randomBild)
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

        btnStart.setOnClickListener {
            val intent = Intent(this, MediumLevelGame::class.java)
            intent.putExtra("randomBild", randomBild)
            intent.putExtra("bildEins", aktuelleBilder[0])
            intent.putExtra("bildZwei", aktuelleBilder[1])
            intent.putExtra("bildDrei", aktuelleBilder[2])
            intent.putExtra("bildVier", aktuelleBilder[3])
            EasyLevelGame.MyApplication.currentPosition++
            startActivity(intent)
        }



    }
}