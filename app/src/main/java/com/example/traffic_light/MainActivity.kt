package com.example.traffic_light

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.traffic_light.enum.TrafficLight

class MainActivity : AppCompatActivity() {

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
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        image_circle_red = findViewById(R.id.image_circle_red)
        image_circle_yellow = findViewById(R.id.image_circle_yellow)
        image_circle_green = findViewById(R.id.image_circle_green)


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
                image_circle_red?.setImageResource(R.drawable.circle_red)
                image_circle_yellow?.setImageResource(R.drawable.circle_gray)
                image_circle_green?.setImageResource(R.drawable.circle_gray)
            }
            TrafficLight.YELLOW -> {
                image_circle_red?.setImageResource(R.drawable.circle_gray)
                image_circle_yellow?.setImageResource(R.drawable.circle_yellow)
                image_circle_green?.setImageResource(R.drawable.circle_gray)
            }
            TrafficLight.GREEN -> {
                image_circle_red?.setImageResource(R.drawable.circle_gray)
                image_circle_yellow?.setImageResource(R.drawable.circle_gray)
                image_circle_green?.setImageResource(R.drawable.circle_green)
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