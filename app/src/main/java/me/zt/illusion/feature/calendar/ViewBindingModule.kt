package me.zt.illusion.feature.calendar

import android.view.View
import androidx.databinding.ViewDataBinding
import dagger.Module
import dagger.Provides
import me.zt.illusion.MyApp
import me.zt.illusion.data.User

@Module
class ViewBindingModule(private val user: User, private val viewBinding: View) {

    @Provides
    internal fun provideUser() : User = user

    @Provides
    fun providerViewBinding() : View = viewBinding

//    @Provides
//    fun providerUnit() : MyApp = MyApp()

}