package com.example.gamelib.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamelib.data.local.AppDatabase
import com.example.gamelib.data.local.entity.Game
import com.example.gamelib.data.local.entity.Review
import com.example.gamelib.data.remote.GameApi
import com.example.gamelib.data.remote.GameApiService
import com.example.gamelib.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: GameRepository
    
    val allGames: StateFlow<List<Game>>
    val favoriteGames: StateFlow<List<Game>>
    
    private val _apiGames = MutableStateFlow<List<GameApi>>(emptyList())
    val apiGames: StateFlow<List<GameApi>> = _apiGames.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        val apiService = GameApiService.create()
        repository = GameRepository(
            database.gameDao(),
            database.reviewDao(),
            apiService
        )
        
        val gamesFlow = MutableStateFlow<List<Game>>(emptyList())
        val favoritesFlow = MutableStateFlow<List<Game>>(emptyList())
        
        allGames = gamesFlow.asStateFlow()
        favoriteGames = favoritesFlow.asStateFlow()
        
        viewModelScope.launch {
            repository.getAllGames().collect {
                gamesFlow.value = it
            }
        }
        
        viewModelScope.launch {
            repository.getFavoriteGames().collect {
                favoritesFlow.value = it
            }
        }
    }

    // CRUD Jogos
    fun insertGame(game: Game) = viewModelScope.launch {
        repository.insertGame(game)
    }

    fun updateGame(game: Game) = viewModelScope.launch {
        repository.updateGame(game)
    }

    fun deleteGame(game: Game) = viewModelScope.launch {
        repository.deleteGame(game)
    }

    suspend fun getGameById(id: Int): Game? {
        return repository.getGameById(id)
    }

    // CRUD Avaliações
    fun getReviewsByGame(gameId: Int): StateFlow<List<Review>> {
        val flow = MutableStateFlow<List<Review>>(emptyList())
        viewModelScope.launch {
            repository.getReviewsByGame(gameId).collect {
                flow.value = it
            }
        }
        return flow.asStateFlow()
    }

    fun insertReview(review: Review) = viewModelScope.launch {
        repository.insertReview(review)
    }

    fun updateReview(review: Review) = viewModelScope.launch {
        repository.updateReview(review)
    }

    fun deleteReview(review: Review) = viewModelScope.launch {
        repository.deleteReview(review)
    }

    // API
    fun searchGames(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.searchGamesFromApi(query)
                _apiGames.value = response.results
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao buscar jogos: ${e.message}"
                _apiGames.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPopularGames() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.getPopularGames()
                _apiGames.value = response.results
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar jogos: ${e.message}"
                _apiGames.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
