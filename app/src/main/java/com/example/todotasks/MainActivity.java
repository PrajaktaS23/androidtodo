package com.example.todotasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> toDoList;
    ArrayAdapter<String>  arrayAdapter;
    ListView listView;
    EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        arrayAdapter=new ArrayAdapter<>(this,R.layout.list_view_layout,toDoList);
        listView=findViewById(R.id.id_list_view);



        listView.setAdapter(arrayAdapter);

      listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              TextView textView=(TextView)view;
              textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

          }



      });
        editText=findViewById(R.id.id_edit_text);



    }
    private  void saveData()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("shared preferances",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(toDoList);
        editor.putString("task list",json);
        editor.apply();

    }

    private void loadData()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("shared preferances",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("task list",null);
        Type type=new TypeToken<ArrayList<String>>(){}.getType();
        toDoList=gson.fromJson(json,type);


        if (toDoList == null){
            toDoList=new ArrayList<>();
        }

    }
    public void  addItemToList(View view)
    {
        toDoList.add(editText.getText().toString());
        arrayAdapter.notifyDataSetChanged();

        editText.setText("");
        saveData();
    }
}
