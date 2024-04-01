package com.example.hm_2_month7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hm_2_month7.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.secundomer.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_fragmentSecundomer)
        }
        binding.timer.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_fragmentTimer)
        }
        binding.alarm.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_fragmentAlarm)
        }
    }

}