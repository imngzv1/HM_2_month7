package com.example.hm_2_month7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hm_2_month7.databinding.FragmentSecundomerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentSecundomer : Fragment() {
    private lateinit var binding: FragmentSecundomerBinding
    private var job: Job? = null
    private var seconds: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSecundomerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStart.setOnClickListener {
            binding.btnStart.text="Начать"
            job = CoroutineScope(Dispatchers.Main).launch {
                while (seconds <= 100000) {
                    delay(1000)
                    seconds++
                    updateTimer()
                }
            }
        }
        binding.btnPause.setOnClickListener {
            job?.cancel()
            binding.btnStart.text="Продолжить"
        }
    }

    private fun updateTimer() {
        val minutes = seconds / 60
        val seconds = seconds % 60

        val textFormat = String.format("%02d:%02d", minutes, seconds)
        binding.tvSec.text = textFormat
    }
}
