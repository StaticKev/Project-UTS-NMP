package com.statickev.projectnmp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.statickev.projectnmp.databinding.ActivityMainBinding

class  MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.recStudent) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = StudentAdapter()
        }
    }
}