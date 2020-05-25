package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_panel.*
import java.io.File

class MainPanel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_panel)

        btn_go_quiz_panel.setOnClickListener {
            goQuizzes();
        }
        btn_go_course_panel.setOnClickListener {
            goCourses();
        }
        btn_go_logout_panel.setOnClickListener {
            logOut();
        }
    }


    private fun goQuizzes() {
        val intent = Intent(this, ActivityListQuizzes::class.java)
        startActivity(intent);
    }

    private fun goCourses() {
        val intent = Intent(this, ActivityListCourses::class.java)
        startActivity(intent);
    }

    private fun logOut() {
        val filePath = Configuration.file_auth;

        val file = File(getExternalFilesDir(filePath), filePath)
        file.writeText("");

        val intent = Intent(this, Login::class.java)
        startActivity(intent);
    }

    /*override fun onDestroy() {
        val filePath = Configuration.file_auth;

        val file = File(getExternalFilesDir(filePath), filePath)
        file.writeText("");

        super.onDestroy()
    }*/

}
