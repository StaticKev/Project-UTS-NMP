package com.statickev.projectnmp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.statickev.projectnmp.databinding.ActivityStudentDetailsBinding

class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val student=intent.getSerializableExtra("student") as Student
        binding.txtName.text=student.name.toString()
        binding.txtNRP.text=student.name.toString()
        binding.txtProgram.text=student.name.toString()

    }
}