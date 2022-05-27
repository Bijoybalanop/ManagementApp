package com.example.managementapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.managementapp.repository.CourseShopRepository;
import com.example.managementapp.model.entity.Category;
import com.example.managementapp.model.entity.Course;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    //Repository

    private CourseShopRepository repository;

    //Live Data
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Course>> coursesOfSelectedCategories;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseShopRepository(application);

    }

    public LiveData<List<Category>> getAllCategories(){
        allCategories=repository.getCategories();
        return allCategories;
    }

    public LiveData<List<Course>> getAllCourses(){
        allCourses=repository.getCourses();
        return allCourses;
    }

    public LiveData<List<Course>> getCoursesOfSelectedCategories(int categoryId) {
        coursesOfSelectedCategories=repository.getCourses(categoryId);
        return coursesOfSelectedCategories;
    }

    public void addNewCourse(Course course){
        repository.insertCourse(course);
    }
    public  void updateCourse(Course course){
        repository.updateCourse(course);
    }
    public void deleteCourse(Course course){
        repository.deleteCourse(course);
    }
}
