package io.strongapp.gymworkout.ui.food.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.FoodMealEntity
import io.strongapp.gymworkout.ui.food.searchfood.SearchFoodAct

class FoodAdapter(
	val context: Context,
	private val list : List<FoodMealEntity>) :
	RecyclerView.Adapter<FoodViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
		return FoodViewHolder.create(parent)
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
}