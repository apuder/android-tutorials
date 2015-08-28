package org.androidtutorials.provider;

import android.app.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This tutorial demonstrates a ContentProvider. The data that the
 * ContentProvider manages is identical to that from the "Storage" tutorial: a
 * set of employees with their first and last name as well as age. The
 * ContentProvider is accessible via the URI
 * content://org.androidtutorials.provider.Employees/. This activity shows how
 * to insert, delete and retrieve employees via the regular ContentProvider
 * interface. Note that the ContentProvider is also accessible from other
 * Android applications.
 */
public class MainActivity extends Activity {

    private EditText firstName;
    private EditText lastName;
    private EditText age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * Get references to first, last name and age entry fields.
         */
        firstName = (EditText) findViewById(R.id.firstname);
        lastName = (EditText) findViewById(R.id.lastname);
        age = (EditText) findViewById(R.id.age);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void deleteAllEmployees(View view) {
        /*
         * Delete all employees.
         */
        int count = getContentResolver().delete(EmployeeProvider.CONTENT_URI, null, null);
        String countNum = "" + count + " records were deleted.";
        Toast.makeText(this, countNum, Toast.LENGTH_LONG).show();
    }

    public void addEmployee(View view) {
        /*
         * Retrieve first, last name and age from the UI and store them in a
         * ContentValues instance.
         */
        ContentValues values = new ContentValues();
        values.put(EmployeeProvider.FIRSTNAME, firstName.getText().toString());
        values.put(EmployeeProvider.LASTNAME, lastName.getText().toString());
        int age_ = 0;
        try {
            age_ = Integer.parseInt(age.getText().toString());
        } catch (NumberFormatException ex) {
        }
        values.put(EmployeeProvider.AGE, age_);

        /*
         * Insert a new employee and show the resulting URI in a toast message.
         */
        Uri uri = getContentResolver().insert(EmployeeProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(), "Added: " + uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void showAllEmployees(View view) {
        /*
         * Query the ContentProvider. The result is a cursor that allows to
         * iterate over the results.
         */
        Cursor c = getContentResolver().query(EmployeeProvider.CONTENT_URI, null, null, null,
                EmployeeProvider.LASTNAME);
        String result = "";

        if (!c.moveToFirst()) {
            Toast.makeText(this, "No employees found!", Toast.LENGTH_LONG).show();
        } else {
            /*
             * Iterate over all employees and construct one big string that will
             * be shown in a toast message.
             */
            do {
                result = result + "\n" + c.getString(c.getColumnIndex(EmployeeProvider.FIRSTNAME))
                        + " " + c.getString(c.getColumnIndex(EmployeeProvider.LASTNAME))
                        + " with id " + c.getString(c.getColumnIndex(EmployeeProvider.ID));
            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
        c.close();
    }
}