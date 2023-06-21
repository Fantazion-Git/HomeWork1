package com.example.homework1

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.homework1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        init()
    }

    private fun init() {
        MediaPlayerHelper.initialize { this }
        binding.bPlay.setOnClickListener { MediaPlayerHelper.playOrPauseMusic() }
        binding.bPrevious.setOnClickListener { MediaPlayerHelper.playPrevMusic() }
        binding.bNext.setOnClickListener { MediaPlayerHelper.playNextMusic() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


