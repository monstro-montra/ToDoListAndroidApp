package com.javierlabs.to_dolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ToDoList mToDoList;
    private EditText mItemEditText;
    private TextView mItemListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItemEditText = findViewById(R.id.todo_item);
        mItemListTextView = findViewById(R.id.item_list);

        findViewById(R.id.add_button).setOnClickListener(view -> addButtonClick());
        findViewById(R.id.clear_button).setOnClickListener(view -> clearButtonClick());

        mToDoList = new ToDoList(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try{
            mToDoList.readFromFile();
            displayList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            //save list for later
            mToDoList.saveToFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void addButtonClick() {
        //ignore leading or trailing spaces
        String item=mItemEditText.getText().toString().trim();

        //clear EditText so it's ready for another item
        mItemEditText.setText("");

        //add item to the list and display
        if (item.length() > 0) {
            mToDoList.addItem(item);
            displayList();
        }
    }

    private void displayList() {
        //Display numbered list of items
        StringBuffer itemText = new StringBuffer();
        String[] items = mToDoList.getItems();
        for(int i = 0; i < items.length; i++) {
            itemText.append(i + 1)
                    .append(". ")
                    .append(items[i])
                    .append("\n");
        }

        mItemListTextView.setText(itemText);
    }

    private void clearButtonClick() {
        mToDoList.clear();
        displayList();
    }
 }