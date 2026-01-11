package com.statickev.projectnmp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.statickev.projectnmp.databinding.FragmentHomeBinding
import com.statickev.projectnmp.databinding.FragmentSettingsBinding
import org.json.JSONObject

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = requireContext()
            .getSharedPreferences("theme_prefs", 0)

        val isDarkMode = prefs.getBoolean("dark_mode", false)
        binding.swNightMode.setOnCheckedChangeListener(null)
        binding.swNightMode.isChecked = isDarkMode

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )

        binding.swNightMode.setOnCheckedChangeListener { _, isChecked ->

            prefs.edit()
                .putBoolean("dark_mode", isChecked)
                .apply()

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )
        }
        binding.btnResetFriends.setOnClickListener {
            val q = Volley.newRequestQueue(activity)
            val url = "http://10.0.2.2/project-nmp/reset_friends.php"
            val stringRequest = StringRequest(
                Request.Method.POST, url,
                { response ->
                    Log.d("apiresult", response)

                    val obj = JSONObject(response)
                    if (obj.getString("result") == "SUCCESS") {
                        Toast.makeText(activity, "Successfully reset all friends.", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(activity, "There is no friends to be reset.", Toast.LENGTH_SHORT).show()
                },
                { Log.e("apiresult", it.message.toString()) })
            q.add(stringRequest)
        }
    }


}