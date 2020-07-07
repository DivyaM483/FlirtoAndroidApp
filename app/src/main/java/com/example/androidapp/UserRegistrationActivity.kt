package com.example.androidapp

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class UserRegistrationActivity :AppCompatActivity(), View.OnClickListener {
    private var nextButton:Button?=null
    private var nameLayout:ConstraintLayout?=null
    private var dobLayout:ConstraintLayout?=null
    private var profession_layout:ConstraintLayout?=null
    private var gender_layout:ConstraintLayout?=null
    private lateinit var radioGroup_Gender:RadioGroup
    private var firstNameTxt:EditText?=null
    private var lastNameTxt:EditText?=null
    private var dobTxt:EditText?=null
    private var txtTitle:TextView?=null
    private var professionTxt:TextView?=null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)
        nameLayout=findViewById(R.id.name_layout)
        dobLayout=findViewById(R.id.dob_layout)
        profession_layout=findViewById(R.id.profession_layout)
        gender_layout=findViewById(R.id.gender_layout)
        nameLayout?.visibility=View.VISIBLE
        firstNameTxt=findViewById(R.id.firstName)

        lastNameTxt=findViewById(R.id.lastName)
        dobTxt=findViewById(R.id.editDOB)
        professionTxt=findViewById(R.id.editProfession)
        radioGroup_Gender=findViewById(R.id.radioGenderGroup)
        txtTitle=findViewById(R.id.txtTitle)
        nextButton=findViewById(R.id.nextButton)
        nextButton?.setOnClickListener(this)
        val datePicker = findViewById<DatePicker>(R.id.datepickerDOB)
        val today = Calendar.getInstance()
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
//        dobTxt?.setOnClickListener {
//            datepickerDOB.visibility=View.VISIBLE
//            datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
//                today.get(Calendar.DAY_OF_MONTH)
//
//            )
//            {view,year,month,day->  val month = month + 1
//                val msg :String=  "$day/$month/$year"
//                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//            }
//
//        }
        dobTxt?.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
              var age : Int? =  getAge( year,  month,  day)
                if (age != null) {
                    if(age>=18) {
                        dobTxt?.setText(""+dayOfMonth + "/" + month + "/" + year)
                    }
                    else
                    {
                        Toast.makeText(this,"Below 18 years are not allowed",Toast.LENGTH_SHORT).show()
                    }
                }

            }, year, month, day)
            dpd.show()

        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private  fun getAge(year: Int, month: Int, day: Int): Int? {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob[year, month] = day
        var age =   today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }
        val ageInt = age
        return ageInt
    }
    override fun onClick(p0: View?) {
        val firstName=firstNameTxt?.text.toString()
        val lastName=lastNameTxt?.text.toString()
        val dob:String=dobTxt.toString()
        val genderID: Int =radioGroup_Gender.checkedRadioButtonId
        var selectedRadioButton:RadioButton
        var selectedGender:String
        val profession:String=professionTxt?.text.toString()
        if (nameLayout?.visibility==View.VISIBLE)
        {
            if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName))
            {
                txtTitle?.text="Hello "+firstName+"..To know more about you, tell us your date of birth."
                nameLayout?.visibility=View.GONE
                dobLayout?.visibility=View.VISIBLE
            }
            else{
                if(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName))
                {
                    firstNameTxt?.error = "Please enter First name"
                    lastNameTxt?.error = "Please enter Last name"
                }
                else if(TextUtils.isEmpty(firstName)) {
                    firstNameTxt?.error = "Please enter First name"
                }
                else if (TextUtils.isEmpty(lastName))
                {
                    lastNameTxt?.error = "Please enter Last name"
                }
            }
        }
        else if (dobLayout?.visibility==View.VISIBLE)
        {
            if(!TextUtils.isEmpty(dob))
            {
                txtTitle?.text="How would you describe your gender?"
                dobLayout?.visibility=View.GONE
                gender_layout?.visibility=View.VISIBLE
            }
            else if(TextUtils.isEmpty(dob))
            {
                dobTxt?.error = "Please enter Date of Birth"
            }

        }
        else if (gender_layout?.visibility==View.VISIBLE)
        {
            if(genderID!=-1)
            {
                txtTitle?.text="What do you actually do?"
                selectedRadioButton=findViewById(genderID)
                selectedGender=selectedRadioButton.text.toString()
                gender_layout?.visibility=View.GONE
                profession_layout?.visibility=View.VISIBLE
            }
            else if(genderID==-1)
            {
                Toast.makeText(this,"Please select gender",Toast.LENGTH_SHORT).show()
            }
        }
        else if (profession_layout?.visibility==View.VISIBLE)
        {
            if(!TextUtils.isEmpty(profession))
            {
                txtTitle?.text="Registration completed successfully.."
                profession_layout?.visibility=View.GONE
                nextButton?.text=="Continue"

            }
            else if(TextUtils.isEmpty(profession))
            {
                professionTxt?.error = "Please enter profession details"
            }
        }
        else
        {
            val intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {

        if (nameLayout?.visibility==View.VISIBLE) {
            val intent=Intent(this,LoginWithPhnNmbrActivity::class.java)
            startActivity(intent)
        }
        else if(dobLayout?.visibility==View.VISIBLE)
        {
            nameLayout?.visibility=View.VISIBLE
            dobLayout?.visibility=View.GONE
        }
        else if (gender_layout?.visibility==View.VISIBLE)
        {
            dobLayout?.visibility=View.VISIBLE
            gender_layout?.visibility=View.GONE
        }
        else if(profession_layout?.visibility==View.VISIBLE)
        {
            gender_layout?.visibility=View.VISIBLE
            profession_layout?.visibility=View.GONE
        }
        else
        {
            val intent=Intent(this,LoginWithPhnNmbrActivity::class.java)
            startActivity(intent)
        }
    }
}