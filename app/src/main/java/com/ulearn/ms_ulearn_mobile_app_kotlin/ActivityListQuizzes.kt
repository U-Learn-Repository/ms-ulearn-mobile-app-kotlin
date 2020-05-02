package com.ulearn.ms_ulearn_mobile_app_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list_quizzes.*



class ActivityListQuizzes : AppCompatActivity() {

    var mp : ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_quizzes)

        mp.add(R.drawable.avatar);
        mp.add(R.drawable.boy);
        mp.add(R.drawable.education);
        mp.add(R.drawable.man);
        mp.add(R.drawable.people);

        var listView = findViewById<ListView>(R.id.list_quizzes);

        var list = mutableListOf<ModelQuizRow>();

        list.add(ModelQuizRow("¿Qué expresa esta formula? e = mc²", mp.get(0)))

        list.add(ModelQuizRow("¿Qué expresa esta formula? e = mc²", mp.get(1)))

        list.add(ModelQuizRow("¿Qué expresa esta formula? e = mc²", mp.get(2)))

        list.add(ModelQuizRow("¿Qué expresa esta formula? e = mc²", mp.get(3)))

        listView.adapter = AdapterQuizzes(this, R.layout.row_quiz, list)

        listView.setOnItemClickListener{parent: AdapterView<*>, view: View, position: Int, id: Long ->
            Toast.makeText(this@ActivityListQuizzes, "you has clicked", Toast.LENGTH_LONG).show()
        }
    }
}
