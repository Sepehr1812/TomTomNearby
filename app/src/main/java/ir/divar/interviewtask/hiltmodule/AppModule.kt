package ir.divar.interviewtask.hiltmodule

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.divar.data.place.PlaceRepository
import ir.divar.domain.place.IPlacesRepository


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindIPlaceRepository(placeRepository: PlaceRepository): IPlacesRepository
}