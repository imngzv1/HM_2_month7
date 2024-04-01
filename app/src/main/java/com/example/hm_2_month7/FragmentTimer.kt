package com.example.hm_2_month7

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hm_2_month7.databinding.FragmentTimerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FragmentTimer : Fragment() {
    lateinit var binding: FragmentTimerBinding
    private var job: Job? = null
    private var timer: CountDownTimer? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentTimerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTimer.setOnClickListener {
            job = CoroutineScope(Dispatchers.Main).launch {
                val editText = EditText(requireContext())
                AlertDialog.Builder(requireContext())
                    .setTitle("Введите время в формате 00:00")
                    .setView(editText)
                    .setPositiveButton("OK") { dialog, _ ->
                        val textOfTimer = editText.text.toString()
                        if (textOfTimer.isNotEmpty()) {
                            val parts = textOfTimer.split(":")
                            if (parts.size == 2) {
                                val minutes = parts[0].toLong()
                                val seconds = parts[1].toLong()
                                val milliseconds = (minutes * 60 + seconds) * 1000
                                binding.tvTimer.text = textOfTimer
                                dialog.dismiss()
                                backTimer(milliseconds)
                            } else {
                                Toast.makeText(requireContext(), "Введите время в формате минуты:секунды", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Введите время", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Отмена") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun backTimer(milliseconds: Long) {
        timer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(timeM: Long) {
                val minutes = timeM / 1000 / 60
                val seconds = (timeM / 1000) % 60
                binding.tvTimer.text = String.format("%02d:%02d", minutes, seconds)
            }
            override fun onFinish() {
                binding.tvTimer.text = "Finish"
            }
        }.start()
    }
}