package io.strongapp.gymworkout.ui.training.finish.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.WorkoutEndEntity
import io.strongapp.gymworkout.databinding.ItemFinishBinding

class FinishAdapter(
	private val list: List<WorkoutEndEntity>,
	private val listFilter : List<WorkoutEndEntity>,
	private val context: Context
) : RecyclerView.Adapter<FinishAdapter.FinishViewHolder>() {
	inner class FinishViewHolder(private val binding: ItemFinishBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(training: WorkoutEndEntity) {
			binding.nameEx.text = training.name
			binding.nameEx.capitalizeFirstLetter()
			binding.rcvTraining.layoutManager = LinearLayoutManager(context)
			val finishItemAdapter = FinishItemAdapter(filterEx(list,training.name))
			binding.rcvTraining.adapter = finishItemAdapter
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishViewHolder {
		val binding = ItemFinishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return FinishViewHolder(binding)
	}

	override fun onBindViewHolder(holder: FinishViewHolder, position: Int) {
		val exercise = listFilter[position]
		holder.bind(exercise)
	}

	override fun getItemCount(): Int = listFilter.size


	private fun filterEx(training: List<WorkoutEndEntity>,targetName : String) : List<WorkoutEndEntity>
	{
		return training
			.filter { it.name == targetName }
			.sortedBy { it.set }
	}

	fun TextView.capitalizeFirstLetter() {
		text = text.toString().capitalize()
	}

}