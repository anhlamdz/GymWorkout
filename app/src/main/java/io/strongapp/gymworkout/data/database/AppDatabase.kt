package io.strongapp.gymworkout.data.database

import io.strongapp.gymworkout.data.database.TypeConverter
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.strongapp.gymworkout.data.database.daos.ExercisesDao
import io.strongapp.gymworkout.data.database.daos.FoodNutritionDao
import io.strongapp.gymworkout.data.database.entities.ExerciseGymEntity
import io.strongapp.gymworkout.data.database.daos.UserDao
import io.strongapp.gymworkout.data.database.entities.NutritionEntity

import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity

@TypeConverters(TypeConverter::class)
@Database(
    entities = [ExerciseGymEntity::class, UserEntity::class,NutritionEntity::class,WorkoutEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun useDao(): UserDao
    abstract fun exerciseDao(): ExercisesDao
    abstract fun foodNutritionDao() : FoodNutritionDao
    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        fun getDatabase(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "recent_database"
        ).build()
    }
}