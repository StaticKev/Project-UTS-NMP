package com.statickev.projectnmp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.statickev.projectnmp.databinding.ActivityStudentDetailsBinding
import org.json.JSONObject

class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentDetailsBinding
    var STUDENT_ID = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var student: Student? = null
        STUDENT_ID = intent.getStringExtra("nrp").toString()

        binding.txtDetails.movementMethod = ScrollingMovementMethod()
        // NOTE: hati2, ini ga diajarin nde kelas
        //       takut e ada pengurangan nilai atau 0 bahkan

        // load detail student
        val q = Volley.newRequestQueue(binding.root.context)
        val url_details = "http://10.0.2.2/project-nmp/get_student_id.php"
        val stringRequest_details = object: StringRequest(
            Method.POST, url_details,
            { response ->
                Log.d("apiresult", STUDENT_ID + " STUDENT DETAIL | " + response)

                val obj = JSONObject(response)
                if (obj.getString("result") == "SUCCESS") {
                    val data = obj.getJSONObject("message")

                    student = Student(
                        name = data.getString("nama"),
                        NRP = data.getString("nrp"),
                        program = data.getString("program"),
                        aboutMe = data.getString("about"),
                        courses = data.getString("courses").split(','),
                        organization = data.getString("experiences").split(','),
                        imgUrl = data.getString("imgurl"),
                        email = data.getString("email")
                    )

                    with(student) {
                        binding.txtName.text = name
                        binding.txtNRP.text = "NRP $NRP"
                        binding.txtEmail.text = "Email: \n$email"
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
            override fun getParams(): Map<String?, String?>? {
                val params = HashMap<String?, String?>()
                params["nrp"] = STUDENT_ID
                return params
            }
        }
        q.add(stringRequest_details)

        val items = listOf("About Me", "My Courses", "Organization/Community")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnOption.adapter = adapter

        // cek apakah sudah berteman (web service tambahan)
        val url_friend = "http://10.0.2.2/project-nmp/check_friend.php"
        val stringRequest_friend = object: StringRequest(
            Method.POST, url_friend,
            { response ->
                Log.d("apiresult", STUDENT_ID + " isFriend | " + response)

                val obj = JSONObject(response)
                if (obj.getString("result") == "SUCCESS") {
                    val data = obj.getString("message")
                    if (data == "isFriend") {
                        binding.btnReq.setEnabled(false)
                    }
                }
            },
            { Log.e("apiresult", it.message.toString()) }
        ) {
            override fun getParams(): Map<String?, String?>? {
                val params = HashMap<String?, String?>()
                params["nrp"] = STUDENT_ID
                return params
            }
        }
        q.add(stringRequest_friend)

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
            student?.let{
                // tambah teman ke database
                val url_req = "http://10.0.2.2/project-nmp/insert_friend.php"
                val stringRequest_req = object: StringRequest(
                    Method.POST, url_req,
                    { response ->
                        Log.d("apiresult", STUDENT_ID + " INSERT FRIEND | " + response)

                        val obj = JSONObject(response)
                        if (obj.getString("result") == "SUCCESS") {
                            val friendCount = obj.getString("count")
                            val notif = AlertDialog.Builder(this)
                            notif.setTitle("Friend Request")
                            notif.setMessage("Sukses tambah " + student.name + " sebagai friend. Friend Anda sekarang adalah " + friendCount + ".")
                            notif.setNeutralButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            notif.create().show()
                            binding.btnReq.setEnabled(false)
                        }
                    },
                    { Log.e("apiresult", it.message.toString()) }
                ) {
                    override fun getParams(): Map<String?, String?>? {
                        val params = HashMap<String?, String?>()
                        params["nrp"] = STUDENT_ID
                        return params
                    }
                }
                q.add(stringRequest_req)
            }
        }
    }
}