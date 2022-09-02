package com.cjqwimaoeup.kupew
import android.bluetooth.BluetoothClass
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.ResponseBody
import org.json.JSONStringer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.lang.StringBuilder
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.concurrent.schedule

class HomeActivity : AppCompatActivity() {
    private val STORETEXT = "storetext.txt"
    var link_dk:String =""
    var link_dn:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        supportActionBar?.hide() // hide the title bar
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ApiInterface.create().getActive().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.body() != null) {
                    val respString = response.body()?.string().toString().split(",")
                    link_dn = respString[1].toString()
                    link_dk = respString[2].toString()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable?) {
            }
        });
        btn_tt.setOnClickListener() {
            setUp()
        }
    }

    fun setUp() {
        btn_tt.isClickable = false
        showFormDialog()
        btn_tt.isClickable = true
    }
    private fun showFormDialog() {
        val dialog = MaterialDialog(this).noAutoDismiss().customView(R.layout.form_dialog)
        dialog.cornerRadius(10.0f)
        val pattern: Pattern = Pattern.compile("^\\d{10}$")
        readFileInEditor(dialog.getCustomView())

        dialog.findViewById<TextView>(R.id.login_button).setOnClickListener {
            //check and post data here
            val phoneNum: String =
                dialog.getCustomView().findViewById<EditText>(R.id.editTextPhone).text.toString()
            val matcher: Matcher = pattern.matcher(phoneNum)
            if (matcher.matches()) {
                saveClicked(phoneNum)
                sendPost(phoneNum, "login")
                dialog.dismiss()
                //navigation to web view
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                    val uri: Uri = Uri.parse(link_dn)
                    val intent = Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent)
            } else {
                Toast.makeText(this, "Số điện thoại không đúng!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<TextView>(R.id.reg_button).setOnClickListener {
            //check and post data here
            val phoneNum: String =
                dialog.getCustomView().findViewById<EditText>(R.id.editTextPhone).text.toString()
            val matcher: Matcher = pattern.matcher(phoneNum)
            if (matcher.matches()) {
                saveClicked(phoneNum)
                sendPost(phoneNum, "reg")
                dialog.dismiss()
                //navigation to web view
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                    val uri: Uri = Uri.parse(link_dk)
                    val intent = Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent)
            } else {
                Toast.makeText(this, "Số điện thoại không đúng!", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    private fun sendPost(phoneNum: String, type: String) {
        val action: String = "addItem"
        val apiInterface = ApiInterface.create().savePost(phoneNum, type, action);
        apiInterface.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
            }
        });
    }
    fun saveClicked(txtEditor:String) {
        try {
            val out = OutputStreamWriter(openFileOutput(STORETEXT, 0))
            out.write(txtEditor.toString())
            out.close()
        } catch (t: Throwable) {
//            Toast
//                .makeText(this, "Exception: $t", Toast.LENGTH_LONG)
//                .show()
        }
    }
    fun readFileInEditor(v: View) {
        try {
            val `in`: InputStream? = openFileInput(STORETEXT)
            if (`in` != null) {
                val tmp = InputStreamReader(`in`)
                val reader = BufferedReader(tmp)
                var str: String = reader.readLine()
                `in`.close()
                v.findViewById<EditText>(R.id.editTextPhone).setText(str)
            }
        } catch (e: FileNotFoundException) {

// that's OK, we probably haven't created it yet
        } catch (t: Throwable) {
//            Toast
//                .makeText(this, "Exception: $t", Toast.LENGTH_LONG)
//                .show()
        }
    }
}