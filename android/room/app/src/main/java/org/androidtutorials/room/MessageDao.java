package org.androidtutorials.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by sp on 5/23/18.
 */

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages")
    LiveData<List<Message>> getAll();

    @Query("SELECT * FROM messages")
    List<Message> getAllSync();

    @Query("SELECT * FROM messages WHERE id = :id LIMIT 1")
    LiveData<List<Message>> get(int id);

    @Query("SELECT * FROM messages WHERE id IN (:messageIds)")
    List<Message> loadAllByIds(int[] messageIds);

    @Query("SELECT * FROM messages WHERE title LIKE :title LIMIT 1")
    Message findByTitle(String title);

    @Query("SELECT * FROM messages WHERE id = :id LIMIT 1")
    Message findById(int id);

    // @TODO: Test date comparison
    @Query("SELECT * FROM messages WHERE created_date < :someDate")
    public Message[] loadAllMessagesOlderThan(String someDate);

    @Query("SELECT * FROM messages WHERE title LIKE :search ")
    public List<Message> findMessagesByTitle(String search);


    // If the @Insert method receives only 1 parameter, it can return a long,
    // which is the new rowId for the inserted item. If the parameter is an
    // array or a collection, it should return long[] or List<Long> instead.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertMessages(Message... messages);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertMessage(Message text);

    @Insert
    public long insert(Message text);

    // Although usually not necessary, you can have this method return an int
    // value instead, indicating the number of rows updated in the database.
    @Update
    public void updateMessages(Message... messages);

    @Delete
    void delete(Message text);

    // Although usually not necessary, you can have this method return an int
    // value instead, indicating the number of rows deleted in the database.
    @Delete
    void deleteAll(Message... messages);
}
