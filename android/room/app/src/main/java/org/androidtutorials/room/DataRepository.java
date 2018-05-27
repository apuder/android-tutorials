package org.androidtutorials.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by sp on 5/23/18.
 */

public class DataRepository {

    private final AppDatabase mDb;
    private MessageDao mMessageDao;
    private LiveData<List<Message>> mAllMessages;

    DataRepository(Application application) {
        mDb = AppDatabase.getDatabase(application);
        mMessageDao = mDb.getMessageDao();

        mAllMessages = mMessageDao.getAll();
    }

    /**
     * Get the list of messages from the database and get notified when the data changes.
     */
    public LiveData<List<Message>> getAllMessages() {
        return mMessageDao.getAll();
    }
    public LiveData<List<Message>> loadMessage(final int messageId) {
        return mMessageDao.get(messageId);
    }

    /**
     * Insert async - Message
     */
    public void insert (Message message) {
        new insertMessageAsyncTask(mMessageDao).execute(message);
    }

    /**
     * The Async insert tasks
     */
    private static class insertListAsyncTask extends AsyncTask<List<Object>, Void, Void> {

        private static final String TAG = "DataRepository";

        private MessageDao mAsyncTaskMessageDao;

        insertListAsyncTask(MessageDao tdao) {
            mAsyncTaskMessageDao = tdao;
        }

        @Override
        protected Void doInBackground( List<Object>... params) {

            Message message = (Message) params[0].get(0);
            long tid = mAsyncTaskMessageDao.insert(message);

            return null;
        }
    }

    private static class insertMessageAsyncTask extends AsyncTask<Message, Void, Void> {

        private MessageDao mAsyncTaskMessageDao;

        insertMessageAsyncTask(MessageDao dao) {
            mAsyncTaskMessageDao = dao;
        }

        @Override
        protected Void doInBackground(final Message... params) {
            mAsyncTaskMessageDao.insert(params[0]);
            return null;
        }
    }
}
