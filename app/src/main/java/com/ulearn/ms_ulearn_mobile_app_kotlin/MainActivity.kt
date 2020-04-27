package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.addLogAdapter(AndroidLogAdapter())

        val apolloClient = ApolloClient
                                .builder()
                                .serverUrl("http://172.17.0.1:5000/api/graphql")
                                .build()

        btn_get_quiz.setOnClickListener {

            Logger.d(txt_id_quiz.text.toString());

            apolloClient.query(
                SearchQuery.builder().id(txt_id_quiz.text.toString()).build()
            ).enqueue(
                object: ApolloCall.Callback<SearchQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Logger.d(e.localizedMessage);
                    }

                    override fun onResponse(response: Response<SearchQuery.Data>) {
                        Logger.d(response.data().toString())
                    }
                }
            )
        }
    }
}
