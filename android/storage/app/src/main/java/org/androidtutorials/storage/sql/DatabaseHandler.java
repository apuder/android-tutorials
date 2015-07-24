package org.androidtutorials.storage.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class DatabaseHandler provides type-safe access to a SQL database that
 * manages a set of employees. Each employee (i.e., a row of the SQL database
 * table) is characterized by a first name, last name and the age of the
 * employee. Public methods of this class help to add and lookup employees.
 * After running the app, Android will create the database in a private folder
 * for this app that can be inspected with adb:
 * 
 * <pre>
 * adb shell
 * cd /data/data/org.androidtutorials.storage/databases
 * ls -l
 * </pre>
 * 
 * You should see a file called 'MyAppData'. Note that folder name
 * org.xmlvm.tutorial.android.storage corresponds to the app id mentioned in
 * AndroidManifest.xml. Also note that accessing this file requires root and
 * will most likely only be possible on the Android emulator and not a real
 * device.
 * 
 * Note that file MyAppData is a regular SQLite database and can be inspected
 * from the command line via the sqlite3 tool. As a continuation of the above
 * shell commands, do the following:
 * 
 * <pre>
 * sqlite3 MyAppData
 * # This will enter the SQLite interactive tool
 * .tables
 * select * from employees;
 * </pre>
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION = 1;

    private static final String DATABASE_NAME    = "MyAppData";

    private static final String TABLE_EMPLOYEE   = "employees";

    private static final String KEY_FIRSTNAME    = "firstName";
    private static final String KEY_LASTNAME     = "lastName";
    private static final String KEY_AGE          = "age";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * THe database is created via the SQL CREATE command. In effect, the
     * following SQL statement is executed:
     * 
     * <pre>
     * CREATE TABLE employees (firstName TEXT NOT NULL, lastName TEXT NOT NULL, age INTEGER)
     * </pre>
     * 
     * It will create a SQL table with three columns: first name, last name and
     * age. First and last name are of type string and age is of type integer.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TAGS_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE + "(" + KEY_FIRSTNAME
                + " TEXT NOT NULL," + KEY_LASTNAME + " TEXT NOT NULL," + KEY_AGE + " INTEGER" + ")";
        db.execSQL(CREATE_TAGS_TABLE);
    }

    /**
     * When a new version of the database needs to be created, we simply delete
     * the old version of the database (via the SQL statement DROP TABLE) and
     * recreate it with the new schema. In real life one should upgrade the
     * database schema and migrate the data to the new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);
    }

    /**
     * Add a new employee to the database.
     */
    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, employee.getFirstName());
        values.put(KEY_LASTNAME, employee.getLastName());
        values.put(KEY_AGE, employee.getAge());
        db.insert(TABLE_EMPLOYEE, null, values);
        db.close();
    }

    /**
     * Searches the database for the first employee with a certain last name.
     * The name needs to match exactly. If no employee can be found, null is
     * returned.
     */
    public Employee getEmployeeByLastName(String lastName) {
        SQLiteDatabase db = this.getReadableDatabase();

        /**
         * Create a cursor that allows us to iterate over all table rows that
         * match the query. The second parameter of method query() is the
         * so-called selection (i.e., the columns that should be returned).
         */
        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[] { KEY_FIRSTNAME, KEY_LASTNAME,
                KEY_AGE }, KEY_LASTNAME + "=?", new String[] { lastName }, null, null, null, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return null;
        }
        /**
         * Return the employee. The indices of the cursor correspond to the
         * selection when querying the database.
         */
        Employee employee = new Employee(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
        cursor.close();
        db.close();
        return employee;
    }

    /**
     * Count the number of employees currently stored in the database via the
     * SQL statement:
     * 
     * <pre>
     * SELECT * FROM employee
     * </pre>
     */
    public int getEmployeeCount() {
        String countQuery = "SELECT * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}
