package com.vadimfedchuk.pillstest.ui.main.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vadimfedchuk.pillstest.ui.main.db.model.Pills;

@Database(entities = {Pills.class}, version = 1)
public abstract class PillsDatabase extends RoomDatabase {

    public abstract PillsDao getPillsDao();
    private volatile static PillsDatabase instance;

    public synchronized static PillsDatabase getAppDatabase(Context context) {
        if (instance == null) {
            synchronized (PillsDatabase.class) {
                if (instance == null) {
                    instance =
                            Room.databaseBuilder(context.getApplicationContext(), PillsDatabase.class, "pills-database")
                                    .allowMainThreadQueries()
                                    .build();
                }
            }
        }
        return instance;
    }
}
