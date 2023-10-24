package io.strongapp.gymworkout.ui.guide.tab

import android.view.View
import androidx.lifecycle.ViewModelProvider
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.databinding.FragmentGuideTargetBodyBinding
import io.strongapp.gymworkout.ui.guide.GuideAct
import  io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel
import io.strongapp.gymworkout.view.RulerView


class FragmentGuideGoalBody  : BaseFragment<FragmentGuideTargetBodyBinding>() {
	private lateinit var guideViewModel: GuideViewModel
	private var currWeight: Float = 0f
	private var tdee : Int = 0
	private var gender : String = ""
	private var goal : String = ""
	private var name : String = ""
	private var age : Int = 0
	private var height : Float= 0f

	override fun getLayoutRes(): Int {
		return R.layout.fragment_guide_target_body
	}


	override fun initAction() {
		binding.btnNext.setOnClickListener {
			(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
			val newUser = UserEntity(0,name,age,gender,goal,height,currWeight,tdee,totalCalo(binding.TargetWeight.text.toString().toFloat()))
				guideViewModel.insertUser(newUser)
		}
	}

	override fun initView() {
		guideViewModel = ViewModelProvider(requireActivity())[GuideViewModel::class.java]
		getData()
		guideViewModel.weight.observe(viewLifecycleOwner){ _currWeight ->
			this.currWeight = _currWeight
			binding.tvWeight.initViewParam(_currWeight,30.0f,250.0f,1f)
			binding.TargetWeight.text = _currWeight.toInt().toString()
			ReduceAndIncrease(_currWeight)
		}
		guideViewModel.tdee.observe(viewLifecycleOwner){_tdee ->
			this.tdee = _tdee
		}
		binding.tvWeight.setChooseValueChangeListener(object: RulerView.OnChooseResulterListener{
			override fun onChooseValueChange(value: Float) {
				binding.TargetWeight.text = value.toInt().toString()
				ReduceAndIncrease(value)
			}
		})
	}
	fun ReduceAndIncrease(targetWeight : Float) {
		binding.tvWeight.setDefaultSelectedValue(currWeight)
		binding.currentWeightIncrease.text = currWeight.toInt().toString()
		binding.currentWeightReduce.text = currWeight.toInt().toString()
		if(targetWeight > currWeight){
			binding.description.text = "It takes about "+ timeIncrease(targetWeight) +" days to gain weight"
			binding.iRight.visibility = View.VISIBLE
			binding.currentWeightReduce.visibility = View.INVISIBLE
			binding.iLeft.visibility = View.INVISIBLE
			binding.currentWeightIncrease.visibility = View.VISIBLE
			val increase = ((targetWeight/currWeight)-1)*100
			val roundedIncrease = "%.2f".format(increase).toFloat()
			if(roundedIncrease > 5.0f && roundedIncrease<6.0f){
				binding.tvComment.text = "Sweaty choice!"
				binding.tvComment.setTextColor(resources.getColor(R.color.orange))

				binding.descriptionComment.text= "You will gain "+" "+roundedIncrease+"% of body weight"
			}
			else if(roundedIncrease < 5.0f){
				binding.tvComment.text = "Reasonable target!"
				binding.tvComment.setTextColor(resources.getColor(R.color.green))

				binding.descriptionComment.text= "You will gain "+" "+roundedIncrease+"% of body weight"
			}
			else if (roundedIncrease > 6.0f){
				binding.tvComment.text = "Attention!"
				binding.tvComment.setTextColor(resources.getColor(R.color.colorAccent))

				binding.descriptionComment.text= "It seems that your target BMI is too high, which might cause some health problems..."
			}
		}
		else if (targetWeight < currWeight){
			binding.description.text = "It takes about "+ timeReduce(targetWeight) +" days to lose weight"
			binding.iRight.visibility = View.INVISIBLE
			binding.currentWeightReduce.visibility = View.VISIBLE
			binding.iLeft.visibility = View.VISIBLE
			binding.currentWeightIncrease.visibility = View.INVISIBLE
			val reduce = (1 - (targetWeight/currWeight))*100
			val roundedReduce = "%.2f".format(reduce).toFloat()
			if (roundedReduce < 10.0f){
				binding.tvComment.text = "Reasonable target!"
				binding.tvComment.setTextColor(resources.getColor(R.color.green))
				binding.descriptionComment.visibility = View.VISIBLE
				binding.descriptionComment.text= "You will lose"+" "+roundedReduce+"% of body weight"
			}
			else if (roundedReduce > 10.0f && roundedReduce < 16.0f){
				binding.tvComment.text = "Sweaty choice!"
				binding.tvComment.setTextColor(resources.getColor(R.color.orange))
				binding.descriptionComment.visibility = View.VISIBLE
				binding.descriptionComment.text= "You will lose"+" "+roundedReduce+"% of body weight"
			}
			else if (roundedReduce > 16.0f && roundedReduce < 21.0f){
				binding.tvComment.text = "Challenging Goal!"
				binding.tvComment.setTextColor(resources.getColor(R.color.colorAccent))
				binding.descriptionComment.visibility = View.VISIBLE
				binding.descriptionComment.text= "You will lose"+" "+roundedReduce+"% of body weight"
			}
			else if (roundedReduce > 21.0f){
				binding.tvComment.text = "Attention!"
				binding.tvComment.setTextColor(resources.getColor(R.color.colorAccent))
				binding.descriptionComment.visibility = View.VISIBLE
				binding.descriptionComment.text= "It seems that your target BMI is too lose, which might cause some health problems..."
			}
		}
		else if (targetWeight == currWeight){
			binding.iRight.visibility = View.INVISIBLE
			binding.currentWeightReduce.visibility = View.INVISIBLE
			binding.iLeft.visibility = View.INVISIBLE
			binding.currentWeightIncrease.visibility = View.INVISIBLE
			binding.tvComment.text = "Not bad!"
			binding.tvComment.setTextColor(resources.getColor(R.color.theme_color))
			binding.descriptionComment.visibility = View.VISIBLE
			binding.descriptionComment.text= "You will keep on with your current body weight!"
		}

	}
	fun timeReduce (targetWeight: Float): Float{
		val caloReduce = (currWeight - targetWeight)*7700
		val time = caloReduce / 500
		return "%.2f".format(time).toFloat()
	}
	fun timeIncrease(targetWeight: Float) : Float{
		val caloIncrease = (targetWeight - currWeight)*7700
		val time = caloIncrease / 500
		return "%.2f".format(time).toFloat()
	}
	fun getData() {
		guideViewModel.name.observe(this){_name ->
			this.name = _name
		}
		guideViewModel.age.observe(this){_age ->
			this.age = _age
		}
		guideViewModel.height.observe(this){_height ->
			this.height = _height
		}
		guideViewModel.goal.observe(this){_goal ->
			this.goal = _goal
		}
		guideViewModel.gender.observe(this){_gender ->
			this.gender = _gender
		}
		guideViewModel.tdee.observe(this){_tdee ->
			this.tdee = _tdee
		}
	}
	fun totalCalo(targetWeight: Float) : Int {
		var calories = tdee
		if (targetWeight > currWeight) {
			calories += 500
		} else {
			calories -= 500
		}
		return calories
	}
}