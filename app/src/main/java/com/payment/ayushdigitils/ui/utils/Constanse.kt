package com.payment.ayushdigitils.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.payment.ayushdigitils.R

import java.text.SimpleDateFormat
import java.util.Locale


object Constanse {


    fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        var startIndexOfLink = -1
        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun updateDrawState(textPaint: TextPaint) {
                    // use this to change the link color
                    textPaint.color = resources.getColor(R.color.dashboard_icon_color)
                    // toggle below value to enable/disable
                    // the underline shown below the clickable text
                    textPaint.isUnderlineText = true
                }

                override fun onClick(view: View) {
                    Selection.setSelection((view as TextView).text as Spannable, 0)
                    view.invalidate()
                    link.second.onClick(view)
                }
            }
            startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
//      if(startIndexOfLink == -1) continue // todo if you want to verify your texts contains links text
            spannableString.setSpan(
                clickableSpan,
                startIndexOfLink,
                startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }

    fun configOtpEditText(vararg etList: EditText) {
        val afterTextChanged = { index: Int, e: Editable? ->
            val view = etList[index]
            val text = e.toString()

            when (view.id) {
                // first text changed
                etList[0].id -> {
                    if (text.isNotEmpty()) {
                        etList[index + 1].requestFocus()
                    }
                }

                // las text changed
                etList[etList.size - 1].id -> {
                    if (text.isEmpty()) etList[index - 1].requestFocus()
                }

                // middle text changes
                else -> {
                    if (text.isNotEmpty()) etList[index + 1].requestFocus()
                    else etList[index - 1].requestFocus()

                }
            }
            false
        }
        etList.forEachIndexed { index, editText ->
            editText.doAfterTextChanged { afterTextChanged(index, it) }
        }
    }

    fun setStaticProviderImg(name: String, img: ImageView, context: Context, type: String) {
        var name = name
        name = name.lowercase(Locale.getDefault()).replace("odisha".toRegex(), "")
        name = name.replace(" ".toRegex(), "").lowercase(Locale.getDefault())
        try {
            if (name.contains("dish")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_dish_tv))
            } else if (name.contains("sundirect")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_sun_direct_vector_logo))
            } else if (name.contains("tatasky")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_tata_sky))
            } else if (name.contains("videocon")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_videocon))
            } else if (name.contains("vodafone")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_vodafone))
            } else if (name.contains("bsnl")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_bsnl))
            } else if (name.contains("jio")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_jio))
            } else if (name.contains("idea")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_idea))
            } else if (name.contains("vi")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_vi))
            } else if (name.contains("airtel")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_airtel))
            } else if (name.contains("tatadocomo")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_docomo))
            } else if (type.contains("mobile")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_prepaid))
            }else if (type.contains("prepaid")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_prepaid))
            }else if (type.contains("postpaid")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_postpaid))
            } else if (type.contains("electricity")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_electricity))
            } else if (type.contains("lpg")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_cylinder))
            } else if (type.contains("gas")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_piped_gas))
            } else if (type.contains("landline")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_postpaid))
            } else if (type.contains("broadband")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_broadband))
            } else if (type.contains("water")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_water))
            } else if (type.contains("loanrepay")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_loan))
            } else if (type.contains("lifeinsurance")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_car_insurance))
            } else if (type.contains("fasttag")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_fast_tag))
            } else if (type.contains("cable")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_cable))
            } else if (type.contains("insurance")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_car_insurance))
            } else if (type.contains("schoolfees")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_education))
            } else if (type.contains("muncipal")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_muncipal))
            } else if (type.contains("postpaid")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_postpaid))
            } else if (type.contains("housing")) {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_home_grocery))
            } else {
                img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_at))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun truncateToTwoDecimalPlaces(input: String): String {
        val decimalIndex = input.indexOf('.')
        if (decimalIndex != -1 && input.length > decimalIndex + 3) {
            return input.substring(0, decimalIndex + 3)
        }
        return input
    }


    @SuppressLint("SimpleDateFormat")
    val SIMPLE_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    @SuppressLint("SimpleDateFormat")
    val SHOWING_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd")
    @SuppressLint("SimpleDateFormat")
    val COMMON_DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")




}