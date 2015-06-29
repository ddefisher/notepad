//dedefish@ncsu.edu
package com.mycompany.notepad;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    static ArrayList<String> notesArray = new ArrayList<String>();
    static ArrayList<String> displayArray = new ArrayList<String>();
    static ListAdapter theAdapter;
    static ListView theListView;
    static int itemNumber;
    @Override
    //Get everything ready
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            FileInputStream fileInputStream = openFileInput("noteSaves");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            notesArray = (ArrayList<String>)objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //copying notes array into displayarray but only the first line
        for(String x : notesArray)
        {
            String[] beforeNewline = x.split("\n", 20);


            displayArray.add(beforeNewline[0]);
        }

        theAdapter= new ArrayAdapter<String>(this,R.layout.list_green_text,
                R.id.list_content,displayArray);

        theListView = (ListView)findViewById(R.id.listView);
        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                itemNumber = position;
                Intent intent = new Intent(MainActivity.this, ENote.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_new) {

            Intent intent = new Intent(this, NewNote.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
