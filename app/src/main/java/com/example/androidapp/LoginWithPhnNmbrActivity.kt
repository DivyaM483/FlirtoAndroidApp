package com.example.androidapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.chaos.view.PinView


class LoginWithPhnNmbrActivity:AppCompatActivity(), View.OnClickListener {
    private lateinit var nxtBtnID:Button
    private lateinit var pinView:PinView
    private lateinit var userPhone:EditText
    private lateinit var first:ConstraintLayout
    private lateinit var second:ConstraintLayout
    private lateinit var nextButton:Button
    private lateinit var textU:TextView
    private lateinit var txtResendOTP:String
    private  var spinner:Spinner?=null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_with_phnnmbr)
        pinView = findViewById(R.id.pinView);
        userPhone = findViewById(R.id.userPhone);
        first = findViewById(R.id.first_step);
        second = findViewById(R.id.secondStep);
        textU=findViewById(R.id.topText)
        first.visibility= View.VISIBLE
        nextButton=findViewById(R.id.nextButton)
        nextButton.setOnClickListener(this);

        spinner=findViewById(R.id.spinnerCountry)
        spinner?.setAdapter(

            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                CountryData.countryNames.plus(CountryData.countryAreaCodes)
            )
        )
        spinner?.setSelection(0)
    }


    override fun onClick(p0: View?) {
        if (nextButton.text=="Let's go!")
        {
            val phone = userPhone.text.toString()
            if (!TextUtils.isEmpty(phone)) {
                nextButton.text="Verify"
                first.visibility=View.GONE
                second.visibility=View.VISIBLE
                textU.text="I Still don't trust you.nTell me something that only two of us know."
            }
            else {
                userPhone.setError("Please enter Phone Number")

            }
        }
        else if (nextButton.text == "Verify") {
            var otp:String  = pinView.text.toString()
            if (otp=="123456")
            {
                pinView.setLineColor(Color.GREEN);
//                textU.text="OTP Verified"
//                textU.setTextColor(Color.GREEN);
                Toast.makeText(this,"OTP Verified successfully",Toast.LENGTH_SHORT).show()
                val intent=Intent(this,UserRegistrationActivity::class.java)
                startActivity(intent)
            } else {
                pinView.setLineColor(Color.RED);
                textU.text= "X Incorrect OTP"
                textU.setTextColor(Color.RED);
            }
        }
    }

    override fun onBackPressed()
    {
        if(nextButton.text=="Let's go!")
        {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        else if(nextButton.text=="Verify")
        {
            nextButton.text="Let's go!"
            first.visibility=View.VISIBLE
            second.visibility=View.GONE
            textU.setTextColor(Color.WHITE)
            pinView.setLineColor(Color.LTGRAY)
            pinView.text=null
            textU.text="You're cute. Can I have your number?"
        }
        else if(nextButton.text=="Next")
        {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}
