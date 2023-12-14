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


	override fun getLayoutRes(): Int {
		return R.layout.fragment_guide_target_body
	}


	override fun initAction() {
		binding.btnNext.setOnClickListener {
			(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
			guideViewModel.setTotalCalo(totalCalo(binding.TargetWeight.text.toString().toFloat()))
			guideViewModel.setTargetWeight(binding.TargetWeight.text.toString().toFloat())
		}
	}

	override fun initView() {
		guideViewModel = ViewModelProvider(requireActivity())[GuideViewModel::class.java]
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
			binding.description.text = "Phải mất khoảng "+ timeIncrease(targetWeight) +" ngày để tăng cân"
			binding.iRight.visibility = View.VISIBLE
			binding.description.visibility = View.VISIBLE
			binding.currentWeightReduce.visibility = View.INVISIBLE
			binding.iLeft.visibility = View.INVISIBLE
			binding.currentWeightIncrease.visibility = View.VISIBLE
			val increase = ((targetWeight/currWeight)-1)*100
			val roundedIncrease = "%.2f".format(increase).replace(",", ".").toFloat()
			if(roundedIncrease > 5.0f && roundedIncrease<9.0f){
				binding.tvComment.text = "Sweaty choice!"
				binding.tvComment.setTextColor(resources.getColor(R.color.orange))
				binding.description.visibility = View.VISIBLE
				binding.descriptionComment.text= "Bạn sẽ tăng được "+" "+roundedIncrease+"% trọng lượng cơ thể"
			}
			else if(roundedIncrease < 5.0f){
				binding.tvComment.text = "Mục tiêu hợp lý!"
				binding.tvComment.setTextColor(resources.getColor(R.color.green))
				binding.description.visibility = View.VISIBLE
				binding.descriptionComment.text= "Bạn sẽ tăng được "+" "+roundedIncrease+"% trọng lượng cơ thể"
			}

			else if (roundedIncrease > 9.0f){
				binding.tvComment.text = "Chú ý!"
				binding.tvComment.setTextColor(resources.getColor(R.color.red))
				binding.description.visibility = View.INVISIBLE
				binding.descriptionComment.text= "Có vẻ như chỉ số BMI mục tiêu của bạn quá cao, điều này có thể gây ra một số vấn đề về sức khỏe..."
			}

		}
		else if (targetWeight < currWeight){
			binding.description.text = "Phải mất khoảng "+ timeReduce(targetWeight) +" ngày để giảm cân"
			binding.iRight.visibility = View.INVISIBLE
			binding.currentWeightReduce.visibility = View.VISIBLE
			binding.iLeft.visibility = View.VISIBLE
			binding.description.visibility = View.VISIBLE
			binding.currentWeightIncrease.visibility = View.INVISIBLE
			val reduce = (1 - (targetWeight/currWeight))*100
			val roundedReduce = "%.2f".format(reduce).replace(",", ".").toFloat()
			if (roundedReduce < 10.0f){
				binding.tvComment.text = "Mục tiêu hợp lý!"
				binding.tvComment.setTextColor(resources.getColor(R.color.green))
				binding.descriptionComment.visibility = View.VISIBLE
				binding.description.visibility = View.VISIBLE
				binding.descriptionComment.text= "Bạn sẽ giảm"+" "+roundedReduce+"% trọng lượng cơ thể"
			}
			else if (roundedReduce > 10.0f && roundedReduce < 16.0f){
				binding.tvComment.text = "Sweaty choice!"
				binding.tvComment.setTextColor(resources.getColor(R.color.orange))
				binding.descriptionComment.visibility = View.VISIBLE
				binding.description.visibility = View.VISIBLE
				binding.descriptionComment.text= "Bạn sẽ giảm"+" "+roundedReduce+"% trọng lượng cơ thể"
			}
			else if (roundedReduce > 16.0f && roundedReduce < 21.0f){
				binding.tvComment.text = "Mục tiêu đầy thử thách!"
				binding.tvComment.setTextColor(resources.getColor(R.color.red))
				binding.descriptionComment.visibility = View.VISIBLE
				binding.description.visibility = View.VISIBLE
				binding.descriptionComment.text= "Bạn sẽ giảm $roundedReduce% trọng lượng cơ thể"
			}
			else if (roundedReduce > 21.0f){
				binding.tvComment.text = "Chú ý!"
				binding.tvComment.setTextColor(resources.getColor(R.color.red))
				binding.descriptionComment.visibility = View.VISIBLE
				binding.description.visibility = View.INVISIBLE
				binding.descriptionComment.text= "Có vẻ như chỉ số BMI mục tiêu của bạn đang bị giảm quá mức, điều này có thể gây ra một số vấn đề về sức khỏe..."
			}
		}
		else if (targetWeight == currWeight){
			binding.iRight.visibility = View.INVISIBLE
			binding.currentWeightReduce.visibility = View.INVISIBLE
			binding.iLeft.visibility = View.INVISIBLE
			binding.currentWeightIncrease.visibility = View.INVISIBLE
			binding.tvComment.text = "Không tệ!"
			binding.tvComment.setTextColor(resources.getColor(R.color.theme_color))
			binding.descriptionComment.visibility = View.VISIBLE
			binding.descriptionComment.text= "Bạn sẽ tiếp tục với trọng lượng cơ thể hiện tại của mình!"
		}

	}
	fun timeReduce (targetWeight: Float): Float{
		val caloReduce = (currWeight - targetWeight)*7700
		val time = caloReduce / 500
		return "%.2f".format(time).replace(",", ".").toFloat()
	}
	fun timeIncrease(targetWeight: Float) : Float{
		val caloIncrease = (targetWeight - currWeight)*7700
		val time = caloIncrease / 500
		return "%.2f".format(time).replace(",", ".").toFloat()
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