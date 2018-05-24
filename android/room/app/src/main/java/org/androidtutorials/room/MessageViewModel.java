package org.androidtutorials.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by sp on 5/23/18.
 */

public class MessageViewModel extends AndroidViewModel {
    private static final String TAG = "MessageViewModel";

    private DataRepository mRepository;
    private LiveData<List<Message>> mAllMessages;

    public MessageViewModel (Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mAllMessages = mRepository.getAllMessages();
    }

    public LiveData<List<Message>> getAllMessages() { return mAllMessages; }
    public LiveData<List<Message>> getMessage(final int messageId) { return mRepository.loadMessage(messageId); }

    public void insert(Message message) { mRepository.insert(message); }
}
