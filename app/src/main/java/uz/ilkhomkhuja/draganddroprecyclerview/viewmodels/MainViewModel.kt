package uz.ilkhomkhuja.draganddroprecyclerview.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.ilkhomkhuja.draganddroprecyclerview.models.SongData
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val songMutableLiveData: MutableLiveData<MutableList<SongData>> =
        MutableLiveData(mutableListOf())
    val songLiveData: LiveData<List<SongData>> = songMutableLiveData.map { it.toList() }

    init {
        loadData()
    }

    fun move(toPosition: Int, fromPosition: Int) {
        songMutableLiveData.value?.let { list ->
            val item = list.removeAt(fromPosition)
            list.add(toPosition, item)
            songMutableLiveData.value = list
        }
    }

    fun dismiss(position: Int) {
        songMutableLiveData.value.let { list ->
            songMutableLiveData.value?.removeAt(position)
            songMutableLiveData.value = list
        }
    }

    private fun loadData() {
        songMutableLiveData.value?.clear()
        for (i in 0..10) {
            songMutableLiveData.value?.add(
                SongData(
                    "Image Dragons & K.Flay",
                    "Thunder (Offical Remix)"
                )
            )
            songMutableLiveData.value?.add(SongData("Craig Ballie", "Morning Rise"))
            songMutableLiveData.value?.add(SongData("Jarico", "Landscape"))
            songMutableLiveData.value?.add(SongData("Lil Pump", "Gucci Gang"))
            songMutableLiveData.value?.add(SongData("Stephan F", "Astronomia 2K19 (Radio Edit)"))
            songMutableLiveData.value?.add(SongData("Stromae", "Alors On Danse"))
            songMutableLiveData.value?.add(SongData("Mario Joy", "California"))
            songMutableLiveData.value?.add(SongData("Willy William", "Ego"))
            songMutableLiveData.value?.add(SongData("Jay San", "Ride it"))
            songMutableLiveData.value?.add(SongData("Eminem", "Lose Your self"))
            songMutableLiveData.value?.add(SongData("Axwell Ingrosso", "More Than You Know"))
            songMutableLiveData.value?.add(SongData("50 Cent", "No Romeo No Jullet"))
        }
    }
}