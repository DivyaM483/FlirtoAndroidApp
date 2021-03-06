package com.example.androidapp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtPrivacyPolicy.makeLinks(
            Pair("Terms and Conditions", View.OnClickListener {
                        }),
            Pair("Privacy Policy", View.OnClickListener {
                }))

        btnLoginPhnNmbr.setOnClickListener {
            val intent=Intent(this, LoginWithPhnNmbrActivity::class.java)
            startActivity(intent)
        }

    }
    private fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    var url=""
                    if(link.first=="Terms and Conditions") {
                        url="https://www.flirto.online/terms-of-service"
                    }
                    if(link.first=="Privacy Policy") {
                         url= "https://www.flirto.online/privacy-policy"
                    }

                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)


                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.setColor(Color.WHITE)
                }
            }
            val startIndexOfLink = this.text.toString().indexOf(link.first)
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

        }
        this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }

}


