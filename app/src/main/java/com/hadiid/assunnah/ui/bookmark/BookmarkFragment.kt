package com.hadiid.assunnah.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hadiid.assunnah.R
import com.hadiid.assunnah.databinding.FragmentBookmarkBinding
import com.hadiid.assunnah.persistence.CourseEntity
import com.hadiid.assunnah.persistence.DatabaseClient
import com.hadiid.assunnah.ui.module.ModuleActivity


class BookmarkFragment : Fragment(), BookmarkView {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var presenter: BookmarkPresenter
    private lateinit var adapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        presenter = BookmarkPresenter(
            this,
            DatabaseClient.getService(requireContext()).courseDao()
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()

    }

    override fun setupListener() {
        adapter = BookmarkAdapter(arrayListOf(),object : BookmarkAdapter.OnAdapterListener{
            override fun onDetail(course: CourseEntity) {
                startActivity(
                    Intent(requireActivity(), ModuleActivity::class.java)
                    .putExtra("id",course.id))
            }

            override fun onRemove(course: CourseEntity) {
                presenter.remove(course)
            }

        })

        binding.listBookmark.adapter = adapter

        presenter.listBookmark.observe(viewLifecycleOwner,{
            Log.e("listBookmark",it.toString())
            adapter.addList(it)

            if(it.isEmpty()) Toast.makeText(requireContext(), "Tidak ada Dars yang diikuti", Toast.LENGTH_SHORT).show()
        })
    }

}