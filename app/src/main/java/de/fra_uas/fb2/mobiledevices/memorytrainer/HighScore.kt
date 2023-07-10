package de.fra_uas.fb2.mobiledevices.memorytrainer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HighScore : AppCompatActivity() {
    private lateinit var adapter: HighScoreAdapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        val recyclerView: RecyclerView = findViewById(R.id.HSrecyclerview)
        val reset = findViewById<Button>(R.id.HSreset)
        val home = findViewById<Button>(R.id.HSLevel)

        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val sharedPreferencesName = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val playerName = sharedPreferencesName.getString("name", "") ?: ""

        val existingHighScores = getExistingHighScores().toMutableList()

        val isNewName = existingHighScores.none { it.name == playerName }
        if (isNewName) {
            val newItem = HighScoreItem(
                name = playerName,
                easyScore = EasyLevelGame.MyApplication.richtigEasyKopie,
                mediumScore = EasyLevelGame.MyApplication.richtigMediumKopie,
                hardScore = EasyLevelGame.MyApplication.richtigHardKopie,
                punkte = EasyLevelGame.MyApplication.gesamtPunkte
            )
            existingHighScores.add(newItem)
        } else {
            val existingItem = existingHighScores.first { it.name == playerName }
            existingItem.easyScore += EasyLevelGame.MyApplication.richtigEasyKopie
            existingItem.mediumScore += EasyLevelGame.MyApplication.richtigMediumKopie
            existingItem.hardScore += EasyLevelGame.MyApplication.richtigHardKopie
            existingItem.punkte += EasyLevelGame.MyApplication.richtigEasyKopie + EasyLevelGame.MyApplication.richtigMediumKopie + EasyLevelGame.MyApplication.richtigHardKopie
        }


        saveHighScores(existingHighScores)

        adapter = HighScoreAdapter(existingHighScores, playerName)
        recyclerView.adapter = adapter

        reset.setOnClickListener {
            clearHighScores()
            existingHighScores.clear()
            EasyLevelGame.MyApplication.richtigEasyKopie = 0
            EasyLevelGame.MyApplication.richtigMediumKopie = 0
            EasyLevelGame.MyApplication.richtigHardKopie = 0
            EasyLevelGame.MyApplication.gesamtPunkte = 0
            adapter.notifyDataSetChanged()
        }

        home.setOnClickListener {
            val intent = Intent(this, ChooseLevel::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getExistingHighScores(): List<HighScoreItem> {
        val sharedPreferences = getSharedPreferences("HighScores", MODE_PRIVATE)
        val highScoreJson = sharedPreferences.getString("highScores", "")
        return if (highScoreJson.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<HighScoreItem>>() {}.type
            Gson().fromJson(highScoreJson, type)
        }
    }

    private fun saveHighScores(highScoreList: List<HighScoreItem>) {
        val sharedPreferences = getSharedPreferences("HighScores", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val highScoreJson = Gson().toJson(highScoreList)
        editor.putString("highScores", highScoreJson)
        editor.apply()
    }

    private fun clearHighScores() {
        val sharedPreferences = getSharedPreferences("HighScores", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
