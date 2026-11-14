package com.noxkie.cannonchest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF090D16)
private val Panel = Color(0xFF141C2B)
private val Cyan = Color(0xFF45D7E8)
private val Gold = Color(0xFFFFC857)
private val Muted = Color(0xFF91A0B7)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme(colorScheme = darkColorScheme(primary = Cyan, background = Bg, surface = Panel)) { Dashboard() } }
    }
}

@Composable
fun Dashboard() {
    var running by remember { mutableStateOf(false) }
    var autoFarm by remember { mutableStateOf(false) }
    var autoDetect by remember { mutableStateOf(false) }
    var speed by remember { mutableFloatStateOf(1f) }
    var discovered by remember { mutableIntStateOf(0) }
    var collected by remember { mutableIntStateOf(0) }
    var destination by remember { mutableStateOf("Not connected to a game session") }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Bg).padding(horizontal = 18.dp),
        contentPadding = PaddingValues(top = 24.dp, bottom = 32.dp), verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("CANNON CHEST", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Black, letterSpacing = 1.4.sp)
            Text("COMPANION  /  FORTNITE", color = Cyan, fontSize = 11.sp, letterSpacing = 2.sp)
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(8.dp).background(if (running) Cyan else Muted, RoundedCornerShape(50)))
                Spacer(Modifier.width(8.dp))
                Text(if (running) "TRACKING SESSION" else "STANDBY", color = Muted, fontSize = 11.sp)
                Spacer(Modifier.weight(1f))
                Text("v1.0.0", color = Muted, fontSize = 11.sp)
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard("DISCOVERED", discovered.toString(), Modifier.weight(1f), Gold)
                StatCard("COLLECTED", collected.toString(), Modifier.weight(1f), Cyan)
                StatCard("REMAINING", (discovered - collected).coerceAtLeast(0).toString(), Modifier.weight(1f), Color.White)
            }
        }
        item {
            SectionTitle("CHEST MAP", "MANUAL COMPANION TRACKER")
            Box(Modifier.fillMaxWidth().height(220.dp).background(Color(0xFF101A25), RoundedCornerShape(16.dp)).border(1.dp, Color(0xFF26364A), RoundedCornerShape(16.dp)).padding(12.dp)) {
                Canvas(Modifier.fillMaxSize()) {
                    val stepX = size.width / 8f; val stepY = size.height / 6f
                    for (i in 1..7) drawLine(Color(0xFF253447), Offset(stepX * i, 0f), Offset(stepX * i, size.height), 1f)
                    for (i in 1..5) drawLine(Color(0xFF253447), Offset(0f, stepY * i), Offset(size.width, stepY * i), 1f)
                    drawCircle(Cyan, 7f, Offset(size.width * .23f, size.height * .34f))
                    drawCircle(Gold, 7f, Offset(size.width * .68f, size.height * .58f))
                    drawCircle(Gold, 7f, Offset(size.width * .47f, size.height * .78f))
                }
                Column(Modifier.align(Alignment.BottomStart)) {
                    Text("● YOU / ROUTE  •  ◆ SAVED CHEST MARKERS", color = Muted, fontSize = 9.sp)
                    Text("Illustrative grid — not live Fortnite telemetry", color = Muted, fontSize = 9.sp)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { discovered++ }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Panel)) { Text("+ DISCOVERED") }
                Button(onClick = { if (collected < discovered) collected++ }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Panel)) { Text("+ COLLECTED") }
            }
        }
        item {
            SectionTitle("FARMING CONTROL", "COMPANION TRACKING ONLY")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = { running = true }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF087D88))) { Text("START") }
                OutlinedButton(onClick = { running = false; autoFarm = false }, modifier = Modifier.weight(1f)) { Text("STOP") }
            }
            ToggleRow("AUTO FARM", "Tracks a repeat-farming session; does not control Fortnite.", autoFarm) { autoFarm = it }
            ToggleRow("AUTO CHEST DETECTION", "Requires a future supported game-data integration.", autoDetect) { autoDetect = it }
            Text("TRACKING INTERVAL  •  ${String.format("%.1f", speed)}x", color = Muted, fontSize = 11.sp)
            Slider(value = speed, onValueChange = { speed = it }, valueRange = .5f..3f, steps = 4)
        }
        item {
            SectionTitle("CANNON DESTINATION", "NO TELEPORT EXECUTION")
            Box(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(14.dp)).padding(14.dp)) {
                Column {
                    Text(destination, color = Color.White, fontSize = 13.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Coordinates can be recorded manually. Actual cannon launch/teleport requires game-side integration.", color = Muted, fontSize = 11.sp)
                }
            }
        }
        item {
            SectionTitle("INTEGRATION STATUS", "HONEST FEATURE BOUNDARIES")
            StatusLine("Chest log / counters", "WORKING", true)
            StatusLine("Companion session controls", "WORKING", true)
            StatusLine("Map marker display", "LOCAL DEMO GRID", true)
            StatusLine("Live chest detection", "NOT CONNECTED", false)
            StatusLine("Cannon teleport / auto collect", "GAME INTEGRATION REQUIRED", false)
        }
        item { Text("This app is an external companion. It does not read Fortnite memory, inject into the game, or send gameplay inputs.", color = Muted, fontSize = 10.sp) }
    }
}

@Composable fun StatCard(label: String, value: String, modifier: Modifier, accent: Color) {
    Column(modifier.background(Panel, RoundedCornerShape(13.dp)).padding(12.dp)) {
        Text(label, color = Muted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        Text(value, color = accent, fontSize = 25.sp, fontWeight = FontWeight.Black)
    }
}

@Composable fun SectionTitle(title: String, sub: String) {
    Column {
        Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp, letterSpacing = 1.sp)
        Text(sub, color = Muted, fontSize = 9.sp, letterSpacing = 1.sp)
    }
}

@Composable fun ToggleRow(title: String, detail: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(detail, color = Muted, fontSize = 10.sp)
        }
        Switch(checked = checked, onCheckedChange = onChecked)
    }
}

@Composable fun StatusLine(name: String, status: String, available: Boolean) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(name, color = Color.White, fontSize = 11.sp, modifier = Modifier.weight(1f))
        Text(status, color = if (available) Cyan else Gold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
    }
}
