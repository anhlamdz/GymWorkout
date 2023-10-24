package io.strongapp.gymworkout.ui.guide.tab

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.lifecycle.ViewModelProvider
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.databinding.FragmentGuideWeightBinding
import io.strongapp.gymworkout.ui.guide.GuideAct
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel
import io.strongapp.gymworkout.view.RulerView


class FragmentGuideWeight  : BaseFragment<FragmentGuideWeightBinding>() {
	private lateinit var guideViewModel: GuideViewModel
	private var defaultWeight : Float = 73.0f
	private var height : Float = 0f


	override fun getLayoutRes(): Int {
		return R.layout.fragment_guide_weight
	}

	override fun initAction() {
		binding.tvWeight.setChooseValueChangeListener(object : RulerView.OnChooseResulterListener{
			override fun onChooseValueChange(value: Float) {
				binding.currentWeight.text = value.toInt().toString()
					BmiTextAndColor(value)
			}

		})

		binding.infoBmi.setOnClickListener {
			showPopup(requireContext(),binding.tvWeight)
		}

		binding.btnNext.setOnClickListener {
			(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
			guideViewModel.setWeight(binding.currentWeight.text.toString().toFloat())
			guideViewModel.setBmi(binding.tvBMI.text.toString().toFloat())
		}
	}


	override fun initView() {
		binding.tvWeight.initViewParam(73.0f,30.0f,250.0f,1f)
		guideViewModel = ViewModelProvider(requireActivity())[GuideViewModel::class.java]
		guideViewModel.height.observe(viewLifecycleOwner){ _height ->
			this.height = (_height/100)
			BmiTextAndColor(defaultWeight)
		}

	}
	fun BmiTextAndColor(weight: Float){
			binding.descriptionBMI.visibility = View.VISIBLE
			val bmi = weight / (height * height)
			val roundedBmi = "%.2f".format(bmi)
			binding.tvBMI.text = roundedBmi
			if (bmi <= 18.4) {
				binding.tvBMI.setTextColor(resources.getColor(R.color.theme_color))
				binding.descriptionBMI.text = "You have a great potential to get in better shape, move now!"
			} else if (18.5 <= bmi && bmi <= 24.9) {
				binding.tvBMI.setTextColor(resources.getColor(R.color.green))
				binding.descriptionBMI.text = "You've got a great figure! Keep it up!"
			} else if (bmi > 25.0) {
				binding.tvBMI.setTextColor(resources.getColor(R.color.orange))
				binding.descriptionBMI.text = "You only need a bit more sweat exercises to see a fitter you!"
			}
	}
	private fun showPopup(context: Context, anchorView: View) {
		val popupView = LayoutInflater.from(context).inflate(R.layout.pop_bmi_des, null)
		val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
		popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		val location = IntArray(2)
		anchorView.getLocationOnScreen(location)
		val x = location[0] + anchorView.height
		val y = location[1] - popupView.width-33
		popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,x, y)
		Handler().postDelayed({
			popupWindow.dismiss()
		}, 3000)
	}


}