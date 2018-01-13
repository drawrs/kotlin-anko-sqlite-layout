package com.example.root.ankoexample

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import com.example.root.ankoexample.database.MyDatabseOpenHelper
import com.example.root.ankoexample.model.model
import org.jetbrains.anko.*
import org.jetbrains.anko.db.RowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.sdk25.coroutines.onClick

class LoginActivity : AppCompatActivity() {
    val Context.database: MyDatabseOpenHelper get() = MyDatabseOpenHelper.getInstance(applicationContext)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        verticalLayout {
            orientation = LinearLayout.VERTICAL
            padding = 10

            var email = editText {

                hint = "Email Address"
                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

            }.lparams {
                width = matchParent
                height = wrapContent
            }
            var password = editText {
                hint = "Password"
                inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }.lparams {
                topMargin = 2
                width = matchParent
                height = wrapContent
            }

            var btnSignIn = button {
                text = "Sign In"
                onClick {
                    userLogin(email.text.toString(), password.text.toString())
                }
            }.lparams {
                width = matchParent
                topMargin = 10
            }

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                horizontalGravity = Gravity.CENTER_HORIZONTAL

                textView {
                    text = "Have an Account ? "
                    //backgroundColor = getColor(R.color.colorPrimary)
                }.lparams {
                    //weight = 1F
                    width = wrapContent
                }
                var linkSignUp = textView {
                   // backgroundColor = getColor(R.color.colorPrimary)
                    text = "Sign Up"
                    textColor = getColor(R.color.signup_link)
                    onClick {
                        startActivity(intentFor<RegisterActivity>())
                    }
                }.lparams {
                    //weight = 1F

                    width = wrapContent
                }

            }.lparams {
                width = matchParent
                topMargin = 20
            }
        }


    }
    private fun userLogin(email: String, password: String) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //toast("Testingg")
        var user: model.User ? = null
        try {
            database.use {

                select(MyDatabseOpenHelper.USER_TABLE, MyDatabseOpenHelper.COLUMN_USER_ID, MyDatabseOpenHelper.COLUMN_USER_EMAIL, MyDatabseOpenHelper.COLUMN_USER_NAME, MyDatabseOpenHelper.COLUMN_USER_GENDER, MyDatabseOpenHelper.COLUMN_USER_AGE)
                        .whereArgs("(${MyDatabseOpenHelper.COLUMN_USER_EMAIL} = {loginEmail}) AND (${MyDatabseOpenHelper.COLUMN_USER_PWD} = {loginPwd})",
                                "loginEmail" to email,
                                "loginPwd" to password)
                        .parseOpt(object : RowParser<model.User> {
                            override fun parseRow(columns: Array<Any?>): model.User {
                                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                Log.d("columnt", ""  + columns)
                                Log.d("columnt", ""  + columns.size)
                                val user_id = columns.get(0)
                                val user_email = columns.get(1)
                                val user_name = columns.get(2)
                                val user_gender = columns.get(3)
                                val user_age = columns.get(4)

                                user = model.User(user_id = user_id.toString(),
                                        email = user_email.toString(),
                                        name = user_name.toString(),
                                        gender = user_gender.toString(),
                                        age = user_age.toString())

                                return user!!

                            }

                        })
            }
           // Cek if user is Null
            if (user != null){
                startActivity(intentFor<MainActivity>( ))
            } else {
                toast("Email or Password wasn't corret !")
            }
        } catch (e : SQLiteConstraintException){
            e.printStackTrace()
        }
    }


}
