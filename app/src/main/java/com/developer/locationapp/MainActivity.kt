package com.developer.locationapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.developer.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LocationApp()
                }
            }
        }
    }
}

@Composable
fun LocationApp(){
    val context = LocalContext.current
    val locationUtil = LocationUtil(context = context)
    LocationDisplay(locationUtils = locationUtil, context = context)
}

@Composable
fun LocationDisplay(locationUtils: LocationUtil, context: Context) {

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                //permissions granted
            } else {
                val rationalRequired = ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                        || ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)

                if(rationalRequired){
                    Toast.makeText(context, "Location permission is required for this feature to work", Toast.LENGTH_LONG).show()
                }
                else{// if need to manually set in device settings
                    Toast.makeText(context, "Location permission is required. Please enable it in your device settings", Toast.LENGTH_LONG).show()
                }
            }
        })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Location not available")

        Button(onClick = {
            if(locationUtils.hasLocationPermissions(context)){

            }
            else{
                requestPermissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            }
        }) {
            Text(text = "Get Location")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationAppPreview(){
    LocationApp()
}