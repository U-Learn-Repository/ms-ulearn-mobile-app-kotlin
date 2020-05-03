package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AdapterQuizzes (var ctx:Context, var src: Int, var items: ArrayList<ModelQuizRow>) : ArrayAdapter<ModelQuizRow>(ctx, src, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(ctx);
        val view:View = layoutInflater.inflate(src, null);
        val imageView:ImageView = view.findViewById(R.id.quiz_image);
        val titleView:TextView  = view.findViewById(R.id.row_title);

        val myItem: ModelQuizRow = items[position];

        imageView.setImageDrawable(ctx.resources.getDrawable(myItem.img));
        titleView.text = myItem.title;

        return view
    }

    fun update(newItems:  List<ModelQuizRow>) {

        for(itm in newItems) {
            this.items.add(itm);
        }

        notifyDataSetChanged()
    }
}