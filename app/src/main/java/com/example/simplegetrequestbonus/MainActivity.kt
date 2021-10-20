package com.example.simplegetrequestbonus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    var text=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView=findViewById(R.id.text)

        CallApi()

    }

    private fun CallApi() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val api = URL("https://dojo-recipes.herokuapp.com/people/")
                    .readText(Charsets.UTF_8)
                if (api.isNotEmpty()){
                    getData(api)
                }else {
                    Toast.makeText(this@MainActivity,"NULL VALUE",Toast.LENGTH_LONG).show()
                }
            }catch (e: Exception){
                println("Error $e")
            }
        }
    }

    private suspend fun getData(api: String) {
        withContext(Dispatchers.Main){
            val names =JSONArray(api)
            for (i in 0 until names.length()){
                text+=names.getJSONObject(i).getString("name")+"\n"
                textView.text=text
            }
        }
    }
}