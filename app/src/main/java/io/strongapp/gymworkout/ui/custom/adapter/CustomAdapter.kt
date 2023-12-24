package io.strongapp.gymworkout.ui.custom.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.database.entities.CustomEntity
import io.strongapp.gymworkout.data.models.CustomTrainingEntity
import io.strongapp.gymworkout.databinding.ItemFoodMealBinding
import io.strongapp.gymworkout.databinding.ItemTrainingBinding
import io.strongapp.gymworkout.ui.custom.detail.DetailCustomAct
import io.strongapp.gymworkout.ui.main_v2.custom.CustomFr

class CustomAdapter(
	val context : Context,
	val list : MutableList<CustomEntity>
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
	 inner class CustomViewHolder(val binding : ItemTrainingBinding): RecyclerView.ViewHolder(binding.root) {
		fun bind(custom : CustomEntity) {
			binding.nameTraining.text = custom.name
			binding.imgFocus.setColorFilter(custom.color)
			val data = custom.data.toCustomTrainingList()
			binding.numberEx.text = data?.size.toString()
			binding.exercise.layoutManager = LinearLayoutManager(context)
			val exInCustomAdapter = data?.let { ExInCustomAdapter(context, it) }
			binding.exercise.adapter = exInCustomAdapter
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.CustomViewHolder {
		val binding = ItemTrainingBinding.inflate(LayoutInflater.from(context), parent, false)
		return CustomViewHolder(binding)
	}

	override fun onBindViewHolder(holder: CustomAdapter.CustomViewHolder, position: Int) {
		val custom = list[position]
		holder.bind(custom)

		holder.itemView.setOnClickListener {
			val intent = Intent(context,DetailCustomAct::class.java)
			intent.putExtra("customTraining",custom)
			(context as? Activity)?.startActivityForResult(intent,CustomFr.REQUEST_DETAIL_CUSTOM)
		}
	}

	override fun getItemCount(): Int {
		return list.size
	}

	fun setWorkoutList(workout: MutableList<CustomEntity>) {
		list.clear()
		list.addAll(workout)
		notifyDataSetChanged()
	}
	fun String.toCustomTrainingList(): List<CustomTrainingEntity>? {
		return try {
			val gson = Gson()
			val listType = object : TypeToken<List<CustomTrainingEntity>>() {}.type
			gson.fromJson(this, listType)
		} catch (e: Exception) {
			e.printStackTrace()
			null
		}
	}
}