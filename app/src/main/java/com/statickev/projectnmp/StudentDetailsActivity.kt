package com.statickev.projectnmp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.statickev.projectnmp.databinding.ActivityStudentDetailsBinding
import org.json.JSONObject

class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtDetails.movementMethod = ScrollingMovementMethod()

        var student: Student? = null

        val q = Volley.newRequestQueue(binding.root.context)
        val url = "http://10.0.2.2/project-nmp/get_student_id.php"
        val stringRequest = object: StringRequest(
            Method.POST, url,
            { response ->
                Log.d("apiresult", intent.getIntExtra("id", 0).toString() + "STUDENT DETAIL | " + response)

                val obj = JSONObject(response)
                if (obj.getString("result") == "SUCCESS") {
                    val data = obj.getJSONObject("message")

                    student = Student(
                        id = data.getInt("id"),
                        name = data.getString("nama"),
                        NRP = data.getString("nrp"),
                        program = data.getString("program"),
                        aboutMe = data.getString("about"),
                        courses = data.getString("courses").split(','),
                        organization = data.getString("experiences").split(','),
                        imgUrl = data.getString("imgurl")
                    )

                    with(student) {
                        binding.txtName.text = name
                        binding.txtNRP.text = NRP
                        binding.txtDetails.text = aboutMe

                        val builder = Picasso.Builder(binding.root.context)
                        builder.listener { picasso, uri, exception ->
                            exception.printStackTrace()
                        }
                        Picasso.get().load(student.imgUrl).into(binding.imgStudent)

                        when (program) {
                            "DSAI" -> binding.rdoDSAI.isChecked = true
                            "NCS" -> binding.rdoNCS.isChecked = true
                            "IMES" -> binding.rdoIMES.isChecked = true
                            "DMT" -> binding.rdoDMT.isChecked = true
                            "GD" -> binding.rdoGD.isChecked = true
                        }
                    }
                }
            },
            { Log.e("apiresult", it.message.toString()) }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf(
                    "id" to intent.getIntExtra("id", 0).toString()
                )
            }
        }
        q.add(stringRequest)

        val items = listOf("About Me", "My Courses", "Organization/Community")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnOption.adapter = adapter

//        if (StudentData.student_data[intent.getIntExtra("position", 0)].isFriend) {
//            binding.btnReq.setEnabled(false)
//        }

        binding.spnOption.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                student?.let {
                    with(it) {
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
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.btnReq.setOnClickListener {
//            StudentData.student_data[intent.getIntExtra("position", 0)].isFriend = true
//            val nama = StudentData.student_data[intent.getIntExtra("position", 0)].name
//            MainActivity.FRIEND_COUNT++
//            val notif = AlertDialog.Builder(this)
//            notif.setTitle("Friend Request")
//            notif.setMessage("Sukses tambah " + nama + " sebagai friend. Friend Anda sekarang adalah " + MainActivity.FRIEND_COUNT + ".")
//            notif.setNeutralButton("OK") { dialog, _ ->
//                dialog.dismiss()
//            }
//            notif.create().show()
//            if (StudentData.student_data[intent.getIntExtra("position", 0)].isFriend) {
//                binding.btnReq.setEnabled(false)
//            }
        }
    }
}