package com.statickev.projectnmp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.statickev.projectnmp.databinding.FriendsCardBinding

class FriendsAdapter(
    private var friends: List<Student>
) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>()  {

    class FriendsViewHolder(val binding: FriendsCardBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendsViewHolder {
        val binding = FriendsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FriendsViewHolder,
        position: Int
    ) {
        holder.binding.txtNama.text = friends[position].name
        holder.binding.txtNRP.text = "NRP: " + friends[position].NRP
        holder.binding.txtProgram.text = "Program " + friends[position].program

        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        Picasso.get().load(friends[position].imgUrl).into(holder.binding.imgMahasiswa)

        holder.binding.btnEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(friends[position].email))
            }

            // pilih app buat kirim email
            val sendIntent = Intent.createChooser(emailIntent, "Choose app to send email.")
            holder.itemView.context.startActivity(sendIntent)
        }
    }

    override fun getItemCount() = friends.size

}