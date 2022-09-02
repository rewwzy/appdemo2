package com.cjqwimaoeup.kupew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_check.*
import java.util.*
import kotlin.concurrent.schedule

class CheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        supportActionBar?.hide() // hide the title bar
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        val link_tt = intent.extras!!.getString("str").toString()
        buttonquest.setOnClickListener(){
            var a = editAnswer.text.toString() == "4"

            if(a){
                val intent = Intent(this@CheckActivity, HomeActivity::class.java)
                intent.putExtra("link",link_tt)
                startActivity(intent)
                finish()
            }
        }
    }
}