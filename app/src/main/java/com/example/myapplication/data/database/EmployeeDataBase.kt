package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class EmployeeDataBase : RoomDatabase() {
    abstract fun userDatabaseDao(): UserDAO

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE users "
                        + "ADD COLUMN address TEXT"
            )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: EmployeeDataBase? = null
        fun getInstance(context: Context): EmployeeDataBase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(EmployeeDataBase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDataBase::class.java,
                    "database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
