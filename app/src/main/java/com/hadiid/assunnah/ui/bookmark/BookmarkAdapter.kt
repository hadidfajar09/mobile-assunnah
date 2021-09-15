package com.hadiid.assunnah.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hadiid.assunnah.databinding.AdapterBookmarkBinding
import com.hadiid.assunnah.persistence.CourseEntity
import com.hadiid.assunnah.util.loadImage

class BookmarkAdapter(
    var courses: ArrayList<CourseEntity>, //dari model
    var listener: OnAdapterListener? //aksi saat diklik
): RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() { //implement ke adapter



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(AdapterBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)) //menggunakan layout adapter

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position] //atur model
        holder.binding.textTitle.text = course.title //pick id adapter dri model name
        holder.binding.textMentor.text = course.mentor //pick id adapter dri model name
        loadImage(holder.binding.imageThumbnail, course.thumbnail)
        holder.itemView.setOnClickListener {
            listener?.onDetail(course)
        }

        holder.binding.imageDelete.setOnClickListener {
            listener?.onRemove(course)
        }




    }

    override fun getItemCount() = courses.size

    class ViewHolder(val binding: AdapterBookmarkBinding):RecyclerView.ViewHolder(binding.root) //binding pada adapter

    fun addList(list: List<CourseEntity>){
        courses.clear()
        courses.addAll(list)
        notifyDataSetChanged()//refresh recycle
    }



    interface OnAdapterListener{
        fun onDetail(course: CourseEntity)
        fun onRemove(course: CourseEntity)
    }

}