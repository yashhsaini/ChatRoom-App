package ys.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import yogesh.example.mychatbot.Injection
import yssh.data.Result.*
import yssh.data.Room
import yssh.data.RoomRepository

class RoomViewModel : ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private val roomRepository: RoomRepository
    init {
        roomRepository = RoomRepository(Injection.instance())
        loadRooms()
        createRoom("Test")

    }

 fun createRoom(name: String) {
        viewModelScope.launch {
          roomRepository.createRoom(name)
       }
  }



    fun loadRooms() {
        viewModelScope.launch {
            when (val result = roomRepository.getRooms()) {
                is Success -> _rooms.value = result.data
                is Error -> {

                }
            }
        }
    }

}
