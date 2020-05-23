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
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filePath = "authentication.txt"

        val file = File(getExternalFilesDir(filePath), filePath)

        if(file.isFile) {
            val tk = file.readText()
            Logger.d("Token -> ".plus(tk))

            if(tk!= null && tk.toString().length >= 36) {
                val intent = Intent(this, MainPanel::class.java)
                startActivity(intent);
            } else {
                val intent = Intent(this, Login::class.java)
                startActivity(intent);
            }
        } else {
            file.createNewFile()
            val intent = Intent(this, Login::class.java)
            startActivity(intent);
        }

    }

}
