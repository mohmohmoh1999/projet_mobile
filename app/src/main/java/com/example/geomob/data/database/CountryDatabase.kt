package com.example.geomob.data.database

import android.content.Context
import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.geomob.R
import com.example.geomob.data.dao.CountryDao
import com.example.geomob.data.entity.*

@Database(
    entities = [Country::class, History::class, Video::class, SlideShow::class, Twitter::class],
    version = 1,
    exportSchema = false
)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao?

    companion object {
        private var instance: CountryDatabase? = null
        @Synchronized
        fun getInstance(context: Context): CountryDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CountryDatabase::class.java, "countries_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }

        private val roomCallback: Callback = object : Callback() {
              override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                  super.onCreate(db)
                  instance?.let { PopulateDbAsyncTask(it).execute() }
              }
          }

          private open class PopulateDbAsyncTask(db: CountryDatabase) :
              AsyncTask<Void?, Void?, Void?>() {
              private val countryDao : CountryDao = db.countryDao()!!
              override fun doInBackground(vararg params: Void?): Void? {
                  countryDao.addCountries(
                      arrayListOf(
                          Country(
                              1,
                              "Algeria",
                              "algiers",
                              "Africa Country",
                              "2m",
                              "42m",
                              R.drawable.ic_algeria,
                              R.raw.algeria,
                              "",
                              ""
                          ),
                          Country(
                              2,
                              "Germany",
                              "Berlin",
                              "Europe country",
                              "357k",
                              "83m",
                              R.drawable.ic_germany,
                              R.raw.germany,
                              "",
                              ""
                          ),
                          Country(
                              3,
                              "Italy",
                              "Rome",
                              "Europe country",
                              "301k",
                              "60m",
                              R.drawable.ic_italy,
                              R.raw.italy,
                              "",
                              ""
                          ),
                          Country(
                              4,
                              "Spain",
                              "Madrid",
                              "Europe country",
                              "506k",
                              "47m",
                              R.drawable.ic_spain,
                              R.raw.spain,
                              "",
                              ""
                          ),
                          Country(
                              5,
                              "Russia",
                              "Moscow",
                              "Europe country",
                              "17m",
                              "145m",
                              R.drawable.ic_russia,
                              R.raw.russia,
                              "",
                              ""
                          )
                      )
                  )
                  countryDao.addSlideshow(
                      arrayListOf(
                          SlideShow(
                              1,
                              1,
                              "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS1xUUMYyMDQx6YqeUhJFhVBv3RXFxWaFtoog&usqp=CAU"
                          ),
                          SlideShow(
                              2,
                              1,
                              "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTdbRIzlvrYjDStWrOlSOAHBdkjaHuzZKmdPQ&usqp=CAU"
                          ),
                          SlideShow(
                              3,
                              1,
                              "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQriZGQgy654YfdPmALUJeus05SbfpJzVbiyg&usqp=CAU"
                          )
                      )
                  )
                  countryDao.addVideos(
                      arrayListOf(
                          Video(
                              1,
                              1,
                              "water",
                              "https://www.videvo.net/videvo_files/converted/2014_12/preview/Raindrops_Videvo.mp485639.webm"

                          ),
                          Video(
                              2,
                              1,
                              "forest",
                              "https://cdn.videvo.net/videvo_files/video/free/2019-05/small_watermarked/190416_10_Drone1_07_preview.webm"
                          ),
                          Video(
                              3,
                              1,
                              "river",
                              "https://www.videvo.net/videvo_files/converted/2017_12/preview/171124_B1_HD_001.mp494977.webm"

                          )
                      )
                  )

                  countryDao.addTwitter(
                      arrayListOf(
                          Twitter(
                              1,
                              1,
                              "https://twitter.com/Sabermoussa33/status/1276374342276984832"
                          ),
                          Twitter(
                              2,
                              1,
                              "https://twitter.com/AlgeriaRevolt/status/1275526613590069248"
                          )
                      )
                  )

                  countryDao.addHistories(
                      arrayListOf(
                          History(
                              1,
                              1,
                              "01-11-1954",
                              "The Algerian liberation revolution or the Algerian war, is a war that broke out in Fatih November 1954 with the participation of about 1,200 Mujahideen",
                              "https://download.vikidia.org/vikidia/fr/images/thumb/9/97/Six_chefs_FLN_-_1954.jpg/200px-Six_chefs_FLN_-_1954.jpg"
                          ),
                          History(
                              2,
                              1,
                              "05-07-1962",
                              "Algeria is liberated from French colonialism that lasted more than thirteen decades, thanks to the bombing of the great editorial revolution, which is the largest revolution in the twentieth century.",
                              "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQg0uwSijPDpNGTGHOJugYs57pFuld7cIrCUowk7dhgu17UfeFk&s"
                          )
                      )
                  )

                  return null
              }
          }
    }

}