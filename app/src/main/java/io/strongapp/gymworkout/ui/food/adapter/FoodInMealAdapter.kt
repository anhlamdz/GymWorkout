package io.strongapp.gymworkout.ui.food.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.database.entities.NutritionEntity
import io.strongapp.gymworkout.databinding.ItemFoodBinding
import io.strongapp.gymworkout.databinding.ItemSelectedFilterBinding

class FoodInMealAdapter(
	private val list : List<NutritionEntity>,
	private val delete: DeleteFood) : RecyclerView.Adapter<FoodInMealAdapter.FoodInMealViewHolder>() {

	private var isDeleteVisible = false
	inner class FoodInMealViewHolder(private val binding: ItemFoodBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(food : NutritionEntity) {
			binding.apply {
				nameFood.text = food.name
				infoFood.text = "${food.name} ${food.weight}g"

				caloFood.text = food.calo.toString()

				btnDelete.setOnClickListener {
					delete.delete(adapterPosition)
				}
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
	interface DeleteFood {
		fun delete(position: Int)
	}

}
