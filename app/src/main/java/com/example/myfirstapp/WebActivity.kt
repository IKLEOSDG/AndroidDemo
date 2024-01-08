import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirstapp.R
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.lang.Exception
import kotlin.concurrent.thread

class WebActivity : AppCompatActivity() {

  private lateinit var textView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_web2)
    textView = findViewById(R.id.textView)
    sendRequestWithOkHttp()
  }

  private fun sendRequestWithOkHttp() {
    thread {
      try {
        val client = OkHttpClient()
        val request = Request.Builder()
        .url("https://www.baidu.com")
        .build()
        val response = client.newCall(request).execute()
        val responseData = response.body?.string()
        if (responseData!=null){
        parseJSONWithJSONObject(responseData)
        }
      }catch (e:Exception){
        e.printStackTrace()
      }
    }
  }
  private fun parseJSONWithJSONObject(jsonData:String){
    try {
      val jsonArray = JSONArray(jsonData)
      for (i in 0 until jsonArray.length()){
        val jsonObject = jsonArray.getJSONObject(i)
        val id = jsonObject.getString("id")
        val name = jsonObject.getString("name")
        val version = jsonObject.getString("version")
        Log.d("WebActivity","id is $id")
        Log.d("WebActivity","name is $name")
        Log.d("WebActivity","version is $version")
      }
    }catch (e:Exception){
      e.printStackTrace()
    }
  }
}


