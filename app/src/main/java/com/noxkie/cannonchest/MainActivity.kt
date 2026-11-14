package com.noxkie.cannonchest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var discovered by remember { mutableIntStateOf(0) }
            var collected by remember { mutableIntStateOf(0) }
            var running by remember { mutableStateOf(false) }

            MaterialTheme {
                Column(Modifier.padding(20.dp)) {
                    Text("CANNON CHEST COMPANION")
                    Text("Discovered: $discovered")
                    Text("Collected: $collected")

                    Button(onClick = { running = true }) {
                        Text("START")
                    }

                    Button(onClick = { running = false }) {
                        Text("STOP")
                    }

                    Button(onClick = { discovered++ }) {
                        Text("ADD CHEST")
                    }

                    Button(onClick = {
                        if (collected < discovered) collected++
                    }) {
                        Text("COLLECT CHEST")
                    }

                    Text(if (running) "TRACKING" else "STOPPED")
                }
            }
        }
    }
}
