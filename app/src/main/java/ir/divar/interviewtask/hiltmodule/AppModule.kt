package ir.divar.interviewtask.hiltmodule

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.divar.data.place.PlaceRepository
import ir.divar.data.place.local.PlaceLocalRepository
import ir.divar.data.place.remote.PlaceRemoteRepository
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.usecase.GetPlaceListByLinkFromServer
import ir.divar.domain.place.usecase.GetPlaceListFromLocal
import ir.divar.domain.place.usecase.GetPlaceListFromServer
import ir.divar.domain.place.usecase.InsertPlaceList
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindIPlaceRepository(placeRepository: PlaceRepository): IPlacesRepository

    @Singleton
    @Provides
    fun provideRepository(
        placeRemoteRepository: PlaceRemoteRepository,
        placeLocalRepository: PlaceLocalRepository
    ) = PlaceRepository(placeRemoteRepository, placeLocalRepository)

    @Singleton
    @Provides
    fun provideGetPlaceListFromServer(iPlacesRepository: IPlacesRepository) =
        GetPlaceListFromServer(iPlacesRepository)

    @Singleton
    @Provides
    fun provideGetPlaceListByLinkFromServer(iPlacesRepository: IPlacesRepository) =
        GetPlaceListByLinkFromServer(iPlacesRepository)

    @Singleton
    @Provides
    fun provideGetPlaceListFromLocal(iPlacesRepository: IPlacesRepository) =
        GetPlaceListFromLocal(iPlacesRepository)

    @Singleton
    @Provides
    fun provideInsertPlaceList(iPlacesRepository: IPlacesRepository) =
        InsertPlaceList(iPlacesRepository)
}