package de.fra_uas.fb2.mobiledevices.memorytrainer

import BilderSpeicher
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.widget.ImageView
import androidx.cardview.widget.CardView
import kotlin.random.Random

class HardLevel : AppCompatActivity() {

    private val delayTime: Long = 5000 // Zeit in Millisekunden (hier: 5 Sekunden)
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private lateinit var imageButtons: Array<ImageView>
    private val aktuelleBilder = IntArray(4)
    private val aktuelleFarbe = IntArray(4)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard_level)
        val randomIndex = Random.nextInt(BilderSpeicher.bilder.size)

        // Zugriff auf das zufällige Element
        val randomBild = BilderSpeicher.bilder[randomIndex]


        // Das Bild in einer ImageView anzeigen
        val bildEins = findViewById<ImageView>(R.id.bildEinsHard)
        val bildZwei = findViewById<ImageView>(R.id.bildZweiHard)
        val bildDrei = findViewById<ImageView>(R.id.bildDreiHard)
        val bildVier = findViewById<ImageView>(R.id.bildVierHard)

        val cardViewEins = findViewById<CardView>(R.id.cardViewHard1)
        val cardViewZwei = findViewById<CardView>(R.id.cardViewHard2)
        val cardViewDrei = findViewById<CardView>(R.id.cardViewHard3)
        val cardViewVier = findViewById<CardView>(R.id.cardViewHard1)



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


        val cardViewColors = setRandomCardViewBackgroundColor()

        imageButtons.forEachIndexed { index, imageButton ->
            var selectedBild = BilderSpeicher.bilder[index]
            var selectedFarbe = BilderSpeicher.farben[index]

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
            val intent = Intent(this, HardLevelGame::class.java)

            val myPairs: List<IntPair> = listOf(
                IntPair(aktuelleBilder[0], cardViewColors[0]),
                IntPair(aktuelleBilder[1], cardViewColors[1]),
                IntPair(aktuelleBilder[2], cardViewColors[2]),
                IntPair(aktuelleBilder[3], cardViewColors[3])
            )
            intent.putParcelableArrayListExtra("myPairs", ArrayList(myPairs))

            EasyLevelGame.MyApplication.currentPosition++
            startActivity(intent)
            finish()
            EasyLevelGame.MyApplication.currentPosition++
            finish() // Optional: Aktuelle Activity beenden, um nicht zurückkehren zu können
        }

        // Timer starten
        handler.postDelayed(runnable, delayTime)


        BilderSpeicher.neueBilderHard = mutableListOf(
            Pair(R.drawable.bild1, cardViewColors[0]),
            Pair(R.drawable.bild12, cardViewColors[0]),
            Pair(R.drawable.bild13, cardViewColors[0]),
            Pair(R.drawable.bild32, cardViewColors[0]),
            Pair(R.drawable.bild15, cardViewColors[0]),
            Pair(R.drawable.bild16, cardViewColors[0]),
            Pair(R.drawable.bild33, cardViewColors[0]),
            Pair(R.drawable.bild18, cardViewColors[0]),
            Pair(R.drawable.bild19, cardViewColors[0]),
            Pair(R.drawable.bild20, cardViewColors[0]),
            Pair(R.drawable.bild21, cardViewColors[0]),
            Pair(R.drawable.bild22, cardViewColors[0]),
            Pair(R.drawable.bild23, cardViewColors[0]),
            Pair(R.drawable.bild24, cardViewColors[0]),
            Pair(R.drawable.bild25, cardViewColors[0]),
            Pair(R.drawable.bild26, cardViewColors[0]),
            Pair(R.drawable.bild27, cardViewColors[0]),
            Pair(R.drawable.bild28, cardViewColors[0]),
            Pair(R.drawable.bild29, cardViewColors[0]),
            Pair(R.drawable.bild30, cardViewColors[0]),
            Pair(R.drawable.bild31, cardViewColors[0])
        )


    }
    private fun setRandomCardViewBackgroundColor(): List<Int> {
        val colors = mutableListOf<Int>()
        imageButtons.forEach { imageButton ->
            val cardView = imageButton.parent as CardView
            val randomColor = getRandomColor()
            cardView.setCardBackgroundColor(randomColor)
            colors.add(randomColor)
        }
        return colors
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
            R.color.purple
        )
        val randomIndex = Random.nextInt(colors.size)
        return resources.getColor(colors[randomIndex], null)
    }
}
