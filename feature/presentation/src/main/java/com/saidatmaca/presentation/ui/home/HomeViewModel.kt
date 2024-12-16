package com.saidatmaca.presentation.ui.home

import ViewModelStateFlow
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.saidatmaca.common.enums.SortValues
import com.saidatmaca.common.enums.UIEvent
import com.saidatmaca.core.viewmodel.BaseViewModel
import com.saidatmaca.domain.observeFavCoinList
import com.saidatmaca.domain.use_cases.CryptoUseCase
import com.saidatmaca.model.AllCoinResponse
import com.saidatmaca.model.Coin
import com.saidatmaca.model.CoinFavModel
import com.saidatmaca.model.Data
import com.saidatmaca.model.Stats
import com.saidatmaca.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cryptoUseCase: CryptoUseCase,
) : BaseViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


   // private var job: Job? = null
    private val _apiResponse: MutableState<AllCoinResponse?> = mutableStateOf(null)

    val apiResponse: State<AllCoinResponse?> = _apiResponse
    private val _coins: MutableState<List<Coin>> = mutableStateOf(listOf())

    val coins: State<List<Coin>> = _coins
    private val _defaultCoins: MutableState<List<Coin>> = mutableStateOf(listOf())

    val defaultCoins: State<List<Coin>> = _defaultCoins
    private val _favList: MutableState<List<CoinFavModel>> = mutableStateOf(listOf())


    val favList: State<List<CoinFavModel>> = _favList


    fun sortCoinList(type: Int) {

        when (type) {
            SortValues.Default.value -> {
                _coins.value = _defaultCoins.value
            }

            SortValues.Price.value -> {
                _coins.value = _coins.value.sortedByDescending { it.price }
            }

            SortValues.MarketCap.value -> {
                _coins.value = _coins.value.sortedByDescending { it.marketCap }
            }

            SortValues.Volume.value -> {
                _coins.value = _coins.value.sortedByDescending { it.`24hVolume` }
            }

            SortValues.Change.value -> {
                _coins.value = _coins.value.sortedByDescending { it.change }
            }

            SortValues.ListedAt.value -> {
                _coins.value = _coins.value.sortedByDescending { it.listedAt }
            }
        }
    }


    fun goToDetailScreen(coin: Coin) {
        viewModelScope.launch {

            _eventFlow.emit(UIEvent.Navigate(Screen.DetailScreen.route))
        }
    }

    fun checkFavCard(coin: Coin): Boolean {
        val subList = _favList.value.filter { it.uuid == coin.uuid }

        return subList.isNotEmpty()
    }


    init {

      //  getAllCryptoData()

        this.observeFavCoinList(cryptoUseCase) {
            _favList.value = it

        }


    }


    internal val uiState: ViewModelStateFlow<HomeUIState> = viewModelStateFlow(HomeUIState.Loading)

    // Flow kullanarak veri akışı
    val allCoinResponse: StateFlow<AllCoinResponse> = cryptoUseCase
        .getAllCryptoData(
            onStart = {
                uiState.tryEmit(key, HomeUIState.Loading)
            },
            onComplete = {
                uiState.tryEmit(key, HomeUIState.Success)
            },
            onError = { uiState.tryEmit(key, HomeUIState.Error(it)) }
        )
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AllCoinResponse(status = "", data = Data(stats = Stats(), coins = emptyList())),
    )



    sealed interface HomeUIState{
        data object Success: HomeUIState
        data object Loading :HomeUIState
        data class Error(val message: String?) : HomeUIState
    }

}