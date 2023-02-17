package com.javierlabs.to_dolist;

//imports
import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    public static final String FILENAME = "todolist.txt";
    private Context mContext;
    private List<String> mTaskList;

    //constructor
    public ToDoList(Context context){
        mContext = context;
        mTaskList = new ArrayList<>();
    }

    //add item to list
    public void addItem(String item) throws IllegalArgumentException{
        mTaskList.add(item); //add item to mTaskList
    }

    //retrieve items as an array
    public String[] getItems() {
        String[] items = new String[mTaskList.size()];
        return mTaskList.toArray(items); //return items from mTaskList as an array
    }

    //clear all items
    public void clear(){
        mTaskList.clear();
    }

    //save the list to a file
    public void saveToFile() throws IOException {
        //write to internal storage (saving)

        FileOutputStream os = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        writeListToStream(os);
    }

    public void readFromFile() throws IOException {
        //read list from file in internal storage
        FileInputStream is = mContext.openFileInput(FILENAME);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            mTaskList.clear();

            String line;
            while ((line = reader.readLine()) != null ){
                mTaskList.add(line);
            }
        } catch (FileNotFoundException ex) {
            //do nothing
        }
    }

    private void writeListToStream(FileOutputStream os) { //private support method for saveToFile
        PrintWriter writer = new PrintWriter(os);
        for(String item : mTaskList){
            writer.println(item);
        }
        writer.close();
    }
}

