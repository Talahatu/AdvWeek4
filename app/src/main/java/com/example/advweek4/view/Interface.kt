package com.example.advweek4.view

import android.view.View
import com.example.advweek4.model.Student

interface ButtonDetailClickListener{
    fun onButtonDetailClick(v: View)
}
interface ButtonUpdateClickListener{
    fun onUpdateClick(v:View,obj: Student)
}
interface NotifClickListener{
    fun onClickNotif(v:View,s:Student)
}