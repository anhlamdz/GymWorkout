package io.strongapp.gymworkout.ui.food.searchfood.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.database.FoodResponse
import io.strongapp.gymworkout.databinding.ItemFoodBinding

object FoodResponseDiffCallback : DiffUtil.ItemCallback<FoodResponse>() {
    override fun areItemsTheSame(oldItem: FoodResponse, newItem: FoodResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodResponse, newItem: FoodResponse): Boolean {
        return oldItem == newItem
    }

}
class SearchFoodAdapter : ListAdapter<FoodResponse, SearchFoodAdapter.FoodViewHolder>
	(FoodResponseDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(foodResponse: FoodResponse) {
            binding.run {
                nameFood.text = foodResponse.name
                Log.i("hahahaha", foodResponse.toString())
//	            infoFood.text= foodResponse.calories.toString()+" g,"
            }
//            "${foodResponse.calories} cal, ${foodResponse.carbonHydrates} carb, ${foodResponse.fat} fat, ${foodResponse.protein} protein, ${foodResponse.weight} g"
        }
    }



}