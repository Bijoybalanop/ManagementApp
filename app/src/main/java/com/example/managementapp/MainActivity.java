package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.managementapp.databinding.ActivityMainBinding;
import com.example.managementapp.databinding.ActivityMainBindingImpl;
import com.example.managementapp.model.entity.Category;
import com.example.managementapp.model.entity.Course;
import com.example.managementapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Category> categoryList;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandler clickHandler;
    private Category selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityViewModel= new ViewModelProvider(this).get(MainActivityViewModel.class);

        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        clickHandler=new MainActivityClickHandler();
        activityMainBinding.setClickHandler(clickHandler);



        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList=(ArrayList<Category>)  categories;


                for (Category c:categories){
                    Log.i("TAG",c.getCategoryName());
                }

                showOnSpinner();
            }
        });
        mainActivityViewModel.getCoursesOfSelectedCategories(1).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                for(Course c:courses)
                Log.i("TAG",c.getCourseName());
            }
        });

    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter =new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                categoryList
        );
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);

    }


    public class MainActivityClickHandler{

        public void onFABClicked(View view){

            Toast.makeText(MainActivity.this, "FAB Clicked ", Toast.LENGTH_SHORT).show();
        }

        public void OnSelectedItem(AdapterView<?> parent,View view,int pos,long id){

            selectedCategory=(Category) parent.getItemAtPosition(pos);
             String message ="id is :"+selectedCategory.getCategoryId()+
                     "\n name is"+selectedCategory.getCategoryName();
            Toast.makeText(parent.getContext(), message, Toast.LENGTH_SHORT).show();

        }


    }
}