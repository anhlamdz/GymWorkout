package io.strongapp.gymworkout.ui.me.marco

import android.graphics.Color
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.databinding.ActivityMarcoNutritionBinding
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

class MarcoAct: BaseActivity<ActivityMarcoNutritionBinding>() {
	private lateinit var userViewModel: UserViewModel
	private lateinit var user : UserEntity
	override fun initView() {
		userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
		lifecycleScope.launch {
			user = userViewModel.getInfo()
			initializeChartAndSeekBar()
		}
		binding.marco.isRotationEnabled = false
		binding.marco.setTouchEnabled(false)

	}

	override fun initAction() {
		binding.btnBack.setOnClickListener {
			finish()
		}
		binding.btnFinish.setOnClickListener {
			saveMarco(
				binding.controlCarb.progress.toFloat(),
				binding.controlProtein.progress.toFloat(),
				binding.controlFat.progress.toFloat()
			)
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_marco_nutrition
	}

	override fun bindViewModel() {

	}
	private fun createSeekBarChangeListener() : SeekBar.OnSeekBarChangeListener{
		return object : SeekBar.OnSeekBarChangeListener{
			override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
				updateChartData()
			}

			override fun onStartTrackingTouch(p0: SeekBar?) {

			}

			override fun onStopTrackingTouch(p0: SeekBar?) {

			}

		}
	}
	private fun updateChartData() {
		val carbValue = binding.controlCarb.progress.toFloat()
		val proteinValue = binding.controlProtein.progress.toFloat()
		val fatValue = binding.controlFat.progress.toFloat()

		binding.caloCarb.text = "${String.format("%.2f", (carbValue / 100) * user.totalCalorie)} Calo"
		binding.caloProtein.text = "${String.format("%.2f", (proteinValue / 100) * user.totalCalorie)} Calo"
		binding.caloFat.text = "${String.format("%.2f", (fatValue / 100) * user.totalCalorie)} Calo"

		binding.gamCarb.text = "${String.format("%.2f", ((carbValue / 100) * user.totalCalorie) / 4)} g"
		binding.gamProtein.text = "${String.format("%.2f", ((proteinValue / 100) * user.totalCalorie) / 4)} g"
		binding.gamFat.text = "${String.format("%.2f", ((fatValue / 100) * user.totalCalorie) / 9)} g"


		val newEntity = listOf(
			PieEntry(carbValue, "Carb"),
			PieEntry(proteinValue, "Protein"),
			PieEntry(fatValue, "Fat")
		)

		val dataSet = PieDataSet(newEntity, "Thành phần dinh dưỡng")
		dataSet.colors = mutableListOf(
			Color.parseColor("#43a5dc"),
			getColor(R.color.orange),
			Color.parseColor("#ff2e78")
		)
		dataSet.valueTextSize = 16f
		dataSet.valueTextColor = getColor(R.color.white)

		val newData = PieData(dataSet)
		binding.marco.data = newData
		binding.marco.invalidate()
	}
	private fun initializeChartAndSeekBar() {
		val entity = listOf(
			PieEntry(30f,"Carb"),
			PieEntry(40f,"Protein"),
			PieEntry(30f,"Fat")
		)


		val color = mutableListOf<Int>()
		color.add(Color.parseColor("#43a5dc"))
		color.add(getColor(R.color.orange))
		color.add(Color.parseColor("#ff2e78"))

		val pieDataSet = PieDataSet(entity,"Thành phần dinh dưỡng")
		pieDataSet.colors = color
		pieDataSet.valueTextSize = 16f
		pieDataSet.valueTextColor = getColor(R.color.white)
		val data = PieData(pieDataSet)


		binding.marco.holeRadius = 40f
		binding.marco.transparentCircleRadius = 45f
		binding.marco.centerText = "Nutrition"
		binding.marco.setCenterTextSize(18f)
		binding.marco.legend.isEnabled = false
		binding.marco.data = data
		binding.marco.invalidate()


		binding.controlCarb.setOnSeekBarChangeListener(createSeekBarChangeListener())
		binding.controlProtein.setOnSeekBarChangeListener(createSeekBarChangeListener())
		binding.controlFat.setOnSeekBarChangeListener(createSeekBarChangeListener())


		binding.controlCarb.progress = 30
		binding.controlProtein.progress = 40
		binding.controlFat.progress = 30
	}
	private fun saveMarco(carbValue : Float, proteinValue : Float, fatValue : Float){
		val pref = this.getSharedPreferences("marco",MODE_PRIVATE)
		val editor = pref.edit()
		editor.putString("marco","${carbValue},${proteinValue},${fatValue}")
		editor.commit()
	}
}