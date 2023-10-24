package io.strongapp.gymworkout.ui.guide.adaper.goaladapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.GoalEntity
import io.strongapp.gymworkout.databinding.ItemGuideGoalBinding


class GoalViewHolder(val binding: ItemGuideGoalBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind(goal: GoalEntity, isSeclect: Boolean) {
		binding.tvContent.text = goal.title ?: ""
		binding.tvDes.text = goal.description ?: ""
		binding.apply {
			if (isSeclect) {
				itemView.isSelected = true
				binding.tvDes.visibility = View.VISIBLE
				binding.ivCheck.visibility = View.VISIBLE
			} else {
				itemView.isSelected = false
				binding.tvDes.visibility = View.GONE
				binding.ivCheck.visibility = View.GONE
			}
		}
	}

	companion object {
		fun create(viewGroup: ViewGroup): GoalViewHolder {
			return GoalViewHolder(
				binding = ItemGuideGoalBinding.inflate(
					LayoutInflater.from(viewGroup.context),
					viewGroup,
					false
				)
			)

		}
	}
}
