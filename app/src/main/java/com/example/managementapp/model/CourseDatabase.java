package com.example.managementapp.model;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.managementapp.model.dao.CategoryDAO;
import com.example.managementapp.model.dao.CourseDAO;
import com.example.managementapp.model.entity.Category;
import com.example.managementapp.model.entity.Course;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Course.class, Category.class},version = 1)
public abstract class CourseDatabase extends RoomDatabase {
    public abstract CategoryDAO categoryDAO();
    public abstract CourseDAO courseDAO();

    //singleton pattern for DB

    private static CourseDatabase instance;
    public static synchronized CourseDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    CourseDatabase.class,
                    "courses_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    //call back
    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            initializeData();
            //Insert data when database is created ....
        }
    };

    private static void initializeData() {
        CourseDAO courseDAO=instance.courseDAO();
        CategoryDAO categoryDAO= instance.categoryDAO();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Categories
                Category category1 =new Category();
                category1.setCategoryName("Front end");
                category1.setCategoryDescription("Web Development");

                Category category2 =new Category();
                category2.setCategoryName("BackEnd");
                category2.setCategoryDescription("Database");

                categoryDAO.insert(category1);
                categoryDAO.insert(category2);

                //Couse
                Course course1=new Course();
                course1.setCourseName("ANDROID");
                course1.setCourseUnitPrice("2000");
                course1.setCategoryId(1);

                Course course2=new Course();
                course2.setCourseName("C++");
                course2.setCourseUnitPrice("2500");
                course2.setCategoryId(1);

                Course course3=new Course();
                course3.setCourseName("JAVA");
                course3.setCourseUnitPrice("500");
                course3.setCategoryId(2);

                Course course4=new Course();
                course4.setCourseName("JSON");
                course4.setCourseUnitPrice("600");
                course4.setCategoryId(2);

                courseDAO.insert(course1);
                courseDAO.insert(course2);
                courseDAO.insert(course3);
                courseDAO.insert(course4);



            }
        });

    }


}
