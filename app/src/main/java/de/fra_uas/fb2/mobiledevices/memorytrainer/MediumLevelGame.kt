package de.fra_uas.fb2.mobiledevices.memorytrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import kotlin.random.Random

class MediumLevelGame : AppCompatActivity() {
    private var bildAusErsterAktivitaet1: Int = 0
    private var bildAusErsterAktivitaet2: Int = 0
    private var bildAusErsterAktivitaet3: Int = 0
    private var bildAusErsterAktivitaet4: Int = 0
    private val aktuelleBilder = IntArray(12)
    private lateinit var imageButtons: Array<ImageButton>

    private val ausgewaehlteBilder = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_level_game)

        val ibMedium1 = findViewById<ImageButton>(R.id.ibMedium1)
        val ibMedium2 = findViewById<ImageButton>(R.id.ibMedium2)
        val ibMedium3 = findViewById<ImageButton>(R.id.ibMedium3)
        val ibMedium4 = findViewById<ImageButton>(R.id.ibMedium4)
        val ibMedium5 = findViewById<ImageButton>(R.id.ibMedium5)
        val ibMedium6 = findViewById<ImageButton>(R.id.ibMedium6)
        val ibMedium7 = findViewById<ImageButton>(R.id.ibMedium7)
        val ibMedium8 = findViewById<ImageButton>(R.id.ibMedium8)
        val ibMedium9 = findViewById<ImageButton>(R.id.ibMedium9)
        val ibMedium10 = findViewById<ImageButton>(R.id.ibMedium10)
        val ibMedium11 = findViewById<ImageButton>(R.id.ibMedium11)
        val ibMedium12 = findViewById<ImageButton>(R.id.ibMedium12)

        imageButtons = arrayOf(
            ibMedium1,
            ibMedium2,
            ibMedium3,
            ibMedium4,
            ibMedium5,
            ibMedium6,
            ibMedium7,
            ibMedium8,
            ibMedium9,
            ibMedium10,
            ibMedium11,
            ibMedium12
        )

        val bilderList = BilderSpeicher.bilder.toMutableList()
        bilderList.shuffle()

        val neueBilder = mutableListOf<Int>()
        bildAusErsterAktivitaet1 = intent.getIntExtra("bildEins", 0)
        bildAusErsterAktivitaet2 = intent.getIntExtra("bildZwei", 0)
        bildAusErsterAktivitaet3 = intent.getIntExtra("bildDrei", 0)
        bildAusErsterAktivitaet4 = intent.getIntExtra("bildVier", 0)

        neueBilder.add(bildAusErsterAktivitaet1)
        neueBilder.add(bildAusErsterAktivitaet2)
        neueBilder.add(bildAusErsterAktivitaet3)
        neueBilder.add(bildAusErsterAktivitaet4)

        val anzahlBilder = minOf(8, bilderList.size)
        var addedBilder = 0

        while (addedBilder < anzahlBilder) {
            val randomIndex = Random.nextInt(bilderList.size)
            val randomBild = bilderList[randomIndex]

            // Überprüfen, ob das zufaellige Bild bereits ausgewaehlt wurde
            if (!neueBilder.contains(randomBild)) {
                neueBilder.add(randomBild)
                bilderList.removeAt(randomIndex)
                addedBilder++
            }
        }

        neueBilder.shuffle()

// Restlicher Code bleibt unveraendert


        neueBilder.shuffle()

        imageButtons.forEachIndexed { index, imageButton ->
            val selectedBild = neueBilder[index]
            imageButton.setImageResource(selectedBild)
            aktuelleBilder[index] = selectedBild
        }



        imageButtons.forEach { imageButton ->
            imageButton.setOnClickListener {
                val selectedBild = aktuelleBilder[imageButtons.indexOf(imageButton)]
                ausgewaehlteBilder.add(selectedBild)

                if (selectedBild == bildAusErsterAktivitaet1 ||
                    selectedBild == bildAusErsterAktivitaet2 ||
                    selectedBild == bildAusErsterAktivitaet3 ||
                    selectedBild == bildAusErsterAktivitaet4
                ) {
                    EasyLevelGame.MyApplication.counter++
                    EasyLevelGame.MyApplication.richtigMedium++

                    Toast.makeText(this, "Richtig!", Toast.LENGTH_SHORT).show()
                } else {
                    EasyLevelGame.MyApplication.counter++
                    EasyLevelGame.MyApplication.falsch++

                    Toast.makeText(
                        this, "Leider falsch.", Toast.LENGTH_SHORT).show()
                }
                imageButton.isEnabled = false

                // Überprüfe, ob vier ImageButtons geklickt wurden
                if (ausgewaehlteBilder.size == 4) {
                    val intent = Intent(this, ResultsActivity::class.java)
                    intent.putExtra("sourceActivity", "MediumLevelGame")
                    startActivity(intent)
                }
            }
        }
    }
}



