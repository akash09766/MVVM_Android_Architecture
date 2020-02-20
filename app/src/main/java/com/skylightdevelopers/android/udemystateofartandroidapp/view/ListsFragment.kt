package com.skylightdevelopers.android.udemystateofartandroidapp.view


import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.skylightdevelopers.android.udemystateofartandroidapp.R
import com.skylightdevelopers.android.udemystateofartandroidapp.adapters.ListAdapter
import com.skylightdevelopers.android.udemystateofartandroidapp.model.AnimalData
import com.skylightdevelopers.android.udemystateofartandroidapp.viewModel.ListViewModel
import kotlinx.android.synthetic.main.fragment_lists.*

/**
 * A simple [Fragment] subclass.
 */

const val TAG = "ListsFragment"


class ListsFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val listAdpater = ListAdapter(arrayListOf())

    private val animalListObserver = Observer<List<AnimalData>> {

        it?.let {
            recyclerView.visibility = View.VISIBLE
            listAdpater.updateAnimalList(it)
        }
    }

    private val progressViewObserver = Observer<Boolean> {
        progressBar2.visibility = if (it) View.VISIBLE else View.GONE
        if (it) {
            errorTv.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }
    }
    private val errorViewObserver = Observer<Boolean> {
        errorTv.visibility = if (it) View.VISIBLE else View.GONE

        if (it) {
            progressBar2.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView.apply {
            layoutManager = GridLayoutManager(this.context,2)
            adapter = listAdpater
        }

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.animals.observe(viewLifecycleOwner, animalListObserver)
        viewModel.isError.observe(viewLifecycleOwner, errorViewObserver)
        viewModel.isLoading.observe(viewLifecycleOwner, progressViewObserver)

        viewModel.getData()

        refreshLayout.setOnRefreshListener {
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.getWindow()?.setStatusBarColor(ContextCompat.getColor(view.context,R.color.colorPrimaryDark))
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity?.getWindow()?.setStatusBarColor(ContextCompat.getColor(view.context,R.color.colorPrimaryDark))
        }

        val  activity = activity as MainActivity
        activity.supportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(view.context,R.color.colorPrimary)))
    }

}
