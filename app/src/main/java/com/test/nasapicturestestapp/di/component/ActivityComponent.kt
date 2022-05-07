package com.test.nasapicturestestapp.di.component


import com.test.nasapicturestestapp.di.module.ActivityModule
import com.test.nasapicturestestapp.ui.details.DetailsActivity
import com.test.nasapicturestestapp.ui.main.MainActivity
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent{

    fun inject(mainActivity: MainActivity)

    fun inject(detailsActivity: DetailsActivity)

}