package com.example.advweek4.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.advweek4.model.Student
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val studentLD = MutableLiveData<Student>()
    private var queue: RequestQueue? =null
    fun fetch(id:String?) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://adv.jitusolution.com/student.php?id=$id"
        val req = StringRequest(Request.Method.GET,url,{
            val result = Gson().fromJson<Student>(it,Student::class.java)
            studentLD.value=result
        },{
            // error
        })
        queue?.add(req)
    }
}