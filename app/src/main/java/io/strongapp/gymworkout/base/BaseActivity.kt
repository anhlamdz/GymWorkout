package io.strongapp.gymworkout.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<DataBinding : ViewDataBinding> : AppCompatActivity() {
	lateinit var binding: DataBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, getContentView())
		bindViewModel()
		window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
				View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

		initView()
		initAction()
	}

	abstract fun initView()

	abstract fun initAction()


	abstract fun getContentView(): Int
	abstract fun bindViewModel()
	override fun onStop() {
		super.onStop()
		window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
				View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
	}
}