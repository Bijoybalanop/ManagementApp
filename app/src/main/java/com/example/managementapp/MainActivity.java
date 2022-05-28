package com.example.managementapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.managementapp.databinding.ActivityMainBinding;
import com.example.managementapp.model.entity.Category;
import com.example.managementapp.model.entity.Course;
import com.example.managementapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_COURSE_REQUEST_CODE = 1;
    private static final int EDIT_COURSE_REQUEST_CODE = 2;
    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Category> categoryList;
    private ArrayList<Course> courseList;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandler clickHandler;
    private Category selectedCategory;
    private Course course;

    //Recycler View

    private RecyclerView courseRecyclerView;
    private CourseAdapter courseAdapter;
    private int selectedCourseId;


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

                showOnSpinnerMain();


            }
        });



        mainActivityViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                courseList=(ArrayList<Course>) courses;
                for(Course c:courses)
                {
                    Log.i("BIJOY",c.getCourseName());
                }

              showOnSpinnerBackUp();
            }
        });


      /*  mainActivityViewModel.getCoursesOfSelectedCategories(1).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                for(Course c:courses)
                Log.i("TAG",c.getCourseName());
            }
        });*/


    }


    private void showOnSpinnerMain() {
        ArrayAdapter<Category> categoryArrayAdapter =new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                categoryList
        );
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapterMain(categoryArrayAdapter);

    }
    private void showOnSpinnerBackUp() {
        ArrayAdapter<Course> courseArrayAdapter=new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                courseList
        );
        courseArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapterBackUp(courseArrayAdapter);

    }

    public void loadCoursesArrayList(int categoryId){
        mainActivityViewModel.getCoursesOfSelectedCategories(categoryId).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                courseList=(ArrayList<Course>) courses;
                LoadRecyclerView();


            }
        });

    }

    private void LoadRecyclerView() {
        courseRecyclerView=activityMainBinding.recyclerMain;
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseRecyclerView.setHasFixedSize(true);
        courseAdapter = new CourseAdapter();
        courseRecyclerView.setAdapter(courseAdapter);
        courseAdapter.setCourses(courseList);

        // EDIT COURSE
        courseAdapter.setListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                selectedCourseId=course.getCourseId();
                Intent i =new Intent(MainActivity.this,AddAndEditActivity.class);
                i.putExtra(AddAndEditActivity.COURSE_ID,selectedCourseId);
                i.putExtra(AddAndEditActivity.COURSE_NAME,course.getCourseName());
                i.putExtra(AddAndEditActivity.UNIT_PRICE,course.getCourseUnitPrice());
                startActivityForResult(i,EDIT_COURSE_REQUEST_CODE);

            }
        });

        //Delete A course
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course courseToDelete=courseList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteCourse(courseToDelete);

            }
        }).attachToRecyclerView(courseRecyclerView);


//commite

    }


    public class MainActivityClickHandler{

        public void onFABClicked(View view){
           //CREATE COURSE
           // Toast.makeText(MainActivity.this, "FAB Clicked ", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(MainActivity.this,AddAndEditActivity.class);
            startActivityForResult(i,ADD_COURSE_REQUEST_CODE);


        }

        public void OnSelectedItemsMainSpinner(AdapterView<?> parent, View view, int pos, long id){


            selectedCategory=(Category) parent.getItemAtPosition(pos);
           //  String message ="id is :"+selectedCategory.getCategoryId()+
            //        "\n name is"+selectedCategory.getCategoryName();
           //  Toast.makeText(parent.getContext(),""+message, Toast.LENGTH_SHORT).show();
            loadCoursesArrayList(selectedCategory.getCategoryId());


        }


        public void OnSelectedItemsBackUpSpinner(AdapterView<?> parent,View view,int pos,long id){

            course= (Course) parent.getItemAtPosition(pos);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int selectedCategoryId=selectedCategory.getCategoryId();
        if (requestCode==ADD_COURSE_REQUEST_CODE && resultCode==RESULT_OK){
            Course course =new Course();
            course.setCategoryId(selectedCategoryId);
            course.setCourseName(data.getStringExtra(AddAndEditActivity.COURSE_NAME));
            course.setCourseUnitPrice(data.getStringExtra(AddAndEditActivity.UNIT_PRICE));
            mainActivityViewModel.addNewCourse(course);
        }else if(requestCode==EDIT_COURSE_REQUEST_CODE && resultCode==RESULT_OK){
            Course course =new Course();
            course.setCategoryId(selectedCategoryId);
            course.setCourseName(data.getStringExtra(AddAndEditActivity.COURSE_NAME));
            course.setCourseUnitPrice(data.getStringExtra(AddAndEditActivity.UNIT_PRICE));
            course.setCourseId(selectedCourseId);
            mainActivityViewModel.updateCourse(course);

        }

    }
}