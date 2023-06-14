package com.example.advweek4.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.advweek4.R
import com.example.advweek4.databinding.FragmentStudentDetailBinding
import com.example.advweek4.model.Student
import com.example.advweek4.util.loadImage
import com.example.advweek4.viewmodel.DetailViewModel
import com.example.advweek4.viewmodel.ListViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class StudentDetailFragment : Fragment(),ButtonUpdateClickListener,NotifClickListener {

    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding:FragmentStudentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_student_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = StudentDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.fetch(id)
        observeDetailViewModel()
    }

    fun observeDetailViewModel(){
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
            dataBinding.student=it
            dataBinding.notifListener=this
            dataBinding.updateListener=this
        })
    }

    override fun onUpdateClick(v: View,s: Student) {
        Toast.makeText(context,"STUDENT: "+s.name,Toast.LENGTH_SHORT).show()
    }

    override fun onClickNotif(v: View,s:Student) {
        Observable.timer(5,TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
            Toast.makeText(context,"Five seconds",Toast.LENGTH_SHORT).show()
            MainActivity.ShowNotification(s.name.toString(),"A new notification created",R.drawable.baseline_error_24)
        }
    }
}