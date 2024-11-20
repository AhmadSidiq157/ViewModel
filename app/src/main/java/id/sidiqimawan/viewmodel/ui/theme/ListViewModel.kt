package id.sidiqimawan.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<String>>(initialItems())
    val items: StateFlow<List<String>> = _items

    fun loadMoreItems() {
        val additionalItems = (items.value.size + 1..items.value.size + 10).map { "Item $it" }
        _items.value = items.value + additionalItems
    }

    companion object {
        private fun initialItems() = List(20) { "Item ${it + 1}" }
    }
}
