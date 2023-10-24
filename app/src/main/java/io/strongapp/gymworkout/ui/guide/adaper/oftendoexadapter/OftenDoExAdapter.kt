package io.strongapp.gymworkout.ui.guide.adaper.oftendoexadapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.ui.guide.adaper.oftendoexadapter.OftenDoExViewHolder
import io.strongapp.gymworkout.data.models.OftenDoExerciseEntity

class OftenDoExAdapter(
	private val list: List<OftenDoExerciseEntity>,
	private val context: Context
) :
	RecyclerView.Adapter<OftenDoExViewHolder>() {
	private var selectedPosition = -1
	private var selectOftenDoExercise: String? = null


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OftenDoExViewHolder {
		return OftenDoExViewHolder.create(parent)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: OftenDoExViewHolder, position: Int) {
		val oftenDoEx = list[position]
		holder.bind(oftenDoEx, position == selectedPosition)
		holder.itemView.setOnClickListener {
			selectOftenDoExercise = oftenDoEx.title
			selectedPosition = holder.adapterPosition
			notifyDataSetChanged()

		}
	}

	fun getOftenDoEx(): String? {
		return selectOftenDoExercise
	}
}