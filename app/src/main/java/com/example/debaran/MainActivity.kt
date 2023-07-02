package com.example.debaran

import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.debaran.features.callQuality.data.repositories.CallQualityRepositoryImpl
import com.example.debaran.features.callQuality.domain.usecases.CallQualityChecker
import com.example.debaran.features.callQuality.views.CallQualityViewModel
import com.example.debaran.ui.theme.DebaranTheme

class MainActivity : ComponentActivity() {

    private val telephonyManager: TelephonyManager by lazy {
        getSystemService(TelephonyManager::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebaranTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val callRepo = CallQualityRepositoryImpl(this)
                    CallQualityChecker(this).startCheckingCallQuality(callRepo)
                    CallQualityViewModel(callRepo).CallQualityView()
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
fun FABBuilder() {
    FloatingActionButton(onClick = { /*TODO*/ },
        shape = RoundedCornerShape(16),
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add FAB",
            tint = Color.White,
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
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