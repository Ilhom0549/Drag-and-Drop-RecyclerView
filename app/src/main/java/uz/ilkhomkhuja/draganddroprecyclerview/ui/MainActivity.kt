package uz.ilkhomkhuja.draganddroprecyclerview.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import uz.ilkhomkhuja.draganddroprecyclerview.adapters.SongAdapter
import uz.ilkhomkhuja.draganddroprecyclerview.databinding.ActivityMainBinding
import uz.ilkhomkhuja.draganddroprecyclerview.helpers.CustomItemTouchHelper
import uz.ilkhomkhuja.draganddroprecyclerview.viewmodels.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    @Inject
    lateinit var songAdapter: SongAdapter
    private lateinit var customItemTouchHelper: CustomItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Music player"
        setupAdapter()
        observeData()
    }

    private fun observeData() {
        mainViewModel.songLiveData.observe(this) { list ->
            songAdapter.submitList(list)
        }
    }

    private fun setupAdapter() {
        customItemTouchHelper = CustomItemTouchHelper(mainViewModel, resources)
        val itemTouchHelper = ItemTouchHelper(customItemTouchHelper)
        itemTouchHelper.attachToRecyclerView(binding.rv)
        songAdapter.onLongClickListener = {
            itemTouchHelper.startDrag(it)
        }
        binding.rv.run {
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        binding.rv.adapter = songAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}