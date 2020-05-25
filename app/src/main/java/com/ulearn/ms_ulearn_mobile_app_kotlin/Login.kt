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
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File

class Login : AppCompatActivity() {

    val self : Login = this;

    var url_api : String = Configuration.url_api;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Logger.addLogAdapter(AndroidLogAdapter())

        val apolloClient = ApolloClient
            .builder()
            .serverUrl(url_api)
            .build()

        submit.setOnClickListener {

            apolloClient.mutate(
                AuthenticationMutation
                    .builder()
                    .username(username.text.toString())
                    .password(password.text.toString())
                    .build()
            ).enqueue(
                object: ApolloCall.Callback<AuthenticationMutation.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Logger.d("Usuario o Contraseña Incorrecta o revise su conexión a internet")
                    }

                    override fun onResponse(response: Response<AuthenticationMutation.Data>) {

                        if(response?.data()?.login?.access_token == null) {
                            Logger.d("Usuario o Contraseña Incorrecta")
                            // Toast.makeText(applicationContext, "Usuario o Contraseña Incorrecta" , Toast.LENGTH_LONG).show()
                            return;
                        }

                         Logger.d("Token: ".plus(response?.data()?.login?.access_token?.toString()))


                        this@Login.runOnUiThread(java.lang.Runnable {
                            val filePath = Configuration.file_auth;

                            val file = File(getExternalFilesDir(filePath), filePath)
                            file.writeText("".plus(response?.data()?.login?.access_token?.toString()))

                            val intent = Intent(self, MainPanel::class.java)
                            startActivity(intent);
                        })
                    }
                }
            )
        }

    }
}
