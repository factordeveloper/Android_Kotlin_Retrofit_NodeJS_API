package com.factor.dev.telematic_heartbeat_retrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.factor.dev.telematic_heartbeat_retrofit.ui.theme.Telematic_Heartbeat_retrofitTheme



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.unit.dp
import com.factor.dev.telematic_heartbeat_retrofit.network.RetrofitClient
import com.factor.dev.telematic_heartbeat_retrofit.utils.buildTelematicsJson
import kotlinx.coroutines.*
import retrofit2.awaitResponse

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelematicsApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelematicsApp() {
    var responseMessage by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val apiService = RetrofitClient.apiService

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Telematics API Sender") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (isSending) "Sending data every second..." else responseMessage,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        isSending = !isSending // Alterna el estado de envío
                        if (isSending) {
                            // Inicia el envío automático
                            coroutineScope.launch {
                                while (isSending) {
                                    try {
                                        val json = buildTelematicsJson()
                                        val response = apiService.sendTelematicsData(json).awaitResponse()
                                        responseMessage = if (response.isSuccessful) {
                                            "Success: ${response.body()?.string() ?: "No response body"}"
                                        } else {
                                            "Error: ${response.code()}"
                                        }
                                    } catch (e: Exception) {
                                        responseMessage = "Error: ${e.message}"
                                    }
                                    delay(1000) // Espera de 1 segundo entre envíos
                                }
                            }
                        } else {
                            responseMessage = "Stopped sending data"
                        }
                    }
                ) {
                    Text(if (isSending) "Stop Sending" else "Start Sending")
                }
            }
        }
    }
}
