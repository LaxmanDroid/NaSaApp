package com.test.nasapicturestestapp.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.test.nasapicturestestapp.data.NasaPicture
import com.test.nasapicturestestapp.databinding.ActivityDetailsBinding
import com.test.nasapicturestestapp.di.component.DaggerActivityComponent
import com.test.nasapicturestestapp.di.module.ActivityModule
import com.test.nasapicturestestapp.ui.main.MainActivity.Companion.CLICKED_POSITION
import com.test.nasapicturestestapp.utils.Loading
import com.test.nasapicturestestapp.utils.Success
import com.test.nasapicturestestapp.utils.ZoomOutPageTransformer
import kotlin.properties.Delegates
import com.test.nasapicturestestapp.utils.Error

import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    private var clickedPosition by Delegates.notNull<Int>()

    @Inject
    lateinit var binding: ActivityDetailsBinding

    @Inject
    lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDependencies()

        val view = binding.root
        setContentView(view)
        setupViews()
        setupObservers()

        clickedPosition = intent.getIntExtra(CLICKED_POSITION, 0)

        viewModel.getPicturesList()
    }

    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
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
                        setupSliderAdapter(it.data as List<NasaPicture>)
                    }
                }
            }
        }
    }

    private fun setupSliderAdapter(picturesList: List<NasaPicture>) {
        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager, picturesList)
        binding.pager.setPageTransformer(true, ZoomOutPageTransformer())
        binding.pager.adapter = pagerAdapter
        binding.pager.currentItem = clickedPosition
    }

    private fun getDependencies() {
        DaggerActivityComponent
                .builder()
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun showMessage(resId: Int) {
        Snackbar.make(binding.pager, resId, Snackbar.LENGTH_SHORT).show()
    }

    private inner class ScreenSlidePagerAdapter(
            fm: FragmentManager,
            private val picturesList: List<NasaPicture>
    ) :
            FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int = picturesList.size
        override fun getItem(position: Int): Fragment =
                DetailsFragment.newInstance(picturesList[position])
    }
}