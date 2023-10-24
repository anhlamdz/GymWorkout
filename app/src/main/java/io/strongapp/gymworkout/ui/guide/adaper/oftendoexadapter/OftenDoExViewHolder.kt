package io.strongapp.gymworkout.ui.guide.adaper.oftendoexadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.OftenDoExerciseEntity
import io.strongapp.gymworkout.databinding.ItemGuideGoalBinding

class OftenDoExViewHolder(val binding: ItemGuideGoalBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind(oftenDoEx: OftenDoExerciseEntity, isSeclect: Boolean) {
		binding.tvContent.text = oftenDoEx.title
		binding.tvDes.text = oftenDoEx.description
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
		fun create(viewGroup: ViewGroup): OftenDoExViewHolder {
			return OftenDoExViewHolder(
				binding = ItemGuideGoalBinding.inflate(
					LayoutInflater.from(viewGroup.context),
					viewGroup,
					false
				)
			)

		}
	}
}