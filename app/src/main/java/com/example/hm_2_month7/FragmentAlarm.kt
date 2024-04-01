package com.example.hm_2_month7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.hm_2_month7.databinding.FragmentAlarmBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class FragmentAlarm : Fragment() {
    lateinit var binding: FragmentAlarmBinding
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentAlarmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        job = viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                val currentTime = Calendar.getInstance()
                val hour = currentTime.get(Calendar.HOUR_OF_DAY)
                val minute = currentTime.get(Calendar.MINUTE)

                binding.tvAlarm.text = String.format("%02d:%02d", hour, minute)

                delay(1000)
            }
        }
        binding.tvAlarm.setOnClickListener {
            val editText = EditText(requireContext())
            AlertDialog.Builder(requireContext())
                .setTitle("Введите время в формате 00:00")
                .setView(editText)
                .setPositiveButton("OK") { dialog, _ ->
                    val textOfTimer = editText.text.toString()
                    binding.tvAlarm.text = textOfTimer
                    dialog.dismiss()

                }.show()
            job?.cancel()
        }
        binding.btnSetAlarm.setOnClickListener {

            Toast.makeText(requireContext(), "Время установлено", Toast.LENGTH_SHORT).show()
            job = viewLifecycleOwner.lifecycleScope.launch {
                while (true) {
                    val currentTime = Calendar.getInstance()
                    val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
                    val currentMinute = currentTime.get(Calendar.MINUTE)

                    val textOfTimer = binding.tvAlarm.text.toString()
                    val parts = textOfTimer.split(":")
                    if (parts.size == 2) {
                        val hours = parts[0].toInt()
                        val minutes = parts[1].toInt()

                        if (hours == currentHour && minutes == currentMinute) {
                            Toast.makeText(requireContext(), "Будильник!!!", Toast.LENGTH_SHORT)
                                .show()
                            job?.cancel()
                        }
                    }
                    delay(1000)
                }
            }
        }
    }
}

