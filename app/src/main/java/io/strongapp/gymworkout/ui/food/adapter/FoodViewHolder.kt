package io.strongapp.gymworkout.ui.food.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
			binding.total.visibility = ViewGroup.VISIBLE
			binding.total.text = total.toString()
		}
		if(foodMealEntity.list.size > 0){
			binding.infoFood.visibility = ViewGroup.VISIBLE
			val foodInMealAdapter = FoodInMealAdapter(foodMealEntity.list)
			binding.infoFood.layoutManager = LinearLayoutManager(itemView.context)
			binding.infoFood.adapter = foodInMealAdapter

			binding.descriptionMeal.visibility = ViewGroup.VISIBLE
			binding.descriptionMeal.text = foodMealEntity.info
		}
		else binding.infoFood.visibility = ViewGroup.GONE

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