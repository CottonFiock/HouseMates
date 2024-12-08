import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.jetpacknavigationdrawer.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            // Replace with actual data loading logic
            _users.value = listOf(
                User("Alice", "Present", "https://media.istockphoto.com/id/1285993433/photo/carefree-african-american-girl-in-studio.jpg?s=612x612&w=0&k=20&c=8IrWu7J3Odp7mwZxizKfUVbDC6dvw-ttIKwvVXVIDvA="),
                User("Bob", "Away", "https://example.com/bob.jpg"),
                User("Clara", "Present", "https://example.com/clara.jpg")
            )
        }
    }
}
