package com.example.arthur.contentprovidercontacts;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Cursor extends AppCompatActivity {

    private SimpleCursorAdapter adapter;
    private Bundle savedInstanceState;

    private void setupCursorAdapter() {
        String[] uiBindFrom = { ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI };
        int[] uiBindTo = { R.id.tvName, R.id.ivImage };
        adapter = new SimpleCursorAdapter(
                this, R.layout.item_contact,
                null, uiBindFrom, uiBindTo,
                0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupCursorAdapter();
        ListView lvContacts = (ListView) findViewById(R.id.lvContacts);
        lvContacts.setAdapter(adapter);
    }

    private LoaderManager.LoaderCallbacks<Cursor> contactsLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {

                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    // Define the columns to retrieve
                    String[] projectionFields = new String[] { ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.Contacts.PHOTO_URI };
                    // Construct the loader
                    CursorLoader cursorLoader = new CursorLoader(Cursor.this,
                            ContactsContract.Contacts.CONTENT_URI, // URI
                            projectionFields, // projection fields
                            null, // the selection criteria
                            null, // the selection args
                            null // the sort order
                    );
                    // Return the loader for use
                    return cursorLoader;
                }


                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    // The swapCursor() method assigns the new Cursor to the adapter
                    adapter.swapCursor((android.database.Cursor) cursor);
                }

            .
                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    // Clear the Cursor we were using with another call to the swapCursor()
                    adapter.swapCursor(null);
                }
            };

    public static final int CONTACT_LOADER_ID = 78; // From docs: A unique identifier for this loader. Can be whatever you want.

    @Override
    protected void onCreate(Bundle savedInstanceState){

        setupCursorAdapter();
        getSupportLoaderManager().initLoader(CONTACT_LOADER_ID,
                new Bundle(), (android.support.v4.app.LoaderManager.LoaderCallbacks<Object>) contactsLoader);
    }


}
