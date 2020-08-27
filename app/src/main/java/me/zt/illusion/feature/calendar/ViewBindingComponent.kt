package me.zt.illusion.feature.calendar

import dagger.Component
import dagger.android.AndroidInjector

@Component(modules = [ViewBindingModule::class])
interface ViewBindingComponent : AndroidInjector<CalendarPresenter> {

}