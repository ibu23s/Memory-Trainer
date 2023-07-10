package de.fra_uas.fb2.mobiledevices.memorytrainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HighScoreAdapter(
    private val data: List<HighScoreItem>,
    private val playerName: String
) : RecyclerView.Adapter<HighScoreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.highscore_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.HSname)
        private val textViewEasy: TextView = itemView.findViewById(R.id.HSeasy)
        private val textViewMedium: TextView = itemView.findViewById(R.id.HSmedium)
        private val textViewHard: TextView = itemView.findViewById(R.id.HShard)
        private val textViewPunkte: TextView = itemView.findViewById(R.id.HSpunknte)

        fun bind(item: HighScoreItem) {
            textViewName.text = item.name
            textViewEasy.text = item.easyScore.toString()
            textViewMedium.text = item.mediumScore.toString()
            textViewHard.text = item.hardScore.toString()
            textViewPunkte.text = item.punkte.toString()
        }

    }

}
