package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.AndroidLogAdapter
import java.io.File
import com.orhanobut.logger.Logger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.addLogAdapter(AndroidLogAdapter())

        val filePath = Configuration.file_auth;

        val file = File(getExternalFilesDir(filePath), filePath)

        if(file.isFile) {
            val tk = file.readText()

            Logger.d(tk.toString());

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
