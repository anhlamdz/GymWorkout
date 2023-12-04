package io.strongapp.gymworkout.ui

import android.content.Context
import android.content.Intent
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.databinding.ActivityMainBinding
import io.strongapp.gymworkout.ui.exercises.ExercisesFr
import io.strongapp.gymworkout.ui.food.FoodFr
import io.strongapp.gymworkout.ui.main_v2.custom.CustomFr
import io.strongapp.gymworkout.ui.me.MeFr

import io.strongapp.gymworkout.ui.training.TrainingFr


class MainActivity : BaseActivity<ActivityMainBinding>() {
	private val trainingFr: Fragment = TrainingFr()
	private val customFr: Fragment = CustomFr()
	private val exercisesFr: Fragment = ExercisesFr()
	private val foodFr :Fragment = FoodFr()
	private val meFr: Fragment = MeFr()
	private val fm: FragmentManager = supportFragmentManager
	private var active: Fragment = trainingFr

	override fun initView() {
		binding.bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
		fm.beginTransaction().add(R.id.frame_layout,meFr,"5").hide(meFr).commit()
		fm.beginTransaction().add(R.id.frame_layout,foodFr,"4").hide(foodFr).commit()
		fm.beginTransaction().add(R.id.frame_layout,exercisesFr,"3").hide(exercisesFr).commit()
		fm.beginTransaction().add(R.id.frame_layout,customFr,"2").hide(customFr).commit()
		fm.beginTransaction().add(R.id.frame_layout,trainingFr,"1").commit()

	}

	override fun initAction() {
//		replaceFragment(TrainingFr(), 0)
//		binding.bottomNav.setOnItemSelectedListener { menuItem ->
//			val fragment = when (menuItem.itemId) {
//				R.id.training -> TrainingFr()
//				R.id.custom -> CustomFr()
//				R.id.exercises -> ExercisesFr()
//				R.id.food -> FoodFr()
//				R.id.me -> MeFr()
//				else -> TrainingFr()
//			}
//			replaceFragment(fragment, menuItem.order)
//			true
//		}
	}

	override fun onBackPressed() {


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

	override fun bindViewModel() {}

	private val mOnNavigationItemSelectedListener  = BottomNavigationView.OnNavigationItemSelectedListener { item ->
		when (item.itemId) {
			R.id.training -> {
				fm.beginTransaction().hide(active).show(trainingFr).commit()
				active = trainingFr
				return@OnNavigationItemSelectedListener true
			}
			R.id.custom -> {
				fm.beginTransaction().hide(active).show(customFr).commit()
				active = customFr
				return@OnNavigationItemSelectedListener true
			}
			R.id.exercises -> {
				fm.beginTransaction().hide(active).show(exercisesFr).commit()
				active = exercisesFr
				return@OnNavigationItemSelectedListener true
			}
			R.id.food -> {
				fm.beginTransaction().hide(active).show(foodFr).commit()
				active = foodFr
				return@OnNavigationItemSelectedListener true
			}
			R.id.me -> {
				fm.beginTransaction().hide(active).show(meFr).commit()
				active = meFr
				return@OnNavigationItemSelectedListener true
			}
		}
		false
	}

	companion object {
		fun getIntent(context: Context): Intent {
			return Intent(context, MainActivity::class.java)

		}
	}
}