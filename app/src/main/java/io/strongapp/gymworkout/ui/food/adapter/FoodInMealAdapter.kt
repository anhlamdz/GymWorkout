package io.strongapp.gymworkout.ui.food.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.database.entities.NutritionEntity
import io.strongapp.gymworkout.databinding.ItemFoodBinding
import io.strongapp.gymworkout.databinding.ItemSelectedFilterBinding

class FoodInMealAdapter(private val list : List<NutritionEntity>) : RecyclerView.Adapter<FoodInMealAdapter.FoodInMealViewHolder>() {
	inner class FoodInMealViewHolder(private val binding: ItemFoodBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(food : NutritionEntity) {
			binding.apply {
				nameFood.text = food.name
				infoFood.text = "${food.name} ${food.weight}g"

				caloFood.text = food.calo.toString()
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodInMealViewHolder {
		val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return FoodInMealViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: FoodInMealViewHolder, position: Int) {
		val food = list[position]
		holder.bind(food)
	}
}