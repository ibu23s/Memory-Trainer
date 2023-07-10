package de.fra_uas.fb2.mobiledevices.memorytrainer

import BilderSpeicher
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import kotlin.random.Random

class HardLevelGame : AppCompatActivity() {

    private lateinit var bildAusErsterAktivität1: Pair<Int, Int>
    private lateinit var bildAusErsterAktivität2: Pair<Int, Int>
    private lateinit var bildAusErsterAktivität3: Pair<Int, Int>
    private lateinit var bildAusErsterAktivität4: Pair<Int, Int>

    private val aktuelleBilder = mutableListOf<Pair<Int, Int>>()
    private val ausgewählteBilder = mutableListOf<Pair<Int, Int>>()

    private lateinit var imageButtons: Array<ImageButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard_level_game)

        val ibHard1 = findViewById<ImageButton>((R.id.ibHard1))
        val ibHard2 = findViewById<ImageButton>(R.id.ibHard2)
        val ibHard3 = findViewById<ImageButton>(R.id.ibHard3)
        val ibHard4 = findViewById<ImageButton>(R.id.ibHard4)
        val ibHard5 = findViewById<ImageButton>(R.id.ibHard5)
        val ibHard6 = findViewById<ImageButton>(R.id.ibHard6)
        val ibHard7 = findViewById<ImageButton>(R.id.ibHard7)
        val ibHard8 = findViewById<ImageButton>(R.id.ibHard8)
        val ibHard9 = findViewById<ImageButton>(R.id.ibHard9)
        val ibHard10 = findViewById<ImageButton>(R.id.ibHard10)
        val ibHard11 = findViewById<ImageButton>(R.id.ibHard11)
        val ibHard12 = findViewById<ImageButton>(R.id.ibHard12)

        EasyLevelGame.MyApplication.counter = 4

        imageButtons = arrayOf(
            ibHard1,
            ibHard2,
            ibHard3,
            ibHard4,
            ibHard5,
            ibHard6,
            ibHard7,
            ibHard8,
            ibHard9,
            ibHard10,
            ibHard11,
            ibHard12
        )


        val bilderList = BilderSpeicher.neueBilderHard.toMutableList()
        bilderList.shuffle()

        intent.getIntegerArrayListExtra("cardViewColors")

        val ersteVierBilder = mutableListOf<Pair<Int, Int>>()

        val myPairs = intent.getParcelableArrayListExtra<IntPair>("myPairs")
        if (myPairs != null && myPairs.isNotEmpty()) {
            bildAusErsterAktivität1 = Pair(myPairs[0].first, myPairs[0].second)
            bildAusErsterAktivität2 = Pair(myPairs[1].first, myPairs[1].second)
            bildAusErsterAktivität3 = Pair(myPairs[2].first, myPairs[2].second)
            bildAusErsterAktivität4 = Pair(myPairs[3].first, myPairs[3].second)

            ersteVierBilder.add(bildAusErsterAktivität1)
            ersteVierBilder.add(bildAusErsterAktivität2)
            ersteVierBilder.add(bildAusErsterAktivität3)
            ersteVierBilder.add(bildAusErsterAktivität4)

            aktuelleBilder.addAll(ersteVierBilder)
        }

        // Copy the original bilderList
        bilderList.removeAll(aktuelleBilder)

        val numRandom = 12 - aktuelleBilder.size
        for (i in 0 until numRandom) {
            val randomIndex = (bilderList.indices).random()
            val randomPair = bilderList[randomIndex]
            aktuelleBilder.add(randomPair)
            bilderList.removeAt(randomIndex)
        }
        aktuelleBilder.shuffle()


        imageButtons.forEachIndexed { index, imageButton ->
            val selectedBild = aktuelleBilder[index]
            val bildRessource = selectedBild.first

            imageButton.setBackgroundColor(Color.TRANSPARENT) // Setze Hintergrund auf transparent
            imageButton.setImageResource(bildRessource)
            val cardView = imageButton.parent as CardView

            if (ersteVierBilder.contains(selectedBild)) {
                val cardViewFarbe = selectedBild.second
                cardView.setCardBackgroundColor(cardViewFarbe)
            } else {
                // Überprüfen Sie, ob das Bild in `ersteVierBilder` und `aktuelleBilder` vorhanden ist
                val matchedBild = ersteVierBilder.find { it.first == selectedBild.first }
                if (matchedBild != null) {
                    var randomColor: Int
                    do {
                        randomColor = getRandomColor()
                    } while (randomColor == matchedBild.second) // Stellen Sie sicher, dass die neue Farbe nicht dieselbe wie die originale Farbe ist
                    cardView.setCardBackgroundColor(randomColor)
                } else {
                    val randomColor = getRandomColor()
                    cardView.setCardBackgroundColor(randomColor)
                }
            }
        }

        imageButtons.forEach { imageButton ->
            imageButton.setOnClickListener {
                val selectedBild = aktuelleBilder[imageButtons.indexOf(imageButton)]
                ausgewählteBilder.add(selectedBild)

                if (ersteVierBilder.contains(selectedBild)) {
                    EasyLevelGame.MyApplication.richtigHard++

                    Toast.makeText(this, "Richtig!", Toast.LENGTH_SHORT).show()
                } else {

                    EasyLevelGame.MyApplication.falsch++

                    Toast.makeText(this, "Leider falsch.", Toast.LENGTH_SHORT).show()
                }
                imageButton.isEnabled = false

                // Überprüfe, ob vier ImageButtons geklickt wurden
                if (ausgewählteBilder.size == 4) {
                    val intent = Intent(this, ResultsActivity::class.java)
                    intent.putExtra("sourceActivity", "HardLevelGame")
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun getRandomColor(): Int {
        val colors = intArrayOf(
            R.color.lila,
            R.color.blau,
            R.color.gruen,
            R.color.gelb,
            R.color.orange,
            R.color.rot,
            R.color.hellBlau,
            R.color.purple,
            R.color.dunkelgruen,
            R.color.dunkelgelb,
            R.color.dunkelorange,
            R.color.pink,

        )
        val randomIndex = Random.nextInt(colors.size)
        return resources.getColor(colors[randomIndex], null)
    }
}
