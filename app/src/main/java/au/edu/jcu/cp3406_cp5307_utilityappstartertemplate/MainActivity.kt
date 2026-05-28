package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CP3406_CP5603UtilityAppStarterTemplateTheme {
                UtilityApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        UtilityApp()
    }
}

@Composable
fun UtilityApp() {
    var selectedTab by remember { mutableStateOf("Utility") }
    var dailyGoalMl by remember { mutableIntStateOf(2000) }
    var intakeMl by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Utility") },
                    label = { Text("Utility") },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Utility" -> UtilityScreen()
                "Settings" -> SettingsScreen()
            }
        }
    }
}

@Composable
fun UtilityScreen(
    intakeMl: Int,
    dailyGoalMl: Int,
    onAdd: (Int) -> Unit,
    onReset: () -> Unit
) {
    val progress = if (dailyGoalMl > 0) intakeMl.toFloat() / dailyGoalMl else 0f
    val goalReached = intakeMl >= dailyGoalMl

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Hydration Tracker", style = MaterialTheme.typography.headlineMedium)

        Text(
            text = "$intakeMl / $dailyGoalMl ml",
            style = MaterialTheme.typography.displaySmall,
            color = if (goalReached) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )

        if (goalReached) {
            Text(
                "🎉 Daily goal reached!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Text(
                "${dailyGoalMl - intakeMl} ml remaining",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Text("Add water:", style = MaterialTheme.typography.labelLarge)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onAdd(150) }) { Text("+150 ml") }
            Button(onClick = { onAdd(250) }) { Text("+250 ml") }
            Button(onClick = { onAdd(500) }) { Text("+500 ml") }
        }

        OutlinedButton(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset Today's Intake")
        }
    }
}

@Composable
fun SettingsScreen(
    dailyGoalMl: Int,
    onGoalChange: (Int) -> Unit
) {
    var goalInput by remember(dailyGoalMl) { mutableStateOf(dailyGoalMl.toString()) }
    val isValid = goalInput.toIntOrNull()?.let { it > 0 } == true
    }
}

