package ir.divar.interviewtask.hiltmodule

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.divar.data.db.ApplicationDatabase
import ir.divar.data.place.local.IPlaceDao
import ir.divar.data.place.local.PlaceLocalRepository
import ir.divar.interviewtask.util.Constants.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        synchronized(ApplicationDatabase::class) {
            Room.databaseBuilder(
                context,
                ApplicationDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

    @Singleton
    @Provides
    fun providePlaceDao(appDatabase: ApplicationDatabase) = appDatabase.placeDao()


    @Singleton
    @Provides
    fun provideRepository(dao: IPlaceDao) = PlaceLocalRepository(dao)
}