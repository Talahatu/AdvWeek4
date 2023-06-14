package com.example.advweek4.view


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.advweek4.R
import com.example.advweek4.util.createNotificationChannel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object{
        var instance:MainActivity ?=null

        fun ShowNotification(title:String,content:String,icon:Int){
            val channelId = "${MainActivity.instance?.packageName}-${MainActivity.instance?.getString(R.string.app_name)}"

            val notificationBuilder = NotificationCompat.Builder(MainActivity.instance!!.applicationContext,channelId).apply {
                setSmallIcon(icon)
                setContentTitle(title)
                setContentText(content)
                setStyle(NotificationCompat.BigTextStyle())
                priority= NotificationCompat.PRIORITY_DEFAULT
                setAutoCancel(true)
            }

            val notificationManager = NotificationManagerCompat.from(MainActivity.instance!!.applicationContext.applicationContext!!)
            if (ActivityCompat.checkSelfPermission(
                    MainActivity.instance!!.applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notificationManager.notify(1001,notificationBuilder.build())
        }
    }
    init {
        instance=this
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val obs = Observable.just("A Stream Of Data","hell","word")
        val observer = object: Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d("Message","Begin Subscribe")
            }

            override fun onError(e: Throwable) {
                Log.d("Message","Error:${e.message.toString()}")
            }

            override fun onComplete() {
                Log.d("Message","Complete")
            }

            override fun onNext(t: String) {
                Log.d("Message","Data: $t")
            }

        }
        obs.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer)

        createNotificationChannel(this,NotificationManagerCompat.IMPORTANCE_DEFAULT,false,getString(R.string.app_name),"App Notification Channel.")


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            Observable.timer(5,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    Log.d("Messages","Five Second")
                    ShowNotification("Dummy","A New Notification Created",R.drawable.baseline_error_24)
                }
        }
    }
}