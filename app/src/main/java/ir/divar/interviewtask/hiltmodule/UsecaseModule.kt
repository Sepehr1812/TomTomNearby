package ir.divar.interviewtask.hiltmodule

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.usecase.ClearPlaceList
import ir.divar.domain.place.usecase.GetPlaceListFromLocal
import ir.divar.domain.place.usecase.GetPlaceListFromServer
import ir.divar.domain.place.usecase.InsertPlaceList


@Module
@InstallIn(ViewModelComponent::class)
object UsecaseModule {

    @Provides
    fun provideGetPlaceListFromServer(iPlacesRepository: IPlacesRepository) =
        GetPlaceListFromServer(iPlacesRepository)

    @Provides
    fun provideGetPlaceListFromLocal(iPlacesRepository: IPlacesRepository) =
        GetPlaceListFromLocal(iPlacesRepository)

    @Provides
    fun provideInsertPlaceList(iPlacesRepository: IPlacesRepository) =
        InsertPlaceList(iPlacesRepository)

    @Provides
    fun provideClearPlaceList(iPlacesRepository: IPlacesRepository) =
        ClearPlaceList(iPlacesRepository)
}