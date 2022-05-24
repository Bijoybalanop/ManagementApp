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
            //Insert data when database is created ....
        }
    };



}
