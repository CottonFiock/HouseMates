package com.app.housemates.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.housemates.data.model.GroceryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class GroceryItemViewModel : ViewModel() {
    private val _groceryItems = MutableStateFlow<List<GroceryItem>>(emptyList())
    val groceryItems : StateFlow<List<GroceryItem>> = _groceryItems

    private val firestore = FirebaseFirestore.getInstance()
    private val groceryItemsCollection = firestore.collection("groceries")
    //private val groceryItemsCollection = firestore.collection("groceries")

    init {
        loadGroceryItems()
    }

    private fun loadGroceryItems() {

        viewModelScope.launch {
            try {
                val result = groceryItemsCollection.get().await()
                val items = result.documents.map { document ->
                    document.toObject(GroceryItem::class.java) ?: GroceryItem("Unknown")
                }
                _groceryItems.value = items
            } catch (e: Exception) {
                Log.e("GroceryItemViewModel", "Error loading grocery items", e)
            }
        }
    }

    fun addGroceryItem(name: String) {
        val newItem = GroceryItem(name)

        // Update local state
        _groceryItems.value += newItem

        // Add to Firestore
        groceryItemsCollection.add(newItem)
            .addOnSuccessListener { documentReference ->
                Log.d("GroceryItemViewModel", "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("GroceryItemViewModel", "Error adding document", e)
            }
    }

}