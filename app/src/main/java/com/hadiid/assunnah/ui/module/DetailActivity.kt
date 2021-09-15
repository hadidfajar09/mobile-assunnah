package com.hadiid.assunnah.ui.module

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.hadiid.assunnah.R
import com.hadiid.assunnah.databinding.ActivityDetailBinding
import com.hadiid.assunnah.network.ApiClient
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class DetailActivity : AppCompatActivity(), DetailView {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var presenter: DetailPresenter
    private val id by lazy { intent.getIntExtra("id",0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = DetailPresenter(this,
        ApiClient.getService()

        )
    }

    override fun onStart() {
        super.onStart()
        if(id != 0) presenter.fetchDetail(id)
    }

    override fun setupListener() {
        binding.swipe.setOnRefreshListener {
            if(id != 0) presenter.fetchDetail(id)
        }
    }

    override fun detailLoading(loading: Boolean) {
        binding.swipe.isRefreshing = loading
    }

    override fun detailResponse(response: DetailResponse) {
        val detail = response.data
        binding.textTitle.text = detail.title
        binding.textDescription.text = HtmlCompat.fromHtml(detail.description,0)
        when(detail.module_type){
            "file" -> {
                when(detail.file_type){
                    "pdf" -> {
                        binding.btnDownload.apply {
                            visibility = View.VISIBLE
                            setOnClickListener {
                                val openUrl = Intent(Intent.ACTION_VIEW)
                                openUrl.data = Uri.parse(detail.document)
                                startActivity(openUrl)
                            }
                        }
                    }

                    "mp4" -> {
                        binding.videoFile.apply {
                            visibility = View.VISIBLE
                            setUp(detail.document,detail.title)
//                            posterImageView.setImageResource(R.drawable.ic_book)
                        }
                    }
                }
            }

            "youtube" -> {
                    lifecycle.addObserver(binding.videoYoutube)
                    binding.videoYoutube.apply {
                        visibility = View.VISIBLE
                        addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
                            //ketika player sdh ready
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                youTubePlayer.cueVideo(detail.youtube,0F)
//                                youTubePlayer.loadVideo(detail.youtube,0F)
                            }
                        })
                    }
            }
        }
    }

    override fun detailError(messege: String) {
        Toast.makeText(applicationContext, messege, Toast.LENGTH_SHORT).show()
        binding.swipe.isRefreshing = false
        
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoFile.apply {
            if(visibility == View.VISIBLE) setUp("","")
        }
    }


}