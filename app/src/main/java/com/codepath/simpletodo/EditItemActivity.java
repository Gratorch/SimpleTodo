package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.codepath.simpletodo.MainActivity.ITEM_POSITION;
import static com.codepath.simpletodo.MainActivity.ITEM_TEXT;

public class EditItemActivity extends AppCompatActivity {

    // track edit text
    EditText etItemText;
    //Position of edited item on list
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edititem);

        //resolve edit text from layout
        etItemText=(EditText)findViewById(R.id.editItemText);
        //set edit value text from intent extra
        etItemText.setText(getIntent().getStringExtra(ITEM_TEXT));
        //update position from intent extra
        position=getIntent().getIntExtra(ITEM_POSITION,0);
        //update the title bar of the activity
        getSupportActionBar().setTitle("Edit item ");
    }

    //handler for save bitton
    public void onSaveItem(View view){
        //prepare new intent for result
        Intent i=new Intent();
        //pass update item as extrs
        i.putExtra(ITEM_TEXT,etItemText.getText().toString());
        //pass original position as extra
        i.putExtra(ITEM_POSITION,position);
        //set the intent for result
        setResult(RESULT_OK,i);
        //close the activity and return to the main
        finish();
    }
}
