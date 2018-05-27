package org.androidtutorials.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Iterator;

/**
 * Created by sp on 5/23/18.
 */

@Database(entities = {Message.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create database, populate with dummy data, build
//                    INSTANCE = Room.inMemoryDatabaseBuilder(conmessage, AppDatabase.class).addCallback(sRoomDatabaseCallback).build();
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "message_database").addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract MessageDao getMessageDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MessageDao mMessageDao;

        PopulateDbAsync(AppDatabase db) {
            mMessageDao = db.getMessageDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // delete pre-existing dummy data if needed
            // deleteAll();
            makeDummyData();
            return null;
        }

        private void deleteAll() {
            // deleteAll if you want to delete all our dummy data on start, then repopulate again
            Iterator<Message> message = mMessageDao.getAllSync().iterator();
            while (message.hasNext()) {
                mMessageDao.deleteAll(message.next());
            }
        }

        private void makeDummyData() {
            Message message = new Message();
            message.setTitle("Happy Birthday!");
            message.setBody("Happy Birthday! You're so old now!");
            long messageId = mMessageDao.insertMessage(message);

        }
    }

}
