package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quizzes.*

class ActivityQuizzes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizzes)


        Logger.addLogAdapter(AndroidLogAdapter())

        val apolloClient = ApolloClient
            .builder()
            .serverUrl("http://192.168.0.9:5000/api/graphql")
            .build()


        answer_1.setOnClickListener {answer_1.setBackgroundColor(Color.RED)}
        answer_2.setOnClickListener {answer_2.setBackgroundColor(Color.RED)}
        answer_3.setOnClickListener {answer_3.setBackgroundColor(Color.RED)}
        answer_4.setOnClickListener {answer_4.setBackgroundColor(Color.GREEN)}


        apolloClient.query(
            SearchQuery.builder().id("5ea8bae0638f782b5207f5b0").build()
        ).enqueue(
            object: ApolloCall.Callback<SearchQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Logger.d(e.localizedMessage);
                }

                override fun onResponse(response: Response<SearchQuery.Data>) {
                    Logger.d(response.data().toString())

                    statement.text = response.data()?.SearchQuestion?.statement.toString();

                    answer_1.text = response.data()?.SearchQuestion?.answers?.get(0)?.context.toString();
                    answer_2.text = response.data()?.SearchQuestion?.answers?.get(1)?.context.toString();
                    answer_3.text = response.data()?.SearchQuestion?.answers?.get(2)?.context.toString();
                    answer_4.text = response.data()?.SearchQuestion?.answers?.get(3)?.context.toString();
                }
            }
        )
    }
}
