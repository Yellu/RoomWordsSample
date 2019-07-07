package com.yellu.roomwordssample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import android.os.AsyncTask

@Database(entities = [Word::class], version = 2, exportSchema = false)
abstract class WordRoomDatabase:RoomDatabase() {

    abstract fun wordDao():WordDao

    companion object {
        @Volatile
        private var instance: WordRoomDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            WordRoomDatabase::class.java, "word_database")
            .fallbackToDestructiveMigration()
            .addCallback(sRoomDatabaseCallback)
            .build()

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                instance?.let { PopulateDbAsync(it).execute() }
            }
        }
    }

    /**
     * Populate the database in the background.
     */
    private class PopulateDbAsync internal constructor(db: WordRoomDatabase) : AsyncTask<Void, Void, Void>() {

        private val mDao: WordDao = db.wordDao()
        internal var words = arrayOf("dolphin", "crocodile", "cobra")

        override fun doInBackground(vararg params: Void): Void? {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            if (mDao.getAnyWord().isEmpty()){
                for (i in 0 until words.size) {
                    val word = Word(0, words[i])
                    mDao.insert(word)
                }
            }


            return null
        }
    }

}