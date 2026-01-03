package com.statickev.projectnmp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
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
        holder.binding.txtNRP.text = friends[position].NRP
        holder.binding.txtProgram.text = friends[position].program

        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        Picasso.get().load(friends[position].imgUrl).into(holder.binding.imgMahasiswa)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, StudentDetailsActivity::class.java)
            intent.putExtra("id", friends[position].id)

            holder.itemView.context.startActivity(intent)
        }

        holder.binding.btnEmail.setOnClickListener {
            // TODO: Panggil intent di sini!
        }
    }

    override fun getItemCount() = friends.size

}