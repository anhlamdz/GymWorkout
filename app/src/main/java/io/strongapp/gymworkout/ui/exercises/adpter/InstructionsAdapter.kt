package io.strongapp.gymworkout.ui.exercises.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R

class InstructionsAdapter(
	private val list :List<String>,
	private val context: Context)
	: RecyclerView.Adapter<InstructionsAdapter.ViewHolder>() {
	inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
		val number = itemView.findViewById<TextView>(R.id.number)
		val title = itemView.findViewById<TextView>(R.id.instructions)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionsAdapter.ViewHolder {
		val view = LayoutInflater.from(context).inflate(R.layout.simple_instructions_text, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: InstructionsAdapter.ViewHolder, position: Int) {
		val instructions = list[position]
		holder.number.text = position.toString()
		holder.title.text = instructions
	}

	override fun getItemCount(): Int {
		return list.size
	}
}