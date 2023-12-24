package io.strongapp.gymworkout.ui.food

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.models.FoodMealEntity
import io.strongapp.gymworkout.databinding.FragmentFoodBinding
import io.strongapp.gymworkout.ui.food.adapter.FoodAdapter
import io.strongapp.gymworkout.ui.food.viewmodel.FoodViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.round


class FoodFr : BaseFragment<FragmentFoodBinding>() {
	private lateinit var foodViewModel: FoodViewModel
	private lateinit var user: UserEntity
	private var eaten: Int = 0

	override fun getLayoutRes(): Int {
		return R.layout.fragment_food
	}

	override fun initAction() {

	}

	override fun initView() {
		binding.foodRcv.layoutManager = LinearLayoutManager(requireContext())
		foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]



		val (year, month, day) = getCurrentDate()
		val date = "$day/$month/$year"

		foodViewModel.getFoodInCurrentDate(date).observe(this, { food ->
			food?.let { foodList ->
				val breakfastList = foodList.filter { it.meal == "Bữa sáng" }
				val lunchList = foodList.filter { it.meal == "Bữa trưa" }
				val dinnerList = foodList.filter { it.meal == "Bữa tối" }
				val snackList = foodList.filter { it.meal == "Đồ ăn nhẹ" }

				val breakfastListTotalCalo = breakfastList.sumBy { it.calo }
				val lunchListListTotalCalo = lunchList.sumBy { it.calo }
				val dinnerListTotalCalo = dinnerList.sumBy { it.calo }
				val snackListTotalCalo = snackList.sumBy { it.calo }

				val infoBreakfastList =
					"Carbs ${roundToTwoDecimals(breakfastList.sumByDouble { it.carb })}g * " +
							"Fat ${roundToTwoDecimals(breakfastList.sumByDouble { it.fat })}g * " +
							"Protein ${roundToTwoDecimals(breakfastList.sumByDouble { it.protein })}g"

				val infoLunchList =
					"Carbs ${roundToTwoDecimals(lunchList.sumByDouble { it.carb })}g * " +
							"Fat ${roundToTwoDecimals(lunchList.sumByDouble { it.fat })}g * " +
							"Protein ${roundToTwoDecimals(lunchList.sumByDouble { it.protein })}g"

				val infoDinnerList =
					"Carbs ${roundToTwoDecimals(dinnerList.sumByDouble { it.carb })}g * " +
							"Fat ${roundToTwoDecimals(dinnerList.sumByDouble { it.fat })}g * " +
							"Protein ${roundToTwoDecimals(dinnerList.sumByDouble { it.protein })}g"

				val infoSnackList =
					"Carbs ${roundToTwoDecimals(snackList.sumByDouble { it.carb })}g * " +
							"Fat ${roundToTwoDecimals(snackList.sumByDouble { it.fat })}g * " +
							"Protein ${roundToTwoDecimals(snackList.sumByDouble { it.protein })}g"
				eaten = breakfastListTotalCalo + lunchListListTotalCalo + dinnerListTotalCalo + snackListTotalCalo
				binding.tvEaten.text = eaten.toString()
				val listMeal: List<FoodMealEntity> = listOf(
					FoodMealEntity("Bữa sáng", infoBreakfastList, breakfastListTotalCalo, breakfastList),
					FoodMealEntity("Bữa trưa", infoLunchList, lunchListListTotalCalo, lunchList),
					FoodMealEntity("Bữa tối", infoDinnerList, dinnerListTotalCalo, dinnerList),
					FoodMealEntity("Đồ ăn nhẹ", infoSnackList, snackListTotalCalo, snackList),
				)
				val foodAdapter = FoodAdapter(requireContext(), listMeal,object : FoodAdapter.DeleteFood {
					override fun delete(adapterPosition: Int, position: Int) {
						deleteFood(adapterPosition,position)
					}
				})
				binding.foodRcv.adapter = foodAdapter

				lifecycleScope.launch {

					user = foodViewModel.getInfo()
					binding.base.text = user.totalCalorie.toString()
					binding.progressBarFood.max = user.totalCalorie
					binding.progressBarFood.progress = eaten
					binding.remaining.text = (user.totalCalorie - eaten).toString()

					val (carbValue, proteinValue,fatValue) = getMarco()
					val totalCarb = user.totalCalorie*(carbValue/100)
					val totalProtein = user.totalCalorie*(proteinValue/100)
					val totalFat = user.totalCalorie*(fatValue/100)

					binding.gamCarbs.text = "Carbs: ${roundToTwoDecimals(breakfastList.sumOf { it.carb }+lunchList.sumOf { it.carb }+dinnerList.sumOf { it.carb }+snackList.sumOf { it.carb })}/${String.format("%.2f",totalCarb/4)} g"
					binding.gamProtein.text="Protein: ${roundToTwoDecimals(breakfastList.sumOf { it.protein }+lunchList.sumOf { it.protein }+dinnerList.sumOf { it.protein }+snackList.sumOf { it.protein })}/${String.format("%.2f",totalProtein/4)} g"
					binding.gamFat.text="Fat: ${roundToTwoDecimals(breakfastList.sumOf { it.fat }+lunchList.sumOf { it.fat }+dinnerList.sumOf { it.fat }+snackList.sumOf { it.fat })}/${String.format("%.2f",totalFat/9)} g"

				}
			}
		})
	}

	fun getCurrentDate(): Triple<Int, Int, Int> {
		val currentDate = Calendar.getInstance()
		val year = currentDate.get(Calendar.YEAR)
		val month = currentDate.get(Calendar.MONTH) + 1
		val day = currentDate.get(Calendar.DAY_OF_MONTH)
		return Triple(year, month, day)
	}



	private fun deleteFood(adapterPosition: Int, position: Int) {
		val foodAdapter = binding.foodRcv.adapter as FoodAdapter
		val foodList = foodAdapter.getList()
		val foodMealEntity = foodList[adapterPosition]
		val foodInMealList = foodMealEntity.list
		val nutritionEntityToDelete = foodInMealList[position]

		foodViewModel.deleteFood(nutritionEntityToDelete)
	}
	fun getMarco() : Triple<Float,Float,Float>{
		val pref = requireContext().getSharedPreferences("marco", AppCompatActivity.MODE_PRIVATE)
		val marcoString =  pref.getString("marco","")?:""
		if (marcoString.isNotEmpty()){
			val value = marcoString.split(",").map { it.toFloat() }
			if (value.size ==3){
				return Triple(value[0],value[1],value[2])
			}
		}
		return Triple(30f,40f,30f)
	}
	fun roundToTwoDecimals(value: Double): Double {
		val scale = 2
		return round(value * Math.pow(10.0, scale.toDouble())) / Math.pow(10.0, scale.toDouble())
	}
}
