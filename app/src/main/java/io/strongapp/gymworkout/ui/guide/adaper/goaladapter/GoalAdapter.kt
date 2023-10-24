package io.strongapp.gymworkout.ui.guide.adaper.goaladapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.GoalEntity

class GoalAdapter(private val list: List<GoalEntity>, private val context: Context) : RecyclerView.Adapter<GoalViewHolder>() {
	private var selectedPosition = -1
	private var selectGoal: String? = null


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
		return GoalViewHolder.create(parent)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
		val goal = list[position]
		holder.bind(goal, position == selectedPosition)
		holder.itemView.setOnClickListener {
			selectGoal = goal.title
			selectedPosition = holder.adapterPosition
			notifyDataSetChanged()

		}
	}

	fun getGoal(): String? {
		return selectGoal
	}
//	private fun savePositionLang(position: Int) {
//		val pref = context.getSharedPreferences(
//			"myPref", AppCompatActivity.MODE_PRIVATE
//		)
//		val editor = pref.edit()
//		editor.putInt("positionGoal", position)
//		editor.apply()
//	}
}
