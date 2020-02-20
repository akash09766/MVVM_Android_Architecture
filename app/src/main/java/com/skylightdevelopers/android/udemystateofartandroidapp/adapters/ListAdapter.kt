package com.skylightdevelopers.android.udemystateofartandroidapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.skylightdevelopers.android.udemystateofartandroidapp.R
import com.skylightdevelopers.android.udemystateofartandroidapp.databinding.ListItemBinding
import com.skylightdevelopers.android.udemystateofartandroidapp.listeners.AnimalClickListener
import com.skylightdevelopers.android.udemystateofartandroidapp.model.AnimalData
import com.skylightdevelopers.android.udemystateofartandroidapp.view.ListsFragmentDirections

class ListAdapter(private val animalList: ArrayList<AnimalData>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>(), AnimalClickListener {


    fun updateAnimalList(newAnimalList: List<AnimalData>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: ListItemBinding =
            DataBindingUtil.inflate<ListItemBinding>(inflater, R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.view.animal = animalList[position]
        holder.view.listener = this
        holder.view.pos = position
        /*  holder.view.textView.text = animalList[position].name

  //        Glide.with(holder.view.context).load(animalList[position].image).into(holder.view.imageView)

          holder.view.imageView.loadImage(
              animalList[position].image,
              getProgressDrawable(holder.view.context)
          )

          holder.view.setOnClickListener{
              val action = ListsFragmentDirections.actionListsFragmentToDetailssFragment(animalList[position])
              Navigation.findNavController(holder.view).navigate(action)

          }*/
    }

    class ListViewHolder(var view: ListItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onClick(view: View) {

        val item = view.tag as Int

        val action = ListsFragmentDirections.actionListsFragmentToDetailssFragment(animalList[item])
        Navigation.findNavController(view).navigate(action)
    }

}