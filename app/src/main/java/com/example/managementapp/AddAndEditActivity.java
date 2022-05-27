package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.managementapp.databinding.ActivityAddAndEditBinding;
import com.example.managementapp.databinding.ActivityMainBinding;
import com.example.managementapp.model.entity.Course;

public class AddAndEditActivity extends AppCompatActivity {

    private Course course;
    public static final String COURSE_ID="courseId";
    public static final String COURSE_NAME="courseName";
    public static final String UNIT_PRICE="UnitPrice";
    private ActivityAddAndEditBinding activityAddAndEditBinding;
    private AddAndEditActivityClickHandler clickHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);

        course=new Course();
        activityAddAndEditBinding= DataBindingUtil.setContentView(this,
                R.layout.activity_add_and_edit);
        activityAddAndEditBinding.setCourse(course);

        clickHandler= new AddAndEditActivityClickHandler(this);
        activityAddAndEditBinding.setClickHandler(clickHandler);

        Intent i =getIntent();
        if(i.hasExtra(COURSE_ID)){
            setTitle("Edit Course");
            course.setCourseName(i.getStringExtra(COURSE_NAME));
            course.setCourseUnitPrice(i.getStringExtra(UNIT_PRICE));
        }else{
            setTitle("Create New Course");
        }


    }

    public class AddAndEditActivityClickHandler{
        Context context;

        public AddAndEditActivityClickHandler(Context context) {
            this.context = context;
        }

        public void onSubmitButtonClicked(View view){
            if (course.getCourseName()==null){
                Toast.makeText(context, "Please enter Course Name", Toast.LENGTH_SHORT).show();
            }else{
                Intent i=new Intent();
                i.putExtra(COURSE_NAME,course.getCourseName());
                i.putExtra(UNIT_PRICE,course.getCourseUnitPrice());
                setResult(RESULT_OK,i);
                finish();
            }

        }
    }
}