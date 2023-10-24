package io.strongapp.gymworkout.ui

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.databinding.ActivityMainBinding
import io.strongapp.gymworkout.ui.exercises.ExercisesFr
import io.strongapp.gymworkout.ui.food.FoodFr
import io.strongapp.gymworkout.ui.main_v2.custom.CustomFr
import io.strongapp.gymworkout.ui.me.MeFr

import io.strongapp.gymworkout.ui.training.TrainingFr


class MainActivity : BaseActivity<ActivityMainBinding>() {

	override fun initView() {

	}

	override fun initAction() {
		replaceFragment(TrainingFr(), 0)
		binding.bottomNav.setOnItemSelectedListener { menuItem ->
			val fragment = when (menuItem.itemId) {
				R.id.training -> TrainingFr()
				R.id.custom -> CustomFr()
				R.id.exercises -> ExercisesFr()
				R.id.food -> FoodFr()
				R.id.me -> MeFr()
				else -> TrainingFr()
			}
			replaceFragment(fragment, menuItem.order)
			true
		}
	}

	private fun replaceFragment(fragment: Fragment, index: Int) {
		val fragmentManager = supportFragmentManager
		val fragmentTransaction = fragmentManager.beginTransaction()
		fragmentTransaction.replace(R.id.frame_layout, fragment)
		fragmentTransaction.commit()
	}

	override fun getContentView(): Int {
		return R.layout.activity_main
	}

	override fun bindViewModel() {
	}

	companion object {
		fun getIntent(context: Context): Intent {
			val intent = Intent(context, MainActivity::class.java)
			intent.putExtra("back_setting", true)
			return intent
		}
	}
}