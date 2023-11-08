package io.strongapp.gymworkout.ui.food.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.FoodMealEntity
import io.strongapp.gymworkout.databinding.ItemFoodMealBinding

class FoodViewHolder(val binding: ItemFoodMealBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind(foodMealEntity: FoodMealEntity) {
		binding.nameMeal.text = foodMealEntity.title
		val total = foodMealEntity.totalCalo
		if (total == 0){
			binding.total.visibility = ViewGroup.INVISIBLE
		}
		else{
			binding.total.text = total.toString()
		}

	}


	companion object {
		fun create(viewGroup: ViewGroup): FoodViewHolder {
			return FoodViewHolder(
				binding = ItemFoodMealBinding.inflate(
					LayoutInflater.from(viewGroup.context),
					viewGroup,
					false
				)
			)

		}
	}
}