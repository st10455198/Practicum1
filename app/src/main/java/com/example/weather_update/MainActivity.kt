@ -1,13 +1,106 @@
package com.example.weather_update

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.text.util.LocalePreferences.FirstDayOfWeek.Days
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    val Days = "Current days of the week"
    val API: String = "01d73c28c2063e2794b7050b5cd88bcc"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var 
        weatherTask().execute()

    }
}

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.Loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errortext).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            TODO("Not yet implemented")
            var response: String?
            try {
                response =
                    URL("https://api.openweathermap.org/data/2.5/weather?q=${Days}&units=metric&appid=$API")
                        .readText(Charsets.UTF_8)
            } catch (e: Exception) {
                response = null
            }
            return response

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try{
                val jsonObj=JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind=jsonObj.getJSONObject("wind")
                val weather=jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt:Long=jsonObj.getLong("dt")
                val updatedAtText="updated at : "+SimpleDateFormat("dd/mm/yyy  hh:mm  a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"Celcius"
                val tempMin="Min Temp: "+main.getString("temp_min")+"Celcius"
                val tempMax="Max Temp: "+main.getString("temp_max")+"Celcius"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val sunrise:Long=sys.getLong("sunrise")
                val sunset:Long=sys.getLong("sunset")
                val windspeed=wind.getString("speed")
                val weatherDescription=weather.getString("description")
                val address=jsonObj.getString("name")+","+sys.getString("Days")


                findViewById<TextView>(R.id.address).text= address
                findViewById<TextView>(R.id.updated_at).text= updatedAtText
                findViewById<TextView>(R.id.status).text= weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text= temp
                findViewById<TextView>(R.id.temp_min).text= tempMin
                findViewById<TextView>(R.id.temp_max).text= tempMax
                findViewById<TextView>(R.id.Sunrise).text= SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                findViewById<TextView>(R.id.Sunset).text= SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                findViewById<TextView>(R.id.wind).text= windspeed
                findViewById<TextView>(R.id.pressure).text= pressure
                findViewById<TextView>(R.id.humidity).text= humidity

                findViewById<ProgressBar>(R.id.Loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            }
            catch (e:Exception)
            {
                findViewById<ProgressBar>(R.id.Loader).visibility = View.GONE
                findViewById<TextView>(R.id.errortext).visibility = View.VISIBLE
            }
            }



            }

        }



