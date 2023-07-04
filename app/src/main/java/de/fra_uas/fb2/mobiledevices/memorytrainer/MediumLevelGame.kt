package de.fra_uas.fb2.mobiledevices.memorytrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import kotlin.random.Random

class MediumLevelGame : AppCompatActivity() {
    private var bildAusErsterAktivität1: Int = 0
    private var bildAusErsterAktivität2: Int = 0
    private var bildAusErsterAktivität3: Int = 0
    private var bildAusErsterAktivität4: Int = 0
    private var bilder = BilderSpeicher.bilder
    private var neuebilder = BilderSpeicher.neueBilder
    private val aktuelleBilder = IntArray(12)
    private lateinit var imageButtons: Array<ImageButton>
    private var counter = 0
    private val ausgewählteBilder = mutableListOf<Int>()

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
        bildAusErsterAktivität1 = intent.getIntExtra("bildEins", 0)
        bildAusErsterAktivität2 = intent.getIntExtra("bildZwei", 0)
        bildAusErsterAktivität3 = intent.getIntExtra("bildDrei", 0)
        bildAusErsterAktivität4 = intent.getIntExtra("bildVier", 0)

        neueBilder.add(bildAusErsterAktivität1)
        neueBilder.add(bildAusErsterAktivität2)
        neueBilder.add(bildAusErsterAktivität3)
        neueBilder.add(bildAusErsterAktivität4)

        val anzahlBilder = minOf(8, bilderList.size)
        var addedBilder = 0

        while (addedBilder < anzahlBilder) {
            val randomIndex = Random.nextInt(bilderList.size)
            val randomBild = bilderList[randomIndex]

            // Überprüfen, ob das zufällige Bild bereits ausgewählt wurde
            if (!neueBilder.contains(randomBild)) {
                neueBilder.add(randomBild)
                bilderList.removeAt(randomIndex)
                addedBilder++
            }
        }

        neueBilder.shuffle()

// Restlicher Code bleibt unverändert


        neueBilder.shuffle()

        imageButtons.forEachIndexed { index, imageButton ->
            val selectedBild = neueBilder[index]
            imageButton.setImageResource(selectedBild)
            aktuelleBilder[index] = selectedBild
        }



        imageButtons.forEach { imageButton ->
            imageButton.setOnClickListener {
                val selectedBild = aktuelleBilder[imageButtons.indexOf(imageButton)]
                ausgewählteBilder.add(selectedBild)

                if (selectedBild == bildAusErsterAktivität1 ||
                    selectedBild == bildAusErsterAktivität2 ||
                    selectedBild == bildAusErsterAktivität3 ||
                    selectedBild == bildAusErsterAktivität4
                ) {
                    EasyLevelGame.MyApplication.counter++
                    EasyLevelGame.MyApplication.richtig++

                    Toast.makeText(this, "Richtig!", Toast.LENGTH_SHORT).show()
                } else {
                    EasyLevelGame.MyApplication.counter++
                    EasyLevelGame.MyApplication.falsch++

                    Toast.makeText(
                        this,
                        "Leider falsch.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // Überprüfe, ob vier ImageButtons geklickt wurden
                if (ausgewählteBilder.size == 4) {
                    val intent = Intent(this, ResultsActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}



