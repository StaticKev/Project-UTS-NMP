package com.statickev.projectnmp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.statickev.projectnmp.databinding.FragmentHomeBinding
import org.json.JSONObject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var studentAdapter: StudentAdapter

    override fun onResume() {
        super.onResume()
        val q = Volley.newRequestQueue(activity)
        val url = "http://10.0.2.2/project-nmp/get_all_students.php"
        val stringRequest = StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("apiresult", response)

                val obj = JSONObject(response)
                if (obj.getString("result") == "SUCCESS") {
                    val students = mutableListOf<Student>()
                    val data = obj.getJSONArray("message")

                    for (i in 0 until data.length()) {
                        val studentObj = data.getJSONObject(i)
                        val student = Student(
                            name = studentObj.getString("nama"),
                            NRP = studentObj.getString("nrp"),
                            program = studentObj.getString("program"),
                            aboutMe = studentObj.getString("about"),
                            courses = studentObj.getString("courses").split(','),
                            organization = studentObj.getString("experiences").split(','),
                            imgUrl = studentObj.getString("imgurl"),
                            email = studentObj.getString("email")
                        )

                        students.add(student)
                    }

                    studentAdapter = StudentAdapter(students.toList())
                    binding.rvStudents.adapter = studentAdapter
                    binding.rvStudents.layoutManager = LinearLayoutManager(binding.root.context)
                }
            },
            { Log.e("apiresult", it.message.toString()) })
        q.add(stringRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}