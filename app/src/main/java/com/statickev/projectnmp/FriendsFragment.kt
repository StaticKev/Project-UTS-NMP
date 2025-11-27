package com.statickev.projectnmp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.statickev.projectnmp.databinding.FragmentFriendsBinding

class FriendsFragment : Fragment() {
    private lateinit var binding: FragmentFriendsBinding

    /*override fun onResume() {
        super.onResume()
        val q = Volley.newRequestQueue(activity)
        val url = "http://10.0.2.2/music/get_playlist.php" //10.0.2.2 to avoid firewall so it can still access the localhost
        var stringRequest = StringRequest( //volley params: method, url, callback success n failed
            Request.Method.POST, url,
            { Log.d("apiresult", it) },
            { Log.e("apiresult", it.message.toString()) })
        q.add(stringRequest)

    }*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFriendsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FriendsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}