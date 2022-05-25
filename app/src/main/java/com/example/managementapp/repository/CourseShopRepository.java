package com.example.managementapp.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.managementapp.model.CourseDatabase;
import com.example.managementapp.model.dao.CategoryDAO;
import com.example.managementapp.model.dao.CourseDAO;
import com.example.managementapp.model.entity.Category;
import com.example.managementapp.model.entity.Course;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CourseShopRepository {
    private CategoryDAO categoryDAO;
    private CourseDAO courseDAO;

    private LiveData<List<Category>> categories;
    private LiveData<List<Course>> courses;

    public CourseShopRepository(Application application) {
        CourseDatabase courseDatabase = CourseDatabase.getInstance(application);
        categoryDAO = courseDatabase.categoryDAO();
        courseDAO = courseDatabase.courseDAO();
    }
    //Getters and setters


    public LiveData<List<Category>> getCategories() {
        return categoryDAO.getAllCategories();
    }

    public LiveData<List<Course>> getCourses(int CategoryId) {
        return courseDAO.getCourses(CategoryId);
    }

    public void insertCategory(Category category) {
        categoryDAO.insert(category);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //inserting categories
                categoryDAO.insert(category);
            }
        });
    }

    public void insertCourse(Course course) {
        courseDAO.insert(course);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //inserting categories
                courseDAO.insert(course);
            }
        });
    }

    public void deleteCategory(Category category) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //inserting categories
                categoryDAO.delete(category);
            }
        });

    }

    public void deleteCourse(Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //inserting categories
                courseDAO.delete(course);
            }
        });
    }

    public void updateCategory(Category category) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //inserting categories
                categoryDAO.update(category);
            }
        });

    }

    public void updateCourse(Course course) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //inserting categories
                courseDAO.update(course);
            }
        });

    }


}


