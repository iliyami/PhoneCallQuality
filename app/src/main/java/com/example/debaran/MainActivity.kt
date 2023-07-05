package com.example.debaran

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.debaran.core.theme.DebaranTheme
import com.example.debaran.features.callQuality.data.repositories.CallQualityRepositoryImpl
import com.example.debaran.features.callQuality.domain.usecases.CallQualityChecker
import com.example.debaran.features.callQuality.views.CallQualityViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebaranTheme {
                val isGranted = remember { mutableStateOf(false) }
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) {
                        isGrantedVal ->
                    run {
                        Toast.makeText(this, "isGranted = $isGranted", Toast.LENGTH_SHORT).show()
                        isGranted.value = isGrantedVal
                    }


                }
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box (
                        Modifier.fillMaxSize(),
                        Alignment.Center
                    ) {
                        FloatingActionButton(onClick = {
                            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }) {
                            Icon(Icons.Rounded.Call, "Call")
                        }
                    }

                    if (isGranted.value) {
                        val callRepo = CallQualityRepositoryImpl(this@MainActivity)
                        CallQualityChecker(this@MainActivity).startCheckingCallQuality(callRepo)
                        CallQualityViewModel(callRepo).CallQualityView()
                    }
                    else {
                        Greeting("No Permissions")
                    }
                }
//                CallQualityView().QualityButton(callQualityChecker = CallQualityChecker(this))
//                val contact = "+989122647213"
//                val intent = Intent(Intent.ACTION_DIAL)
//                intent.data = Uri.parse("tel:$contact")
//                startActivity(intent)
            }
        }
    }
}

@Composable
fun FABBuilder(context: Context) {

}

@Composable
fun Greeting(text: String, modifier: Modifier = Modifier) {
    Text(
        text = "$text!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DebaranTheme {
        Greeting("Android")
    }
}