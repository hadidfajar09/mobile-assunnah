package com.hadiid.assunnah.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hadiid.assunnah.databinding.AdapterPopularBinding
import com.hadiid.assunnah.ui.course.CourseData
import com.hadiid.assunnah.util.loadImage

class PopularAdapter(
    var courses: ArrayList<CourseData>, //dari model
    var listener: OnAdapterListener? //aksi saat diklik
): RecyclerView.Adapter<PopularAdapter.ViewHolder>() { //implement ke adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(AdapterPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)) //menggunakan layout adapter

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position] //atur model
        loadImage(holder.binding.imageThumbnail, course.thumbnail)
        holder.binding.textTitle.text = course.title
        holder.binding.textMentor.text = course.mentor
        holder.itemView.setOnClickListener {
            listener?.onCLick(course)
        }



    }

    override fun getItemCount() = courses.size

    class ViewHolder(val binding: AdapterPopularBinding):RecyclerView.ViewHolder(binding.root) //binding pada adapter

    fun addList(list: List<CourseData>){
        courses.clear()
        courses.addAll(list)
        notifyDataSetChanged()
    }


    interface OnAdapterListener{
        fun onCLick(course: CourseData)
    }

}