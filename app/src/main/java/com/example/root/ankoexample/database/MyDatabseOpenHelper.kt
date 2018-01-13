package com.example.root.ankoexample.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.root.ankoexample.model.model
import org.jetbrains.anko.db.*

/**
 * Created by root on 11/01/18.
 */
class MyDatabseOpenHelper(ctx : Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {

    companion object {
        private var instance : MyDatabseOpenHelper? = null
        fun getInstance(ctx : Context) : MyDatabseOpenHelper {
            if (instance == null){
                instance = MyDatabseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
        // Users table
        val USER_TABLE = "User"
        val COLUMN_USER_ID = "id"
        val COLUMN_USER_EMAIL = "email"
        val COLUMN_USER_PWD = "password"
        val COLUMN_USER_NAME = "name"
        val COLUMN_USER_GENDER = "gender"
        val COLUMN_USER_AGE = "age"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        db?.createTable("User", true,
                "id" to INTEGER + PRIMARY_KEY + UNIQUE,
                "email" to TEXT + UNIQUE,
                "password" to TEXT,
                "name" to TEXT,
                "gender" to TEXT,
                "age" to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // Upgrade tables
        db?.dropTable("User", true)
    }

    fun insertUserData(email : String, password: String, name: String, gender: String, age:String, db: SQLiteDatabase?){
        db?.insert(USER_TABLE,
                COLUMN_USER_ID to null,
                COLUMN_USER_EMAIL to email,
                COLUMN_USER_PWD to password,
                COLUMN_USER_NAME to name,
                COLUMN_USER_GENDER to gender,
                COLUMN_USER_AGE to age)
    }
    fun loginUser(email: String, password: String, db: SQLiteDatabase): model.User {
        var user: model.User ? = null

        db.select(USER_TABLE, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_GENDER, COLUMN_USER_AGE)
                .whereArgs("($COLUMN_USER_EMAIL = {loginEmail}) AND ($COLUMN_USER_PWD = {loginPwd}",
                        "loginEmail" to email,
                        "loginPwd" to password)
                .parseOpt(object : RowParser<model.User>{
                    override fun parseRow(columns: Array<Any?>): model.User {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        val user_id = columns.get(1)
                        val user_email = columns.get(2)
                        val user_name = columns.get(3)
                        val user_gender = columns.get(4)
                        val user_age = columns.get(5)

                        user = model.User(user_id = user_id.toString(),
                                email = user_email.toString(),
                                name = user_name.toString(),
                                gender = user_gender.toString(),
                                age = user_age.toString())

                        return user!!

                    }

                })
        return user!!
    }


}