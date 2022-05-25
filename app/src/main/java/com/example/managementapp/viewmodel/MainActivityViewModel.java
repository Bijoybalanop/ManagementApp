package com.example.managementapp.viewmodel;

import android.app.Application;
import android.icu.util.ULocale;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.managementapp.model.CourseShopRepository;
import com.example.managementapp.model.entity.Category;
import com.example.managementapp.model.entity.Course;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    //Repository

    private CourseShopRepository repository;

    //Live Data
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Course>> coursesOfSelectedCategories;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseShopRepository(application);

    }

    public LiveData<List<Category>> getAllCategories(){
        allCategories=repository.getCategories();
        return allCategories;
    }

    public LiveData<List<Course>> getCoursesOfSelectedCategories(int categoryId) {
        coursesOfSelectedCategories=repository.getCourses(categoryId);+









        return coursesOfSelectedCategories;
    }
}
