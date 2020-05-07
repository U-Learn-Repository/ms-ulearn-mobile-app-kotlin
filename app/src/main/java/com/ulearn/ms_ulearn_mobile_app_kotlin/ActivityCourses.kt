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
import kotlinx.android.synthetic.main.activity_courses.*

class ActivityCourses : AppCompatActivity() {

    var hm : HashMap<String, Boolean?> = HashMap<String, Boolean?> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)


        Logger.addLogAdapter(AndroidLogAdapter())

        val apolloClient = ApolloClient
                .builder()
                .serverUrl("http://52.3.187.50:5000/api/graphql")
                .build()





        var id = intent.getStringExtra("id");

        apolloClient.query(
                SearchQuery2Query.builder().build()
        ).enqueue(
                object: ApolloCall.Callback<SearchQuery2Query.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Logger.d(e.localizedMessage);
                    }

                    override fun onResponse(response: Response<SearchQuery2Query.Data>) {
                        Logger.d(response.data().toString())

                        statement.text =response.data()?.listarCursos?.get(id.toInt())?.nombre.toString();

                        field_1.text ="Categoria " + response.data()?.listarCursos?.get(id.toInt())?.categoria.toString();

                        field_2.text = "Duracion "+ response.data()?.listarCursos?.get(id.toInt())?.duracion.toString() +" minutos";

                        field_3.text = "Id profesor "+response.data()?.listarCursos?.get(id.toInt())?.idProfesor.toString();


                    }
                }
        )
    }

    fun clearButton() {
        field_1.setBackgroundColor(Color.WHITE);
        field_2.setBackgroundColor(Color.WHITE);
        field_3.setBackgroundColor(Color.WHITE);
    }

    fun disable() {
        field_1.isEnabled = false
        field_1.isClickable = false
        field_2.isEnabled = false
        field_2.isClickable = false
        field_3.isEnabled = false
        field_3.isClickable = false
    }
}