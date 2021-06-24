package com.gg.chatjin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_option.*

class OptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        list()

    }
    fun list(){
        val list_menu = arrayOf("비밀번호 변경","차단목록","회원탈퇴","로그아웃")
        val adapter = ArrayAdapter(this,R.layout.list_item,list_menu)
        option_view.adapter=adapter

        option_view.onItemClickListener=object :AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val strText = parent?.getItemAtPosition(position) as String
            }

        }


    }
}