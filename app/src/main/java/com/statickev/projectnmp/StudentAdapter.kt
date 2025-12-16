package com.statickev.projectnmp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.statickev.projectnmp.databinding.StudentCardBinding

class StudentAdapter(
    private var students: List<Student>
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder(val binding: StudentCardBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentViewHolder {
        val binding = StudentCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: StudentViewHolder,
        position: Int
    ) {
        holder.binding.txtNama.text = students[position].name
        holder.binding.txtNRP.text = students[position].NRP
        holder.binding.txtProgram.text = students[position].program

        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        Picasso.get().load(students[position].imgUrl).into(holder.binding.imgMahasiswa)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, StudentDetailsActivity::class.java)
            intent.putExtra("id", students[position].id)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = students.size
}