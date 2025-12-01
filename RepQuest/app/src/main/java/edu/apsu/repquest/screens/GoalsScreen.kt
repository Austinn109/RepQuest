
package edu.apsu.repquest.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.apsu.repquest.dataclasses.Goals

@Composable
fun GoalsScreen() {
    val goals = remember { mutableStateListOf<Goals>() }
    var showCreateDialog by remember { mutableStateOf(false) }
    var totalXP by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(64.dp))
        Text("Goals", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)

        ){
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "Total XP: $totalXP",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Level: ${totalXP / 100}",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }


        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = { showCreateDialog = true }) {
            Text("Create A Goal")
        }
        Spacer(modifier = Modifier.size(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(goals, key = { it.id }) { goal ->
                GoalCard(
                    goal = goal,
                    onUpdate = { newCurrent ->
                        val index = goals.indexOfFirst { it.id == goal.id }
                        if (index != -1) {
                            val oldGoal = goals[index]
                            val current = newCurrent.toFloatOrNull() ?: 0f
                            val target = oldGoal.targetValue.toFloatOrNull() ?: 1f
                            
                            if (current >= target && !oldGoal.isCompleted) {
                                //awarded 50 xp for achieving a goal
                                val xpReward = 50
                                totalXP += xpReward
                                goals[index] = oldGoal.copy(
                                    currentValue = newCurrent,
                                    isCompleted = true,
                                    xpAwarded = xpReward
                                )
                            } else {
                                goals[index] = oldGoal.copy(currentValue = newCurrent)
                            }
                        }
                    },
                    onEdit = { newName, newTarget ->
                        val index = goals.indexOfFirst { it.id == goal.id }
                        if (index != -1) {
                            val oldGoal = goals[index]
                            goals[index] = oldGoal.copy(
                                name = newName,
                                targetValue = newTarget,
                                isCompleted = false,
                                xpAwarded = 0
                            )
                        }
                    },
                    onDelete = { goals.remove(goal) }
                )
            }
        }
    }

    if (showCreateDialog) {
        GoalDialog(
            title = "Create New Goal",
            initialName = "",
            initialTarget = "",
            onDismiss = { showCreateDialog = false },
            onConfirm = { name, target ->
                goals.add(Goals(name = name, targetValue = target, currentValue = "0"))
                showCreateDialog = false
            }
        )
    }
}

@Composable
fun GoalCard(
    goal: Goals,
    onUpdate: (String) -> Unit,
    onEdit: (String, String) -> Unit,
    onDelete: () -> Unit
) {
    var showUpdateDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val current = goal.currentValue.toFloatOrNull() ?: 0f
    val target = goal.targetValue.toFloatOrNull() ?: 1f
    val progress = (current / target).coerceIn(0f, 1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = if (goal.isCompleted) {
            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        } else {
            CardDefaults.cardColors()
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(goal.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    if (goal.isCompleted) {
                        Text(
                            "Completed! +${goal.xpAwarded} XP",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                IconButton(onClick = { showUpdateDialog = true }) {
                    Icon(Icons.Default.Add, "Update")
                }
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(Icons.Default.Edit, "Edit")
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, "Delete")
                }
            }

            Spacer(modifier = Modifier.size(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Current: ${goal.currentValue}", fontSize = 14.sp)
                Text("Target: ${goal.targetValue}", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.size(8.dp))
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())

            Text("${(progress * 100).toInt()}% Complete",
                fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
        }
    }

    if (showUpdateDialog) {
        var newValue by remember { mutableStateOf(goal.currentValue) }
        AlertDialog(
            onDismissRequest = { showUpdateDialog = false },
            title = { Text("Update Progress") },
            text = {
                OutlinedTextField(
                    value = newValue,
                    onValueChange = { newValue = it },
                    label = { Text("Current Value") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newValue.isNotBlank()) {
                        onUpdate(newValue)
                        showUpdateDialog = false
                    }
                }) { Text("Update") }
            },
            dismissButton = { TextButton(onClick =
                { showUpdateDialog = false }) { Text("Cancel") } }
        )
    }

    if (showEditDialog) {
        GoalDialog(
            title = "Edit Goal",
            initialName = goal.name,
            initialTarget = goal.targetValue,
            onDismiss = { showEditDialog = false },
            onConfirm = { name, target ->
                onEdit(name, target)
                showEditDialog = false
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Goal") },
            text = { Text("Delete '${goal.name}'?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) { Text("Delete", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") } }
        )
    }
}

@Composable
fun GoalDialog(
    title: String,
    initialName: String,
    initialTarget: String,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var target by remember { mutableStateOf(initialTarget) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Goal Name") },
                    placeholder = { Text("e.g., Push-ups") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(12.dp))
                OutlinedTextField(
                    value = target,
                    onValueChange = { target = it },
                    label = { Text("Target Value") },
                    placeholder = { Text("e.g., 100") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (name.isNotBlank() && target.isNotBlank()) onConfirm(name, target) },
                enabled = name.isNotBlank() && target.isNotBlank()
            ) { Text(if (initialName.isEmpty()) "Create" else "Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}


/*
 PLACE HOLDER SCREEN

package edu.apsu.repquest.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GoalsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Goals Screen")
    }
}
*/
