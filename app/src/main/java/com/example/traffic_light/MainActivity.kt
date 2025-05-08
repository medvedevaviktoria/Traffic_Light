package com.example.traffic_light

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.traffic_light.databinding.ActivityMainBinding
import com.example.traffic_light.enum.TrafficLight

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    //переменные для изображений
    var image_circle_red:ImageView? = null
    var image_circle_yellow:ImageView? = null
    var image_circle_green:ImageView? = null
    //переменные для работы кода
    private var current_light = TrafficLight.RED //начинаем показывать цвета с красного
    private var lastWasGreen: Boolean = false //если true - последним цветом перед жёлтым был зелёный, если false - последним цветом перед жёлтым был красный
    //переменные для изменения ориентации экрана
    private val STATE_CURRENT_LIGHT = "current_light"
    private val STATE_LAST_WAS_GREEN = "last_was_green"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //восстановление состояния после изменения ориентации экрана
        savedInstanceState?.let {
            current_light = TrafficLight.valueOf(it.getString(STATE_CURRENT_LIGHT).toString())
            lastWasGreen = it.getBoolean(STATE_LAST_WAS_GREEN)
        }
        updateLights()
    }

    fun onClickChangeColor(view: View) {
        changeColor()
        updateLights()
    }

    private fun updateLights() {
        when (current_light) {
            TrafficLight.RED -> {
                binding.imageCircleRed.setImageResource(R.drawable.circle_red)
                binding.imageCircleYellow.setImageResource(R.drawable.circle_gray)
                binding.imageCircleGreen.setImageResource(R.drawable.circle_gray)
            }
            TrafficLight.YELLOW -> {
                binding.imageCircleRed.setImageResource(R.drawable.circle_gray)
                binding.imageCircleYellow.setImageResource(R.drawable.circle_yellow)
                binding.imageCircleGreen.setImageResource(R.drawable.circle_gray)
            }
            TrafficLight.GREEN -> {
                binding.imageCircleRed.setImageResource(R.drawable.circle_gray)
                binding.imageCircleYellow.setImageResource(R.drawable.circle_gray)
                binding.imageCircleGreen.setImageResource(R.drawable.circle_green)
            }
        }
    }

    private fun changeColor() {
        when (current_light) {
            TrafficLight.RED -> {
                lastWasGreen = false
                current_light = TrafficLight.YELLOW
            }
            TrafficLight.YELLOW -> {
                current_light = if (lastWasGreen) TrafficLight.RED else TrafficLight.GREEN
            }
            TrafficLight.GREEN -> {
                lastWasGreen = true
                current_light = TrafficLight.YELLOW
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_CURRENT_LIGHT, current_light.name)
        outState.putBoolean(STATE_LAST_WAS_GREEN, lastWasGreen)
    }
}