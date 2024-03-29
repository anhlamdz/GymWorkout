package io.strongapp.gymworkout.ui.food.searchfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.database.FoodResponse
import io.strongapp.gymworkout.databinding.ItemFoodAddBinding
import io.strongapp.gymworkout.databinding.ItemFoodBinding

object FoodResponseDiffCallback : DiffUtil.ItemCallback<FoodResponse>() {
    override fun areItemsTheSame(oldItem: FoodResponse, newItem: FoodResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodResponse, newItem: FoodResponse): Boolean {
        return oldItem == newItem
    }

}
class SearchFoodAdapter(private val listener : ListenerInsertFoodToMeal) : ListAdapter<FoodResponse, SearchFoodAdapter.FoodViewHolder>
	(FoodResponseDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding =
            ItemFoodAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    inner class FoodViewHolder(private val binding: ItemFoodAddBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
        	binding.btnAddFood.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val foodResponse = getItem(position)
                    listener.onClick(foodResponse)
                }
            }
        }

        fun bind(foodResponse: FoodResponse) {
            binding.run {
                nameFood.text = foodResponse.name
                infoFood.text= "${foodResponse.calories} cal, ${foodResponse.carbonHydrates} carb, ${foodResponse.fat} fat, ${foodResponse.protein} protein, ${foodResponse.weight} g"
            }
        }
    }

    interface ListenerInsertFoodToMeal{
        fun onClick(foodResponse: FoodResponse)
    }

}