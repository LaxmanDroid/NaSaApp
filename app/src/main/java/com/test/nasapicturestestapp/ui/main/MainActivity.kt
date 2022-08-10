package com.test.nasapicturestestapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.test.nasapicturestestapp.R
import com.test.nasapicturestestapp.data.NasaPicture
import com.test.nasapicturestestapp.databinding.ActivityMainBinding
import com.test.nasapicturestestapp.di.component.DaggerActivityComponent
import com.test.nasapicturestestapp.di.module.ActivityModule
import com.test.nasapicturestestapp.ui.PicturesListAdapter
import com.test.nasapicturestestapp.ui.details.DetailsActivity
import com.test.nasapicturestestapp.utils.EqualSpacingItemDecoration
import com.test.nasapicturestestapp.utils.Loading
import com.test.nasapicturestestapp.utils.Success
import com.test.nasapicturestestapp.utils.Error

import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var adapter: PicturesListAdapter

    @Inject
    lateinit var glm: GridLayoutManager

    companion object {
        const val SINGLE_PICTURE = "picture"
        const val CLICKED_POSITION = "clicked_position"
        const val GRID_SPAN_COUNT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDependencies()

        val view = binding.root

        setContentView(view)
        setupPicturesList()
        setupObservers()

        viewModel.getPicturesList()
    }

    private fun setupObservers() {

        viewModel.picturesListState.observe(this) {
            when (it) {
                is Error -> {
                    showMessage(it.resId)
                }
                Loading -> {
                }
                is Success<*> -> {
                    if (it.data is List<*>) {
                        val nasaPicturesList: List<NasaPicture> = it.data as List<NasaPicture>
                        adapter.refreshList(nasaPicturesList)
                    }
                }
            }
        }
    }

    private fun showMessage(resId: Int) {
        //snackbar
        Snackbar.make(binding.rvPicturesList,resId, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupPicturesList() {

        binding.rvPicturesList.layoutManager = glm
        binding.rvPicturesList.addItemDecoration(
            EqualSpacingItemDecoration(
                resources.getDimension(R.dimen.default_margin_grid).toInt()
            )
        )
        adapter.openDetailsClickLister = {
            Intent(this, DetailsActivity::class.java).run {
                putExtra(CLICKED_POSITION, it)
                startActivity(this)
            }
        }
        binding.rvPicturesList.adapter = adapter
    }

    private fun getDependencies() {
        DaggerActivityComponent
            .builder()
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }
}