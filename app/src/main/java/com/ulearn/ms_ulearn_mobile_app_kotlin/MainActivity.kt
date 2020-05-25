package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filePath = Configuration.file_auth;

        val file = File(getExternalFilesDir(filePath), filePath)

        if(file.isFile) {
            val tk = file.readText()

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

    override fun onDestroy() {
        val filePath = Configuration.file_auth;

        val file = File(getExternalFilesDir(filePath), filePath)
        file.writeText("");

        super.onDestroy()
    }
}
