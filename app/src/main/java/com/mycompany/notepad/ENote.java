package com.mycompany.notepad;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

//Activity for a old note being opened

public class ENote extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_note);

        //Set the editText with text from notesArray that is text from a old note
        EditText editText = (EditText)findViewById(R.id.text_editor2);
        editText.setText(MainActivity.notesArray.get(MainActivity.itemNumber));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu2) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_e_note, menu2);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings2) {
            return true;
        }
        if (id == R.id.action_discardNote2) {

            MainActivity.notesArray.remove(MainActivity.itemNumber);
            MainActivity.displayArray.remove(MainActivity.itemNumber);

            MainActivity.theListView.setAdapter(MainActivity.theAdapter);

            try {
                FileOutputStream fileOutputStream = openFileOutput("noteSaves", Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                objectOutputStream.writeObject(MainActivity.notesArray);
                objectOutputStream.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finish();
        }
        if (id == R.id.action_saveNote2) {


            //Get text from user input in the EditText and add it to the listview using adapter
            EditText editText = (EditText)findViewById(R.id.text_editor2);
            String theText = (String)editText.getText().toString();

            //set updated note to notes Array
            MainActivity.notesArray.set(MainActivity.itemNumber, theText);

            //set updated note up to newline to display array
            String[] beforeNewline = theText.split("\n", 20);
            MainActivity.displayArray.set(MainActivity.itemNumber,beforeNewline[0]);

            //update the listview on main activity
            MainActivity.theListView.setAdapter(MainActivity.theAdapter);

            //write notesArray to memory
            try {
                FileOutputStream fileOutputStream = openFileOutput("noteSaves", Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                objectOutputStream.writeObject(MainActivity.notesArray);
                objectOutputStream.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            finish();

        }



        return super.onOptionsItemSelected(item);
    }
}
