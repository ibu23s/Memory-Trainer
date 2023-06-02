package de.fra_uas.fb2.mobiledevices.memorytrainer

import BilderSpeicher
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import kotlin.random.Random

class EasyLevelGame : AppCompatActivity() {

    class MyApplication : Application() {
        companion object {
            var counter = 0
            var currentPosition = 0
            var name: String = ""
            var richtig = 0
            var falsch = 0
        }
    }

    private var bildAusErsterAktivität: Int = 0
    private var bilder = BilderSpeicher.bilder
    private var neuebilder = BilderSpeicher.neueBilder
    private val aktuelleBilder = IntArray(6)
    private lateinit var imageButtons: Array<ImageButton>
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_level_game)

        val imageEasyEins = findViewById<ImageButton>(R.id.ibEasyEins)
        val imageEasyZwei = findViewById<ImageButton>(R.id.ibEasyZwei)
        val imageEasyDrei = findViewById<ImageButton>(R.id.ibEasyDrei)
        val imageEasyVier = findViewById<ImageButton>(R.id.ibEasyVier)
        val imageEasyFuenf = findViewById<ImageButton>(R.id.ibEasyFuenf)
        val imageEasySechs = findViewById<ImageButton>(R.id.ibEasySechs)

        imageButtons = arrayOf(
            imageEasyEins,
            imageEasyZwei,
            imageEasyDrei,
            imageEasyVier,
            imageEasyFuenf,
            imageEasySechs
        )

        val bilderList = BilderSpeicher.bilder.toMutableList()
        bilderList.shuffle()

        val neueBilder = mutableListOf<Int>()
        bildAusErsterAktivität = intent.getIntExtra("randomBild", 0)
        neueBilder.add(bildAusErsterAktivität)

        val anzahlBilder = minOf(5, bilderList.size)
        repeat(anzahlBilder) {
            val randomIndex = Random.nextInt(bilderList.size)
            val randomBild = bilderList[randomIndex]
            neueBilder.add(randomBild)
            bilderList.remove(randomBild)
        }

        neueBilder.shuffle()

        imageButtons.forEachIndexed { index, imageButton ->
            var selectedBild = neueBilder[index]

            // Überprüfen, ob das ausgewählte Bild bereits an anderer Stelle im Array vorkommt
            if (neueBilder.count { it == selectedBild } > 1) {
                // Neues Bild auswählen, das noch nicht im Array vorkommt
                val uniqueBilder = neueBilder.distinct()
                val remainingBilder = bilderList.filter { it !in uniqueBilder }
                selectedBild = if (remainingBilder.isNotEmpty()) {
                    remainingBilder.random()
                } else {
                    bilderList.random()
                }
                neueBilder[index] = selectedBild
            }

            imageButton.setImageResource(selectedBild)
            aktuelleBilder[index] = selectedBild
        }


        imageButtons.forEach { imageButton ->
            imageButton.setOnClickListener {
                val selectedBild = aktuelleBilder[imageButtons.indexOf(imageButton)]

                if (selectedBild == bildAusErsterAktivität) {
                    MyApplication.counter++
                    MyApplication.richtig++


                    Toast.makeText(this, "Richtig!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    MyApplication.counter++
                    MyApplication.falsch++

                    Toast.makeText(
                        this,
                        "Leider falsch.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                if (MyApplication.counter < 5) {
                    val intent = Intent(this, EasyLevel::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, ResultsActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
