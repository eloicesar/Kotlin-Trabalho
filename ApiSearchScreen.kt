package com.example.gamelib.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gamelib.data.local.entity.Game
import com.example.gamelib.data.remote.GameApi
import com.example.gamelib.ui.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiSearchScreen(
    navController: NavController,
    viewModel: GameViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val apiGames by viewModel.apiGames.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var showImportDialog by remember { mutableStateOf<GameApi?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadPopularGames()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar Jogos Online") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Barra de busca
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar jogo") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Filled.Clear, "Limpar")
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (searchQuery.isNotBlank()) {
                            viewModel.searchGames(searchQuery)
                        } else {
                            viewModel.loadPopularGames()
                        }
                    },
                    enabled = !isLoading
                ) {
                    Icon(Icons.Filled.Search, "Buscar")
                }
            }

            errorMessage?.let { error ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Error,
                            contentDescription = "Erro",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = error,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { viewModel.clearError() }) {
                            Icon(Icons.Filled.Close, "Fechar")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Buscando jogos...")
                        }
                    }
                }
                apiGames.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Filled.SearchOff,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Nenhum jogo encontrado")
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(apiGames) { game ->
                            ApiGameCard(
                                game = game,
                                onClick = { showImportDialog = game }
                            )
                        }
                    }
                }
            }
        }
    }

    showImportDialog?.let { game ->
        ImportGameDialog(
            game = game,
            onDismiss = { showImportDialog = null },
            onConfirm = { importedGame ->
                viewModel.insertGame(importedGame)
                showImportDialog = null
                navController.navigateUp()
            }
        )
    }
}

@Composable
fun ApiGameCard(
    game: GameApi,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            if (game.backgroundImage != null) {
                AsyncImage(
                    model = game.backgroundImage,
                    contentDescription = game.name,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.titleMedium
                )
                
                game.genres?.firstOrNull()?.let { genre ->
                    Text(
                        text = genre.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                game.platforms?.firstOrNull()?.let { platformInfo ->
                    Text(
                        text = platformInfo.platform.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Nota",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format("%.1f", game.rating),
                        style = MaterialTheme.typography.bodySmall
                    )
                    
                    game.released?.let { released ->
                        Text(
                            text = " • $released",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            Icon(Icons.Filled.Add, "Importar")
        }
    }
}

@Composable
fun ImportGameDialog(
    game: GameApi,
    onDismiss: () -> Unit,
    onConfirm: (Game) -> Unit
) {
    var isFavorite by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Importar Jogo") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Deseja adicionar este jogo à sua biblioteca?")
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = game.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = game.genres?.joinToString { it.name } ?: "Sem gênero",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = game.platforms?.firstOrNull()?.platform?.name ?: "PC",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Marcar como favorito")
                    Switch(
                        checked = isFavorite,
                        onCheckedChange = { isFavorite = it }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val releaseYear = game.released?.substring(0, 4)?.toIntOrNull() 
                    ?: java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
                
                val importedGame = Game(
                    title = game.name,
                    genre = game.genres?.firstOrNull()?.name ?: "Diversos",
                    platform = game.platforms?.firstOrNull()?.platform?.name ?: "PC",
                    releaseYear = releaseYear,
                    imageUrl = game.backgroundImage,
                    description = game.description,
                    isFavorite = isFavorite
                )
                onConfirm(importedGame)
            }) {
                Text("Importar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
