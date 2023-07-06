package com.example.debaran.features.callQuality.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.debaran.R
import com.example.debaran.core.theme.DebaranTheme
import com.example.debaran.core.theme.Dimen.dp90
import com.example.debaran.core.theme.cyanDark
import com.example.debaran.core.theme.purpleColor
import com.example.debaran.core.utils.AppUtils.launchUrl
import com.example.debaran.features.callQuality.data.repositories.CallStateRepositoryImpl
import com.example.debaran.features.callQuality.domain.usecases.Dialer
import com.example.debaran.features.callQuality.views.components.CircularBox
import com.example.debaran.features.callQuality.views.components.ConnectivityStatus
import com.example.debaran.features.callQuality.views.components.ThemeButton
import es.dmoral.toasty.Toasty

@Composable
fun DashboardScreen(
    onConnectClick: () -> Unit,
    onDisconnect: () -> Unit,
    onCallClick: () -> Unit,
    connectivityStatus: ConnectivityStatus = ConnectivityStatus.NONE,
) {
    val context = LocalContext.current

    val callStateRepo = CallStateRepositoryImpl.getInstance()
    val isCallOffTheHook = callStateRepo.getCallStateLiveData().observeAsState()

    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 5.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { context.launchUrl(context.getString(R.string.developer_github)) }
                        )
                    },
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))

        CircularBox(
            status = connectivityStatus,
            onClick = {
                if (connectivityStatus.isDisconnected()) {
                    // TODO
                    // Check if the Call is OFF_THE_HOOK
                    if (true) {
                        onConnectClick()
                    } else {
                        Toasty.warning(context, context.getString(R.string.no_call_in_progress)).show()
                    }
                } else {
                    onDisconnect()
                }
            }
        )

        Spacer(modifier = Modifier.padding(top = 30.dp))

        Text(
            text = stringResource(
                when (connectivityStatus) {
                    ConnectivityStatus.CONNECTED -> R.string.measurement_details_title
//                    ConnectivityStatus.NONE -> R.string.tap_to_start
//                    ConnectivityStatus.DISCONNECT -> R.string.tap_to_start
//                    ConnectivityStatus. -> R.string.tap_to_start
                    else -> R.string.tap_to_start
                }
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondary
        )

        // TODO Measurements View
//        Text(
//            text = ipText,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(top = 3.dp)
//                .animateContentSize(),
//            style = MaterialTheme.typography.titleLarge,
//            color = ipTextColor,
//        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(dp90)
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(
                            0.5f to cyanDark, 1f to purpleColor
                        )
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
        ) {
            TxtField()

            Spacer(modifier = Modifier.padding(start = 10.dp))

            ThemeButton(
                onClick = onCallClick,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(5.dp)),
                text = stringResource(R.string.call),
                enabled = !connectivityStatus.isConnected() && !connectivityStatus.isConnecting()
            )
        }

            Spacer(modifier = Modifier.width(10.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TxtField() {
    val dialer = Dialer.getInstance()
    val placeHolder = stringResource(R.string.enter_your_number)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        TextField(
            value = dialer.dialerValue.value ?: "",
            onValueChange = { dialer.dialerValue.postValue(it) },
            placeholder = { Text(text = placeHolder) },
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                // below line is use for capitalization
                // inside our text field.
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Phone,
            ),
            textStyle = MaterialTheme.typography.labelMedium,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DebaranTheme {
        TxtField()
    }
}