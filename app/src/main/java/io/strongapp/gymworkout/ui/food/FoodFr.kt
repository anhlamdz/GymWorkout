package io.strongapp.gymworkout.ui.food

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
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FoodFr : BaseFragment<FragmentFoodBinding>() {
	private lateinit var foodViewModel: FoodViewModel
	private lateinit var user : UserEntity
	private var eaten : Int = 0


	private val listMeal : List<FoodMealEntity> = listOf(
		FoodMealEntity("Bữa sáng",0),
		FoodMealEntity("Bữa trưa",0),
		FoodMealEntity("Bữa tối",0),
		FoodMealEntity("Đồ ăn nhẹ",0),
	)

	private val foodAdapter by lazy {
		FoodAdapter(requireContext(),listMeal)
	}
	override fun getLayoutRes(): Int {
		return R.layout.fragment_food
	}

	override fun initAction() {

	}

	override fun initView() {
		binding.foodRcv.layoutManager = LinearLayoutManager(requireContext())
		foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]
		lifecycleScope.launch {
			user = foodViewModel.getInfo()
			binding.base.text = user.tdee.toString()
			binding.remaining.text = (user.tdee - eaten).toString()
			binding.foodRcv.adapter = foodAdapter

		}
		binding.foodRcv.adapter = foodAdapter
	}
}