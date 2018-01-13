package com.example.root.ankoexample

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.LinearLayout
import com.example.root.ankoexample.database.MyDatabseOpenHelper
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class RegisterActivity : AppCompatActivity() {
    // Database initialization
    val Context.database: MyDatabseOpenHelper get() = MyDatabseOpenHelper.getInstance(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
                hint = "Full name"
                inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }.lparams {
                topMargin = 2
                width = matchParent
                height = wrapContent
            }
            var name = editText {
                hint = "Full name"
            }.lparams {
                topMargin = 2
                width = matchParent
                height = wrapContent
            }

            var gender = editText {
                hint = "Male / Female"
            }.lparams {
                topMargin = 2
                width = matchParent
                height = wrapContent
            }

            var age = editText {
                hint = "Your age"
            }.lparams {
                topMargin = 2
                width = matchParent
                height = wrapContent
            }
            var btnSignIn = button {
                text = "Sign Up"
                onClick {
                    userSignUp(email.text.toString(),
                            password.text.toString(),
                            name.text.toString(),
                            gender.text.toString(),
                            age.text.toString())
                }
            }.lparams {
                width = matchParent
                topMargin = 10
            }

        }
    }

    private fun userSignUp(email: String, password: String, name: String, gender: String, age: String) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        try {
            val db = database.writableDatabase
            database.insertUserData(email, password, name, gender, age, db)
            // if success insert
            longToast("Register successfully. Check Your email")
            //
            startActivity(intentFor<LoginActivity>())
        } catch (e : SQLiteConstraintException){
            e.printStackTrace()
            toast("Failed to register. Something when wrong")
        }
    }
}
