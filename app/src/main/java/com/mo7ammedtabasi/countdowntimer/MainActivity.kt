package com.mo7ammedtabasi.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.mo7ammedtabasi.countdowntimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val START_TIME_IN_MILLIS : Long= 25 * 60 * 1000
    private var remainingTime = START_TIME_IN_MILLIS
    private var timer: CountDownTimer? = null
    private var isTimerRunning = false

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.startBtn.setOnClickListener {
            if (!isTimerRunning){
                startTimer()
                binding.titleTv.text = resources.getString(R.string.keep_going)
            }
        }
        binding.resetTv.setOnClickListener {
            resetTimer()
        }
    }


    private fun startTimer() {
        timer = object : CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                updateTimerText()
                binding.progressBar.progress = remainingTime.toDouble().div(START_TIME_IN_MILLIS.toDouble()).times(100).toInt()
            }
            override fun onFinish() {
                binding.titleTv.text = resources.getString(R.string.take_pomodoro)
                isTimerRunning = false
                binding.progressBar.progress = 100
            }
        }.start()
        isTimerRunning = true
    }

    private fun resetTimer() {
        timer?.cancel()
        remainingTime = START_TIME_IN_MILLIS
        updateTimerText()
        binding.titleTv.text = resources.getText(R.string.take_pomodoro)
        isTimerRunning = false
        binding.progressBar.progress = 100
    }


    private fun updateTimerText(){
        val minute = remainingTime.div(1000).div(60)
        val second = remainingTime.div(1000) % 60
        val formattedTimer = String.format("%02d:%02d",minute,second)
        binding.timerTv.text = formattedTimer
    }
}