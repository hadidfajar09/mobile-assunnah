package com.hadiid.assunnah.ui.module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hadiid.assunnah.databinding.AdapterModuleBinding

class ModuleAdapter(
    var details: ArrayList<DetailData>, //dari model
    var listener: OnAdapterListener? //aksi saat diklik
): RecyclerView.Adapter<ModuleAdapter.ViewHolder>() { //implement ke adapter



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(AdapterModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)) //menggunakan layout adapter

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detail = details[position] //atur model
        holder.binding.textTitle.text = detail.title
        if(detail.file_type != null && detail.file_type == "pdf"){
            holder.binding.imageDownload.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    listener?.onCLick(detail)
                }
            }
        }
        else{
            holder.binding.fabPlay.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    listener?.onCLick(detail)
                }
            }
        }




    }

    override fun getItemCount() = details.size

    class ViewHolder(val binding: AdapterModuleBinding):RecyclerView.ViewHolder(binding.root) //binding pada adapter

    fun addList(list: List<DetailData>){
        details.clear()
        details.addAll(list)
        notifyDataSetChanged()//refresh recycle
    }



    interface OnAdapterListener{
        fun onCLick(detail: DetailData)
    }

}