package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_go_course.setOnClickListener {


            goQuizzes();
        }
    }

    private fun goQuizzes() {
        // val intent = Intent(this, ActivityQuizzes::class.java);
        val intent = Intent(this, ActivityListCourses::class.java)
        startActivity(intent);
    }
}