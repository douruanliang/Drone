package com.dourl.drone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dourl.drone.adapter.PosterAdapter
import com.dourl.drone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModelFactory: MainViewModelFactory = MainViewModelFactory()
    private val viewModel:MainViewModel by lazy {
        ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
        DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
            adapter = PosterAdapter()
        }
    }
}