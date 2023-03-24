package com.example.advweek4.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.advweek4.R
import com.example.advweek4.viewmodel.DetailViewModel
import com.example.advweek4.viewmodel.ListViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.fetch()
        observeDetailViewModel()
    }

    fun observeDetailViewModel(){
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
            view?.let {v->
                v.findViewById<TextInputEditText>(R.id.txtInputId).setText(it.id)
                v.findViewById<TextInputEditText>(R.id.txtInputName).setText(it.name)
                v.findViewById<TextInputEditText>(R.id.txtInputBOD).setText(it.dob)
                v.findViewById<TextInputEditText>(R.id.txtInputPhone).setText(it.phone)
                v.findViewById<ImageView>(R.id.imgProfile_detail).setImageURI(Uri.parse(it.photoUrl))
            }
        })
    }
}