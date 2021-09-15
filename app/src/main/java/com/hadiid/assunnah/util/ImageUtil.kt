package com.hadiid.assunnah.util

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hadiid.assunnah.R

fun loadImage(imageView: ImageView, urlString: String){
    Glide.with(imageView) //tampil gmbr
        .load(urlString)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(imageView)
}

fun loadAvatar(imageView: ImageView, urlString: String){
    Glide.with(imageView) //tampil gmbr
        .load(urlString)
        .circleCrop()
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(imageView)
}

fun loadUri(imageView: ImageView, uri: Uri){
    Glide.with(imageView) //tampil gmbr
        .load(uri)
        .circleCrop()
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(imageView)
}