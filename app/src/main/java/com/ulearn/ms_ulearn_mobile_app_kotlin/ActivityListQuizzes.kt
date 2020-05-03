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
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_list_quizzes.*
import kotlinx.android.synthetic.main.activity_quizzes.*


class ActivityListQuizzes : AppCompatActivity() {

    var mp : ArrayList<Int> = ArrayList()

    var list = ArrayList<ModelQuizRow>();
    var dataID = ArrayList<String>();


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

        val apolloClient = ApolloClient
            .builder()
            .serverUrl("http://54.159.72.1:5000/api/graphql")
            .build()

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
}
