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

        val student = StudentData.student_data[intent.getIntExtra("position", 0)]
        binding.txtName.text=student.name.toString()
        binding.txtNRP.text=student.NRP.toString()
        binding.txtDetails.text = student.biodata.toString() //sementara
        binding.imgStudent.setImageResource(student.imageId)

        val program = student.program
        if(program == "DSAI"){
            binding.rdoDSAI.isChecked = true
        } else if(program == "NCS"){
            binding.rdoNCS.isChecked = true
        } else if(program == "IMES"){
            binding.rdoIMES.isChecked = true
        } else if(program == "DMT"){
            binding.rdoDMT.isChecked = true
        } else if(program == "GD"){
            binding.rdoGD.isChecked = true
        }
    }
}