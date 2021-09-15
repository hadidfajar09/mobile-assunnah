package com.hadiid.assunnah.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hadiid.assunnah.R
import com.hadiid.assunnah.databinding.AdapterCategoryBinding

class CategoryAdapter(
    var categories: ArrayList<CategoryData>, //dari model
    var listener: OnAdapterListener? //aksi saat diklik
): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() { //implement ke adapter

    private val items = arrayListOf<TextView>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(AdapterCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)) //menggunakan layout adapter

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position] //atur model
        holder.binding.textCategory.text = category.name //pick id adapter dri model name
        items.add(holder.binding.textCategory)
        holder.itemView.setOnClickListener {
            setColor(holder.binding.textCategory) //color saat d klik
            listener?.onCLick(category)
        }




    }

    override fun getItemCount() = categories.size

    class ViewHolder(val binding: AdapterCategoryBinding):RecyclerView.ViewHolder(binding.root) //binding pada adapter

    fun addList(list: List<CategoryData>){
        categories.clear()
        categories.add(CategoryData(id = 0, name = "Semua")) //tmbh kategori
        categories.addAll(list)
        notifyDataSetChanged()//refresh recycle
    }

    private fun setColor(textView: TextView){
        items.forEach {
            it.setBackgroundResource(R.color.white) //default kategori color

        }
        textView.setBackgroundResource(R.color.ungu_200) //set saat disentuh
    }

    interface OnAdapterListener{
        fun onCLick(category: CategoryData)
    }

}