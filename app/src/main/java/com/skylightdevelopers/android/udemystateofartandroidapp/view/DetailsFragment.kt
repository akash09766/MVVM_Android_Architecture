package com.skylightdevelopers.android.udemystateofartandroidapp.view


import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.skylightdevelopers.android.udemystateofartandroidapp.R
import com.skylightdevelopers.android.udemystateofartandroidapp.databinding.FragmentDetailsBinding
import com.skylightdevelopers.android.udemystateofartandroidapp.model.AnimalData

/**
 * A simple [Fragment] subclass.
 */


class DetailsFragment : Fragment() {

    private var animalData: AnimalData? = null
    lateinit var dataBinding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animalData = DetailsFragmentArgs.fromBundle(it).animal
        }

        val activity = activity as MainActivity
        activity.supportActionBar?.title = animalData?.name

        /*animalImage.loadImage(animalData?.image, getProgressDrawable(view.context))
        animalName.text = animalData?.name
        animalLocation.text = animalData?.location
        animalLifespan.text = animalData?.lifespan
        animalData?.diet?.let {
            animalDiet.text = it
        }*/
        animalData?.image?.let {
            setBackgroundColor(it)
        }

        dataBinding.animal = animalData
//        val color = String().getPaletteColorFromUrl(context!!.applicationContext,animalData?.image)
//        Log.d(TAG,"COLOR : $color")
//        dataBinding.paletteColor = color

    }

    private fun setBackgroundColor(url: String) {

        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                    Palette.from(resource)
                        .generate() { palette ->

                            val intColor = (palette?.darkMutedSwatch?.rgb)//darkVibrantSwatch
                                ?: ((palette?.darkVibrantSwatch?.rgb)) ?: 0//darkMutedSwatch

                            val secondayIntColor =
                                (palette?.lightMutedSwatch?.rgb)//darkVibrantSwatch
                                    ?: ((palette?.lightVibrantSwatch?.rgb)) ?: 0//darkMutedSwatch

                            Log.d(TAG, "intColor : $intColor")

//                            detailsParentView.setBackgroundColor(intColor)
                            dataBinding.paletteColor = intColor
                            dataBinding.secondaryPaletteColor = secondayIntColor

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                activity?.getWindow()?.setStatusBarColor(intColor)
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                activity?.getWindow()?.setStatusBarColor(intColor)
                            }

                            val activity = activity as MainActivity
                            activity.supportActionBar!!.setBackgroundDrawable(ColorDrawable(intColor))

                        }
                }

            })

    }
}
