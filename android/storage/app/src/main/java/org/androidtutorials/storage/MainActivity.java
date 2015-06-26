
package org.androidtutorials.storage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.androidtutorials.storage.sql.DatabaseHandler;
import org.androidtutorials.storage.sql.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
/**
 * This demo application shows four different ways of making data persistent:
 * (1) Shared preferences, (2) Writing to external storage, (3) SQL database,
 * (4) Cloud storage. Each self-contained example is shown in a separate Java
 * method.
 */
public class MainActivity extends Activity {

    final static public String TAG = "storage";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        storeToSharedPreferences();
        storeToSDCard();
        storeToSQL();
        storeToParse();
    }

    /**
     * Android offers SharedPreferences as a persistence mechanism to store
     * simple key/value pairs. Keys are of type string and values can be any
     * primitive Java type. SharedPreferences are not appropriate to store
     * complex data such as Java objects. A few things to consider:
     * SharedPreferences do not require any special permission.
     * SharedPreferences are automatically deleted when the corresponding
     * application is un-installed from the device. SharedPreferences can be
     * made private so that only the application that created them can read
     * them.
     */
    private void storeToSharedPreferences() {
        /**
         * Retrieve the shared preferences for "Settings". The name is arbitrary
         * and is equivalent to a SQL table name where the key/value pairs are
         * stored. Getting the shared preferences via MODE_PRIVATE will
         * guarantee that no other application has access to the key/value
         * pairs.
         */
        SharedPreferences prefs = this.getSharedPreferences("Settings", MODE_PRIVATE);

        /**
         * Read a shared preference with key "COUNT". If the key does not yet
         * exist, return 0 as the default value.
         */
        int count = prefs.getInt("COUNT", 0);
        Log.d(TAG, "storeToSharedPreferences: " + count);

        /**
         * The following three lines show how to modify a key/value pair via a
         * so-called Editor.
         */
        Editor editor = prefs.edit();
        editor.putInt("COUNT", count++);
        editor.commit();
    }

    /**
     * Data can be made persistent on the devices' external storage (usually a
     * SD card). Since Android is based on Linux, this basically amounts to
     * reading and writing data to a regular file. Android its own file-system
     * layout and method Environment.getExternalStorageDirectory() should be
     * used to retrieve the directory where data can be safely stored. Apart
     * from this detail, reading and writing to files can be done with the
     * regular Java file I/O API. After running this app, you can verify via:
     * 
     * <pre>
     * adb shell
     * cd /sdcard/StorageDemo
     * cat person.txt
     * </pre>
     * 
     * A few things to consider: The application will need permission
     * WRITE_EXTERNAL_STORAGE. Any application installed on the device can read
     * (and over-write) the data. When the application is un-installed from the
     * device, the data it has previously stored on the SD card is *not*
     * removed.
     */
    private void storeToSDCard() {
        String BASE_DIR = "/StorageDemo/";
        String DOC_FILE = "person.txt";

        /**
         * Get the base directory to which we have write permission.
         */
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();

        File docFile = new File(new File(sdcardPath, BASE_DIR), DOC_FILE);

        /**
         * If the file already exists, do nothing.
         */
        if (docFile.exists()) {
            return;
        }

        /**
         * Make sure the parent directory exists.
         */
        File parentFile = docFile.getParentFile();
        parentFile.mkdirs();

        /**
         * Write some data to the file using the regular Java file I/O.
         */
        try {
            OutputStream os = new FileOutputStream(docFile);
            Writer writer = new OutputStreamWriter(os);
            writer.write("Mickey Mouse");
            writer.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show some read/write access to an internal SQL database. The following
     * method shows how to use the type-safe interface to an employee database.
     * The main implementation that interfaces with the SQL database can be
     * found in classes DatabaseHandler and Employee. Using the SQL database
     * does not require any permissions. Also, when the app is un-installed, the
     * SQL databases it created will automatically be deleted as well. Note that
     * with each execution of this app two employees will be added. After two
     * runs the database will contain duplicates.
     */
    private void storeToSQL() {
        DatabaseHandler db = new DatabaseHandler(this);
        Employee mickey = new Employee("Mickey", "Mouse", 42);
        Employee donald = new Employee("Donald", "Duck", 48);
        db.addEmployee(mickey);
        db.addEmployee(donald);
        int numEmployees = db.getEmployeeCount();
        Log.d(TAG, "storeToSQL: numEmployees=" + numEmployees);
        Employee employee = db.getEmployeeByLastName("Duck");
        Log.d(TAG, "storeToSQL: name=" + employee.getName());
        db.close();
    }

    /**
     * www.parse.com offers free cloud storage that suffices for low-volume
     * apps. Their SDK handles all the communication with the backend and is a
     * very convenient way to store data in the cloud. The following method
     * shows how to save and retrieve data from Parse's cloud service. This
     * tutorial uses a demo authentication key (see class StorageApplication)
     * that enables the app to run. However, it is suggested that you create
     * your own account at parse.com and use your own authentication key. This
     * will enable you to log in to Parse's web page and use the dashboard to
     * see the data you have pushed to the cloud.
     */
    private void storeToParse() {
        /**
         * Create a new ParseObject and push it to the cloud. The name of the
         * ParseObject "Employee" corresponds to a SQL table and the keys of the
         * various key/value pairs correspond to column names. Parse will
         * automatically add ParseObject instances to the appropriate table.
         */
        ParseObject employee = new ParseObject("Employee");
        employee.put("firstName", "Mickey");
        employee.put("lastName", "Mouse");
        employee.put("age", 42);
        employee.saveInBackground();

        employee = new ParseObject("Employee");
        employee.put("firstName", "Donald");
        employee.put("lastName", "Duck");
        employee.put("age", 48);
        employee.saveInBackground();

        /**
         * The following code sniplet shows how to perform a query. Note that
         * the query runs asynchronously, i.e., it will take some time for the
         * query to complete running. The query will show the first employee
         * whose last name is "Duck".
         */
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Employee");
        query.whereEqualTo("lastName", "Duck");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> employees, ParseException e) {
                if (e == null) {
                    if (employees.size() > 0) {
                        ParseObject employee = employees.get(0);
                        Log.d(TAG, "storeToParse: firstName=" + employee.getString("firstName"));
                        Log.d(TAG, "storeToParse: lastName =" + employee.getString("lastName"));
                        Log.d(TAG, "storeToParse: age      =" + employee.getInt("age"));
                    } else {
                        Log.d(TAG, "storeToParse: No employees found!");
                    }
                } else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }
}
