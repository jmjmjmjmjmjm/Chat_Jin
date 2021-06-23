package com.gg.chatjin

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.gg.chatjin.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val adapter = ArrayAdapter.createFromResource(this,R.array.emailList,R.layout.support_simple_spinner_dropdown_item)
        binding.spinner.adapter=adapter
        spinner.setSelection(1)
        spinner.onItemSelectedListener=object :
        AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                email.setText("이메일을 선택하세요! ->")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    1->{
                        email.setText("naver.com")
                    }
                    2->{
                        email.setText("nate.com")
                    }
                    3->{
                        email.setText("gmail.com")
                    }
                }
            }

        }
    }
}
