package ir.divar.interviewtask.hiltmodule

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.divar.data.place.PlaceRepository
import ir.divar.data.place.remote.PlaceRemoteRepository
import ir.divar.domain.place.IPlacesRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindIPlaceRepository(placeRepository: PlaceRepository): IPlacesRepository

    @Singleton
    @Provides
    fun provideRepository(placeRemoteRepository: PlaceRemoteRepository) =
        PlaceRepository(placeRemoteRepository)
}