package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // a numeric code to identity the edit activity
    public final static int EDIT_REQUEST_CODE=20;
    //Key used for passing data between activities
    public final static String ITEM_TEXT="itemText";
    public final static String ITEM_POSITION="itemPosition";

    ArrayList<String> items;
    ArrayAdapter<String>itemsAdapter;
    ListView lvtItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //items= new ArrayList<String>();
        readItem();
        itemsAdapter=new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, items);
        lvtItems=(ListView)findViewById(R.id.lvtItems);
        lvtItems.setAdapter(itemsAdapter);

        //Mook data
        //items.add("First item");
        //items.add("Second item");

        setUpListViewListener();
    }

    public void onAddItem(View v){
        EditText etNewItem=(EditText)findViewById(R.id.etNewItem);
        String itemText=etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        Toast.makeText(getApplicationContext(),"Itemm add to list",Toast.LENGTH_SHORT).show();
    }

    public void setUpListViewListener(){
        Log.i("MainActivity","Setting up listener on list view");
        lvtItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.i("MainActivity","Item remove from list view" + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        //set up item  listener for edit(regular click)
        lvtItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //create the new activity
                Intent i =new Intent(MainActivity.this, EditItemActivity.class);
                //pass the data being edit
                i.putExtra(ITEM_TEXT, items.get(position));
                i.putExtra(ITEM_POSITION, position);
                //display the activity
                startActivityForResult(i,EDIT_REQUEST_CODE);
            }
        });
    }

    //handle result from activity


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if the activity completed ok
        if(resultCode==RESULT_OK && requestCode==EDIT_REQUEST_CODE){
            //
            String updateItem=data.getExtras().getString(ITEM_TEXT);
            //
            int position=data.getExtras().getInt(ITEM_POSITION);
            // extract original position of edited item
            items.set(position,updateItem);
            // update the model with the new item text at the edit position
            itemsAdapter.notifyDataSetChanged();
            // persist the change model
            writeItem();
            //notify the user the operation completed ok
            Toast.makeText(this,"Update succesfuly ",Toast.LENGTH_SHORT).show();
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(),"todo.txt");
    }


    private  void readItem(){
        try {
            items=new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException ee){
            Log.i("MainActivity","Error reanding file",ee);
            items=new ArrayList<>();
        }
    }

    private  void writeItem(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        }
        catch (IOException ee){
            Log.i("MainActivity","Error writing file",ee);
        }
    }

}



















