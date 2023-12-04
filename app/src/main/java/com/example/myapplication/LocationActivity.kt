package com.example.myapplication
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class LocateActivity : ComponentActivity() {
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        geocoder = Geocoder(this, Locale.getDefault())

        setContent {
            DisplayLocationData()
        }

        fetchLocationData()
    }

    private fun fetchLocationData() {
        lifecycleScope.launch {
            val locationData = withContext(Dispatchers.IO) {
                val address = geocoder.getFromLocationName("your_location_name", 1)?.firstOrNull()
                val latitude = address?.latitude
                val longitude = address?.longitude
                val cityName = address?.locality
                val temperature = getTemperature(latitude, longitude)
                LocationData(cityName, latitude, longitude, temperature)
            }

            updateUI(locationData)
        }
    }

    private suspend fun getTemperature(latitude: Double?, longitude: Double?): Double {
        // Implement your code to get the temperature using latitude and longitude
        // Return the temperature value
        return 25.0 // Placeholder value, replace with actual temperature retrieval logic
    }

    private fun updateUI(locationData: LocationData) {
        setContent {
            DisplayLocationData(locationData)
        }
    }

    @Composable
    fun DisplayLocationData(locationData: LocationData? = null) {
        Column {
            if (locationData != null) {
                Text(text = "Sensors PlayGround" ,
                     textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    lineHeight = 55.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)

                        .fillMaxWidth(),)
                Text(text = "Location:" ,  lineHeight = 35.sp,)
                Text(text = "City: ${locationData.cityName}")
                Text(text = "State: ${locationData.cityName}")
                Text(text = "Temperature: ${locationData.temperature}")
                Text(text = "Air Pressure: 998.5")
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        //点击回调

                        startActivity(Intent(this@LocateActivity,MainActivity::class.java))
                    }) {
                        //单单一个Button是没有内容的，这里需要在Button里添加一个Text
                        Text(text = "GESTURE PLAYGROUND")
                    }
                }
            } else {
                Text(text = "Loading location data...")
            }
        }
    }

    data class LocationData(
        val cityName: String?,
        val latitude: Double?,
        val longitude: Double?,
        val temperature: Double?
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LocateActivity().DisplayLocationData(
        LocateActivity.LocationData(
            "City Name",
            37.7749,
            -122.4194,
            25.0
        )
    )
}
