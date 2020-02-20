package com.skylightdevelopers.android.udemystateofartandroidapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.skylightdevelopers.android.udemystateofartandroidapp.R


fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable) {
    val option = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)
}


@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}

private fun View.loadFromBitmap(url: String?) {

    val view = this

    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {

            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {


                Palette.from(resource)
                    .generate() { palette ->

                        val intColor = (palette?.darkMutedSwatch?.rgb)//darkVibrantSwatch
                            ?: ((palette?.darkVibrantSwatch?.rgb)//darkMutedSwatch
                                ?: ((palette?.lightVibrantSwatch?.rgb)
                                    ?: ((palette?.lightMutedSwatch?.rgb)
                                        ?: ((palette?.dominantSwatch?.rgb) ?: 0))))


                        view.setBackgroundColor(intColor)
                    }
            }

        })
}


@BindingAdapter("android:loadFromBitMap")
fun loadImageFromBitmap(view: View, url: String?) {
    view.loadFromBitmap(url)
}

fun String.getPaletteColorFromUrl(context: Context, url: String?): Int {

    var initcolor: Int = 3

    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {

            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                Palette.from(resource)
                    .generate() { palette ->

                        val intColor = (palette?.darkMutedSwatch?.rgb)//darkVibrantSwatch
                            ?: ((palette?.darkVibrantSwatch?.rgb)//darkMutedSwatch
                                ?: ((palette?.lightVibrantSwatch?.rgb)
                                    ?: ((palette?.lightMutedSwatch?.rgb)
                                        ?: ((palette?.dominantSwatch?.rgb) ?: 0))))
                        initcolor = intColor

                    }
            }

        })

    return initcolor
}