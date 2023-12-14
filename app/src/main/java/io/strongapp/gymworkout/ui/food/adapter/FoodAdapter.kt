package io.strongapp.gymworkout.ui.food.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.FoodMealEntity
import io.strongapp.gymworkout.databinding.ItemFoodMealBinding
import io.strongapp.gymworkout.ui.food.searchfood.SearchFoodAct

class FoodAdapter(
	val context: Context,
	private val list : List<FoodMealEntity>,
	private val delete: DeleteFood
) :
	RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
	inner class FoodViewHolder(val binding: ItemFoodMealBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(foodMealEntity: FoodMealEntity) {
			binding.nameMeal.text = foodMealEntity.title
			val total = foodMealEntity.totalCalo
			if (total == 0) {
				binding.total.visibility = ViewGroup.INVISIBLE
			} else {
				binding.total.visibility = ViewGroup.VISIBLE
				binding.total.text = total.toString()
			}
			if (foodMealEntity.list.size > 0) {
				binding.infoFood.visibility = ViewGroup.VISIBLE
				val foodInMealAdapter = FoodInMealAdapter(foodMealEntity.list, object : FoodInMealAdapter.DeleteFood {
					override fun delete(position: Int) {
						delete.delete(adapterPosition, position)
					}
				})
				binding.infoFood.layoutManager = LinearLayoutManager(itemView.context)
				binding.infoFood.adapter = foodInMealAdapter

				binding.descriptionMeal.visibility = ViewGroup.VISIBLE
				binding.descriptionMeal.text = foodMealEntity.info
			} else binding.infoFood.visibility = ViewGroup.GONE

		}

	}
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdapter.FoodViewHolder {
		val binding = ItemFoodMealBinding.inflate(LayoutInflater.from(context), parent, false)
		return FoodViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
		val meal = list[position]
		holder.bind(meal)

		holder.binding.btnAddFood.setOnClickListener {
			val intent = Intent(context, SearchFoodAct::class.java)
			intent.putExtra("nameMeal",holder.binding.nameMeal.text.toString())
			context.startActivity(intent)
		}
	}
	fun getList() : List<FoodMealEntity> {
		return list
	}
	interface DeleteFood {
		fun delete(adapterPosition: Int, position: Int)
	}
}