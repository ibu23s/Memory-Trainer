package de.fra_uas.fb2.mobiledevices.memorytrainer

import BilderSpeicher
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import kotlin.random.Random

class ChooseLevel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_level)

        val name = intent.getStringExtra("name")
        val btnStart = findViewById<Button>(R.id.btnStartGame)
        val welcomeMessage = getString(R.string.welcome_message, name)
        val welcomeTextView = findViewById<TextView>(R.id.welcomeText)
        welcomeTextView.text = "Willkommen \n ${EasyLevelGame.MyApplication.name}"
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        BilderSpeicher.bilder = mutableListOf(
            R.drawable.bild1,
            R.drawable.bild12,
            R.drawable.bild13,
            R.drawable.bild15,
            R.drawable.bild16,
            R.drawable.bild18,
            R.drawable.bild19,
            R.drawable.bild20,
            R.drawable.bild21,
            R.drawable.bild22,
            R.drawable.bild23,
            R.drawable.bild24,
            R.drawable.bild25,
            R.drawable.bild26,
            R.drawable.bild27,
            R.drawable.bild28,
            R.drawable.bild29,
            R.drawable.bild30,
            R.drawable.bild31,
            R.drawable.bild32,
            R.drawable.bild33
        )

        BilderSpeicher.farben = mutableListOf(
            R.color.blau,
            R.color.gelb,
            R.color.gruen,
            R.color.lila,
            R.color.hellBlau,
            R.color.orange,
            R.color.purple,
            R.color.rot
        )

       // val images = listOf(R.drawable.bild1, R.drawable.bild29, R.drawable.bild30, R.drawable.bild31, R.drawable.bild32, R.drawable.bild33)
        val random = Random(System.currentTimeMillis())

        // Zufälliges Bild auswählen und anzeigen
        val randomIndex = random.nextInt(BilderSpeicher.bilder.size)
        val randomImage = BilderSpeicher.bilder[randomIndex]



        btnStart.setOnClickListener {
            when (radioGroup.checkedRadioButtonId) {
                R.id.rbEasy -> {
                    val intent = Intent(this, EasyLevel::class.java)
                    intent.putExtra("imageResId", randomImage)
                    startActivity(intent)
                }
                R.id.rbMedium -> {
                    val intent = Intent(this, MediumLevel::class.java)
                    startActivity(intent)
                }
                R.id.rbHard -> {
                    val intent = Intent(this, HardLevel::class.java)
                    startActivity(intent)
                }

            }
        }

    }

}