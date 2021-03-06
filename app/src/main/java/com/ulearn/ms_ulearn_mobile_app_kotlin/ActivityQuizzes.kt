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
import kotlinx.android.synthetic.main.activity_quizzes.*
import okhttp3.OkHttpClient
import java.io.File

class ActivityQuizzes : AppCompatActivity() {

    var hm : HashMap<String, Boolean?> = HashMap<String, Boolean?> ()

    var url_api : String = Configuration.url_api;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizzes)


        Logger.addLogAdapter(AndroidLogAdapter())

        val filePath = Configuration.file_auth;

        val file = File(getExternalFilesDir(filePath), filePath)

        val token = file.readText().toString()

        val apolloClient = setupApollo(token);


        answer_1.setOnClickListener {
            clearButton();
            var value = hm.get(answer_1.text.toString());

            if(value == true) {
                answer_1.setBackgroundColor(Color.GREEN);
            } else {
                answer_1.setBackgroundColor(Color.RED);
            }
            disable();
        }
        answer_2.setOnClickListener {
            clearButton();
            var value = hm.get(answer_2.text.toString());

            if(value == true) {
                answer_2.setBackgroundColor(Color.GREEN);
            } else {
                answer_2.setBackgroundColor(Color.RED);
            }
            disable();
        }

        answer_3.setOnClickListener {
            clearButton();
            var value = hm.get(answer_3.text.toString());

            if(value == true) {
                answer_3.setBackgroundColor(Color.GREEN);
            } else {
                answer_3.setBackgroundColor(Color.RED);
            }
            disable();
        }

        answer_4.setOnClickListener {
            clearButton();
            var value = hm.get(answer_4.text.toString());

            if(value == true) {
                answer_4.setBackgroundColor(Color.GREEN);
            } else {
                answer_4.setBackgroundColor(Color.RED);
            }
            disable();
        }

        var id = intent.getStringExtra("id");


        Logger.d(id);

        apolloClient.query(
            SearchQuery.builder().id(id).build()
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

    fun disable() {
        answer_1.isEnabled = false
        answer_1.isClickable = false
        answer_2.isEnabled = false
        answer_2.isClickable = false
        answer_3.isEnabled = false
        answer_3.isClickable = false
        answer_4.isEnabled = false
        answer_4.isClickable = false
    }

    private fun setupApollo(token: String): ApolloClient {
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method(),original.body())
                builder.addHeader("Authorization"
                    , "Bearer $token"
                )
                chain.proceed(builder.build())
            }
            .build()


        return ApolloClient.builder()
            .serverUrl(url_api)
            .okHttpClient(okHttp)
            .build()
    }

    /*override fun onDestroy() {
        val filePath = Configuration.file_auth;

        val file = File(getExternalFilesDir(filePath), filePath)
        file.writeText("");

        Logger.d("Destroid")

        super.onDestroy()
    }*/
}
