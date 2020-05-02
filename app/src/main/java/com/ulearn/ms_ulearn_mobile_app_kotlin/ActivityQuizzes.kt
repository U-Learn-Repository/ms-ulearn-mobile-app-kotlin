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

    var hm : HashMap<String, Boolean?> = HashMap<String, Boolean?> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizzes)


        Logger.addLogAdapter(AndroidLogAdapter())

        val apolloClient = ApolloClient
            .builder()
            .serverUrl("http://127.0.0.1:5000/api/graphql")
            .build()


        answer_1.setOnClickListener {
            clearButton();
            var value = hm.get(answer_1.text.toString());

            if(value == true) {
                answer_1.setBackgroundColor(Color.GREEN);
            } else {
                answer_1.setBackgroundColor(Color.RED);
            }

        }
        answer_2.setOnClickListener {
            clearButton();
            var value = hm.get(answer_2.text.toString());

            if(value == true) {
                answer_2.setBackgroundColor(Color.GREEN);
            } else {
                answer_2.setBackgroundColor(Color.RED);
            }
        }

        answer_3.setOnClickListener {
            clearButton();
            var value = hm.get(answer_3.text.toString());

            if(value == true) {
                answer_3.setBackgroundColor(Color.GREEN);
            } else {
                answer_3.setBackgroundColor(Color.RED);
            }
        }

        answer_4.setOnClickListener {
            clearButton();
            var value = hm.get(answer_4.text.toString());

            if(value == true) {
                answer_4.setBackgroundColor(Color.GREEN);
            } else {
                answer_4.setBackgroundColor(Color.RED);
            }
        }


        apolloClient.query(
            SearchQuery.builder().id("5eab0281fe7b80a0896249c8").build()
        ).enqueue(
            object: ApolloCall.Callback<SearchQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Logger.d(e.localizedMessage);
                }

                override fun onResponse(response: Response<SearchQuery.Data>) {
                    Logger.d(response.data().toString())

                    statement.text = response.data()?.SearchQuestion?.statement.toString();

                    answer_1.text = response.data()?.SearchQuestion?.answers?.get(0)?.context.toString();
                    hm.put(answer_1.text.toString(), response.data()?.SearchQuestion?.answers?.get(0)?.is_correct);

                    answer_2.text = response.data()?.SearchQuestion?.answers?.get(1)?.context.toString();
                    hm.put(answer_2.text.toString(), response.data()?.SearchQuestion?.answers?.get(1)?.is_correct);

                    answer_3.text = response.data()?.SearchQuestion?.answers?.get(2)?.context.toString();
                    hm.put(answer_3.text.toString(), response.data()?.SearchQuestion?.answers?.get(2)?.is_correct);

                    answer_4.text = response.data()?.SearchQuestion?.answers?.get(3)?.context.toString();
                    hm.put(answer_4.text.toString(), response.data()?.SearchQuestion?.answers?.get(3)?.is_correct);
                }
            }
        )
    }

    fun clearButton() {
        answer_1.setBackgroundColor(Color.WHITE);
        answer_2.setBackgroundColor(Color.WHITE);
        answer_3.setBackgroundColor(Color.WHITE);
        answer_4.setBackgroundColor(Color.WHITE);
    }
}
