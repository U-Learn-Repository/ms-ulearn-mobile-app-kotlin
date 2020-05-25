package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.get
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.request.RequestHeaders
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_list_quizzes.*
import kotlinx.android.synthetic.main.activity_quizzes.*
import okhttp3.OkHttpClient
import java.io.File



class ActivityListQuizzes : AppCompatActivity() {

    var mp : ArrayList<Int> = ArrayList()

    var list = ArrayList<ModelQuizRow>();
    var dataID = ArrayList<String>();

    var url_api : String = Configuration.url_api;


    override fun onCreate(savedInstanceState: Bundle?) {
        var self = this;

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_quizzes)

        mp.add(R.drawable.avatar)
        mp.add(R.drawable.boy)
        mp.add(R.drawable.education)
        mp.add(R.drawable.man)
        mp.add(R.drawable.people)


        Logger.addLogAdapter(AndroidLogAdapter())

        val filePath = Configuration.file_auth;

        val file = File(getExternalFilesDir(filePath), filePath)

        val token = file.readText().toString()

        val apolloClient = setupApollo(token);

        var listView = findViewById<ListView>(R.id.list_quizzes)

        listView.setOnItemClickListener{parent: AdapterView<*>, view: View, position: Int, id: Long ->
            Toast.makeText(this@ActivityListQuizzes, "you has clicked" , Toast.LENGTH_LONG).show()
            val intent = Intent(this@ActivityListQuizzes, ActivityQuizzes::class.java)
            intent.putExtra("id", dataID.get(position))
            startActivity(intent);
        }




        apolloClient.query(
            SearchQuestionsQuery.builder().build()
        ).enqueue(
            object: ApolloCall.Callback<SearchQuestionsQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Logger.d(e.localizedMessage);
                }

                override fun onResponse(response: Response<SearchQuestionsQuery.Data>) {
                    if(response.data()?.SearchQuestions == null) {
                        return;
                    }

                    this@ActivityListQuizzes.runOnUiThread(java.lang.Runnable {
                        var index = 0;

                        for(item in response.data()?.SearchQuestions!!.iterator()) {
                            list.add(ModelQuizRow(item.statement, mp.get( (index % mp.size) )))
                            dataID.add(item.id)
                            index++;
                        }

                        listView.adapter = AdapterQuizzes(self, R.layout.row_quiz, list)
                    })

                }
            }
        )
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

    override fun onDestroy() {
        val filePath = Configuration.file_auth;

        val file = File(getExternalFilesDir(filePath), filePath)
        file.writeText("");

        super.onDestroy()
    }
}
