package com.example.advweek4.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.advweek4.R
import com.example.advweek4.model.Student
import com.example.advweek4.viewmodel.ListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentListFragment : Fragment() {

    private lateinit var viewModel:ListViewModel
    private val studentListAdapter = StudentListAdapter(arrayListOf())


    fun observeViewModel() {
        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
        })

        viewModel.studentLoadErrorLD.observe(viewLifecycleOwner, Observer {
            view?.let {v ->
                if(it == true) {
                    v.findViewById<TextView>(R.id.txtError).visibility = View.VISIBLE
                } else {
                    v.findViewById<TextView>(R.id.txtError).visibility = View.GONE
                }
            }

        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            view?.let {v->
                if(it == true) {
                    v.findViewById<RecyclerView>(R.id.recView).visibility = View.GONE
                    v.findViewById<ProgressBar>(R.id.progLoad).visibility = View.VISIBLE
                } else {
                    v.findViewById<RecyclerView>(R.id.recView).visibility = View.VISIBLE
                    v.findViewById<ProgressBar>(R.id.progLoad).visibility = View.GONE
                }
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        viewModel.refresh()
        val v = view.findViewById<RecyclerView>(R.id.recView)
        v.layoutManager = LinearLayoutManager(context)
        v.adapter = studentListAdapter
        observeViewModel()
        val srl = view.findViewById<SwipeRefreshLayout>(R.id.SwipeRefreshLayout)
        srl.setOnRefreshListener {
            v.visibility=View.GONE
            view.findViewById<TextView>(R.id.txtError).visibility = View.GONE
            view.findViewById<ProgressBar>(R.id.progLoad).visibility = View.VISIBLE
            viewModel.refresh()
            srl.isRefreshing=false
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }


}