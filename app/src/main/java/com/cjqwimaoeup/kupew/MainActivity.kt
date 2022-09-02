package com.cjqwimaoeup.kupew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        supportActionBar?.hide() // hide the title bar
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiInterface.create().getActive().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.body() != null) {
                    val active = response.body()?.string().toString().split(",")

                    if (active[0] == "active") {
                        val intent = Intent(this@MainActivity, CheckActivity::class.java)
                        Timer().schedule(1000) {
                            intent.putExtra("str",active[1])
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable?) {
            }
        });
    }
}