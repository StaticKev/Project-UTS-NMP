package com.statickev.projectnmp

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
    }

    override fun getItemCount() = StudentData.student_data.size
}