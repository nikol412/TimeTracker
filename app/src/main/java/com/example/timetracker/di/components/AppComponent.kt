package com.example.timetracker.di.components

import com.example.timetracker.App
import com.example.timetracker.di.modules.ActivityInjectorsModule
import com.example.timetracker.di.modules.FragmentInjectorsModule
import com.example.timetracker.di.modules.AppModule
import com.example.timetracker.di.modules.DatabaseModule
import com.example.timetracker.ui.base.BaseViewModel
import com.example.timetracker.ui.fragment.dashboard.DashboardViewModel
import com.example.timetracker.ui.fragment.home.HomeViewModel
import com.example.timetracker.ui.fragment.home.createTask.CreateTaskBottomSheetDialogFragment
import com.example.timetracker.ui.fragment.home.createTask.CreateTaskBottomSheetDialogViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        FragmentInjectorsModule::class,
        DatabaseModule::class,
        AppModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun databaseModule(databaseModule: DatabaseModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

    fun inject(baseVM: BaseViewModel)

    fun inject(homeVM: HomeViewModel)

    fun inject(createTaskBottomSheetDialogViewModel: CreateTaskBottomSheetDialogViewModel)

    fun inject(dashboardVM: DashboardViewModel)

}