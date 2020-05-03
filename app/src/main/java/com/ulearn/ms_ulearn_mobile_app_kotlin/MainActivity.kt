package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.internal.Internal

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_go_quiz.setOnClickListener {
            goQuizzes();
        }
    }

    private fun goQuizzes() {
        // val intent = Intent(this, ActivityQuizzes::class.java);
        val intent = Intent(this, ActivityListQuizzes::class.java)
        startActivity(intent);
    }
}
