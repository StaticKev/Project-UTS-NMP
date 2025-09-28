package com.statickev.projectnmp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.statickev.projectnmp.databinding.StudentCardBinding

class StudentAdapter() : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder(val binding: StudentCardBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentViewHolder {
        var binding = StudentCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: StudentViewHolder,
        position: Int
    ) {
        holder.binding.txtNama.text = StudentData.student_data[position].name
        holder.binding.txtNRP.text = "NRP: " + StudentData.student_data[position].NRP
        holder.binding.txtProgram.text = "Program: " + StudentData.student_data[position].program
        holder.binding.imgMahasiswa.setImageResource(StudentData.student_data[position].imageId)

        // Click listener for the whole card
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, StudentDetails::class.java)

            intent.putExtra("STUDENT_OBJ", StudentData.student_data[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = StudentData.student_data.size
}