package com.statickev.projectnmp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.statickev.projectnmp.databinding.ActivityStudentDetailsBinding

class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ini gpp lah ya, biar bisa di scroll gais. Masa 0 gara-gara ini :)
        // Leon: mending jangan deh, maen aman ae :)))
        // binding.txtDetails.movementMethod = android.text.method.ScrollingMovementMethod()

        val items = listOf("About Me", "My Courses", "Organization/Community")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnOption.adapter = adapter

        if (StudentData.student_data[intent.getIntExtra("position", 0)].isFriend) {
            binding.btnReq.setEnabled(false)
        }

        binding.spnOption.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                with(StudentData.student_data[intent.getIntExtra("position", 0)]) {
                    when (items[position]) {
                        "About Me" -> binding.txtDetails.text = aboutMe
                        "My Courses" -> {
                            var content = ""
                            courses.forEach { course -> content += "- $course\n" }
                            binding.txtDetails.text = content
                        }
                        "Organization/Community" -> {
                            var content = ""
                            organization.forEach { org -> content += "- $org\n" }
                            binding.txtDetails.text = content
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.btnReq.setOnClickListener {
            StudentData.student_data[intent.getIntExtra("position", 0)].isFriend = true
            val nama = StudentData.student_data[intent.getIntExtra("position", 0)].name
            MainActivity.FRIEND_COUNT++
            val notif = AlertDialog.Builder(this)
            notif.setTitle("Friend Request")
            notif.setMessage("Sukses tambah " + nama + " sebagai friend. Friend Anda sekarang adalah " + MainActivity.FRIEND_COUNT + ".")
            notif.setNeutralButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            notif.create().show()
            if (StudentData.student_data[intent.getIntExtra("position", 0)].isFriend) {
                binding.btnReq.setEnabled(false)
            }
        }

        with(StudentData.student_data[intent.getIntExtra("position", 0)]) {
            binding.txtName.text = name
            binding.txtNRP.text = NRP
            binding.txtDetails.text = aboutMe
            binding.imgStudent.setImageResource(imageId)

            when (program) {
                "DSAI" -> binding.rdoDSAI.isChecked = true
                "NCS" -> binding.rdoNCS.isChecked = true
                "IMES" -> binding.rdoIMES.isChecked = true
                "DMT" -> binding.rdoDMT.isChecked = true
                "GD" -> binding.rdoGD.isChecked = true
            }
        }
    }
}