package org.androidtutorials.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * This ContentProvider manages a similar data model than the one used in the
 * "Storage" tutorial, namely the first name, last name and age of employees.
 * Just like the "Storage" tutorial, employees are stored in an underlying
 * SQLite database. The code shown here adds the necessary API to expose that
 * data via a ContentProvider so that it becomes accessible from other
 * applications via a ContentResolver.
 */
public class EmployeeProvider extends ContentProvider {

    /*
     * The authority portion of the CONTENT_URI.
     */
    private static final String     AUTHORITY_NAME   = "org.androidtutorials.provider.Employees";

    /*
     * The content URI by which this ContentProvider can be accessed. By
     * convention, this is stored in a static final member called CONTENT_URI.
     * The content URI always has the scheme "content://". In this exmaple, the
     * full content URI is:
     * content://org.androidtutorials.provider.Employees/employees
     */
    public static final Uri         CONTENT_URI;

    /*
     * These are the column names of the SQL database. They are also used by
     * clients of the ContentProvider to access specific information from an
     * employee via a ContentResolver. The column names are used to define the
     * projection of the query.
     */
    public static final String      ID               = "id";
    public static final String      FIRSTNAME        = "firstName";
    public static final String      LASTNAME         = "lastName";
    public static final String      AGE              = "age";

    private static final int        DATABASE_VERSION = 1;
    private static final String     DATABASE_NAME    = "MyProviderData";
    private static final String     TABLE_EMPLOYEE   = "employees";

    /*
     * An UriMatcher is used to efficiently parse content URIs. The following
     * integer values are used to identify different URL patterns of the content
     * URI. EMPLOYEES represents all employees in the database while EMPLOYEE_ID
     * represents a specific employee (see the initialization of uriMatcher
     * below).
     */
    private static final int        EMPLOYEES        = 1;
    private static final int        EMPLOYEE_ID      = 2;

    private static final UriMatcher uriMatcher;

    static {
        CONTENT_URI = Uri.parse("content://" + AUTHORITY_NAME + "/employees");

        /*
         * Define a helper URI matcher that can distinguish between different
         * URI patterns. In particular, we need to distinguish between all
         * employees and a specific employee. The latter is identified by
         * appending "/" and a number that represents the ID of an employee to
         * the URI.
         */
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY_NAME, "employees", EMPLOYEES);
        uriMatcher.addURI(AUTHORITY_NAME, "employees/#", EMPLOYEE_ID);
    }


    /**
     * Class DatabaseHandler creates a similar schema to the one already
     * discussed in the "Storage" tutorial. The difference is that the
     * "employees" table also has a primary key of type integer. That integer
     * becomes the ID that is appended to the content URI in order to reference
     * a particular employee instance.
     */
    static private class DatabaseHandler extends SQLiteOpenHelper {

        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TAGS_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE + "(" + ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIRSTNAME + " TEXT NOT NULL,"
                    + LASTNAME + " TEXT NOT NULL," + AGE + " INTEGER" + ")";
            db.execSQL(CREATE_TAGS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
            onCreate(db);
        }
    }


    private SQLiteDatabase database;


    /**
     * onCreate() is called when the ContentProvider is first created.
     */
    @Override
    public boolean onCreate() {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        database = dbHandler.getWritableDatabase();
        return database != null;
    }

    /**
     * shutdown() is called when the ContentProvider is shut down.
     */
    @Override
    public void shutdown() {
        super.shutdown();
        if (database != null) {
            database.close();
        }
    }

    /**
     * query() is called whenever a client performs a query on this particular
     * ContentProvider. The parameters "projection" et al are the usual SQL
     * projection and search parameters.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_EMPLOYEE);

        switch (uriMatcher.match(uri)) {
        case EMPLOYEES:
            /*
             * Search through all employees. Nothing needs to be done to
             * queryBuilder.
             */
            break;
        case EMPLOYEE_ID:
            /*
             * Retrieve a specific employee. The employee's ID is the last path
             * segment of the URI. Add a "WHERE ID = ?" clause to the
             * queryBuilder.
             */
            queryBuilder.appendWhere(ID + "=" + uri.getLastPathSegment());
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder.equals("")) {
            /*
             * Sort results by LASTNAME by default.
             */
            sortOrder = LASTNAME;
        }

        /*
         * Get the cursor for this query and return it to the caller.
         */
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null,
                null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert a new employee into the underlying SQLite database.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = database.insert(TABLE_EMPLOYEE, "", values);

        if (row > 0) {
            /*
             * Insert was successful. Return the new URI as the result.
             */
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    /**
     * Update the employee database. The update can either be performed for all
     * employees or just one depending on the content URI.
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)) {
        case EMPLOYEES:
            /*
             * Update all employees. This will set the same first name, last
             * name and/or age for all employees based on the selection.
             * Admittedly, this might not make that much sense for the employee
             * database, but is still shown here as an example. :)
             */
            count = database.update(TABLE_EMPLOYEE, values, selection, selectionArgs);
            break;
        case EMPLOYEE_ID:
            /*
             * Update just one employee. The query is augmented by checking for
             * the correct ID.
             */
            count = database.update(TABLE_EMPLOYEE, values, ID + " = " + uri.getLastPathSegment()
                    + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
                    selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        /*
         * Return the number of employees that were updated.
         */
        return count;
    }

    /**
     * Delete either all or just one employee.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)) {
        case EMPLOYEES:
            /*
             * Delete all employees from the database.
             */
            count = database.delete(TABLE_EMPLOYEE, selection, selectionArgs);
            break;
        case EMPLOYEE_ID:
            /*
             * Delete a specific employee. Change the query by appending the ID
             * as an additional search parameter.
             */
            String id = uri.getLastPathSegment();
            count = database.delete(TABLE_EMPLOYEE,
                    ID + " = " + id
                            + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
                    selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        /*
         * Return the number of employees that were deleted.
         */
        return count;
    }

    /**
     * Returns a vendor-specific MIME type for a given URI. The prefixes of the
     * MIME types for all employees (vnd.android.cursor.dir) and a single
     * employee (vnd.android.cursor.item) are defined by Android.
     */
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
        case EMPLOYEES:
            /*
             * All employees
             */
            return "vnd.android.cursor.dir/vnd.androidtutorials.employees";
        case EMPLOYEE_ID:
            /*
             * A particular employee
             */
            return "vnd.android.cursor.item/vnd.androidtutorials.employees";
        default:
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}