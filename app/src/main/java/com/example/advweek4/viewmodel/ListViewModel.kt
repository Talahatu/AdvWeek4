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

class ListViewModel(application: Application):AndroidViewModel(application) {
    val TAG = "volleyTag"
    private var queue: RequestQueue? =null
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val studentLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    fun refresh() {
        studentLoadErrorLD.value = false
        loadingLD.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://adv.jitusolution.com/student.php"
        val req = StringRequest(Request.Method.GET,url,{
            loadingLD.value=false
            val sType = object: TypeToken<ArrayList<Student>>(){}.type
            val result = Gson().fromJson<ArrayList<Student>>(it,sType)
            studentsLD.value=result
        },{
            studentLoadErrorLD.value=false;
            loadingLD.value=false;
        })

        req.tag=TAG
        queue?.add(req)

    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}