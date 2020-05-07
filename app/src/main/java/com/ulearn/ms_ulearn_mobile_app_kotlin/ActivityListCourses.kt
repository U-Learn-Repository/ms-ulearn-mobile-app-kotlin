package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class ActivityListCourses : AppCompatActivity() {

    var mp : ArrayList<Int> = ArrayList()

    var list = ArrayList<ModelCoursesRow>();
    var dataID = ArrayList<String>();


    override fun onCreate(savedInstanceState: Bundle?) {
        var self = this;

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_courses)

        mp.add(R.drawable.avatar)
        mp.add(R.drawable.boy)
        mp.add(R.drawable.education)
        mp.add(R.drawable.man)
        mp.add(R.drawable.people)


        Logger.addLogAdapter(AndroidLogAdapter())

        val apolloClient = ApolloClient
            .builder()
            .serverUrl("http://192.168.0.6:5000/api/graphql")
            .build()

        var listView = findViewById<ListView>(R.id.list_courses)

        listView.setOnItemClickListener{parent: AdapterView<*>, view: View, position: Int, id: Long ->
            Toast.makeText(this@ActivityListCourses, "you has clicked" , Toast.LENGTH_LONG).show()
            val intent = Intent(this@ActivityListCourses, ActivityCourses::class.java)
            intent.putExtra("id", dataID.get(position))
          //  System.out.println(dataID);
            startActivity(intent);
        }

        apolloClient.query(
                SearchQuery2Query.builder().build()
        ).enqueue(
            object: ApolloCall.Callback<SearchQuery2Query.Data>() {
                override fun onFailure(e: ApolloException) {
                    Logger.d(e.localizedMessage);
                }

                override fun onResponse(response: Response<SearchQuery2Query.Data>) {

                    this@ActivityListCourses.runOnUiThread(java.lang.Runnable {
                        var index = 0;
                        var size = response.data()?.listarCursos?.size!!;
                        System.out.println(response.data()?.listarCursos?.get(0)?.nombre)
                        for (item in response.data()?.listarCursos!!.iterator() ) {
                            list.add(ModelCoursesRow(item.nombre.toString()+" | "+ item.categoria.toString() +" | " +item.duracion.toString() +" minutes", mp.get( (index % mp.size) )))
                            dataID.add(index.toString());
                            index++;
                        }
//                        for(item in response.data()?.SearchQuery!!.iterator()) {
//                            list.add(ModelQuizRow(item.statement, mp.get( (index % mp.size) )))
//                            dataID.add(item.id)
//                            index++;
//                        }

                        listView.adapter = AdapterCourses(self, R.layout.row_course, list)
                    })

                }
            }
        )
    }
}