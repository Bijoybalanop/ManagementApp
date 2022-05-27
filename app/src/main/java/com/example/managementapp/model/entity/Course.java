package com.example.managementapp.model.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.managementapp.model.entity.Category;

@Entity(tableName = "course_table",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "category_id",
                childColumns = "course_id",
                onDelete = CASCADE))

public class Course extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    private int courseId;

    @ColumnInfo(name = "course_name")
    private String courseName;

    @ColumnInfo(name = "course_unit_price")
    private String courseUnitPrice;

    @ColumnInfo(name = "category_Id")
    private int categoryId;

    @Ignore
    public Course() {
    }

    public Course(int courseId, String courseName, String courseUnitPrice, int categoryId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseUnitPrice = courseUnitPrice;
        this.categoryId = categoryId;
    }

    @Bindable
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
        notifyPropertyChanged(BR.courseId);
    }

    @Bindable
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
        notifyPropertyChanged(BR.courseName);
    }

    @Bindable
    public String getCourseUnitPrice() {
        return courseUnitPrice;
    }

    public void setCourseUnitPrice(String courseUnitPrice) {
        this.courseUnitPrice = courseUnitPrice;
        notifyPropertyChanged(BR.courseUnitPrice);
    }

    @Bindable
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.categoryId);
    }
    @Override
    public String toString() {
        return this.courseName;
    }
}
