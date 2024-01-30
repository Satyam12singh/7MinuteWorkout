package com.example.a7minuteworkout

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minuteworkout.databinding.ActivityCalculateBmiBinding
import kotlin.math.pow

class CalculateBMI : AppCompatActivity() {

    private var binding: ActivityCalculateBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.toolbarBmi?.setNavigationOnClickListener {
            onBackPressed()
        }

        val radioGroup = binding?.radiogroup

        radioGroup!!.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                R.id.radio_button_matric -> {
                    binding?.textinputheight?.visibility = View.VISIBLE
                    binding?.textinputweight?.visibility= View.VISIBLE
                    binding?.heightLayoutForUsSystem?.visibility = View.INVISIBLE
                    binding?.textinputusweight?.visibility=View.INVISIBLE
                    binding?.textinputusweight?.setText("")
                    binding?.tvFeet?.setText("")
                    binding?.tvInch?.setText("")

                }

                R.id.radio_button_usunit -> {
                    binding?.textinputheight?.visibility = View.INVISIBLE
                    binding?.textinputweight?.visibility=View.INVISIBLE
                    binding?.textinputusweight?.visibility=View.VISIBLE
                    binding?.textinputusweight?.setText("")
                    binding?.heightLayoutForUsSystem?.visibility = View.VISIBLE
                    binding?.textinputheight?.setText("")
                }
            }
        }


        binding?.btnBmiNormalCalculator?.setOnClickListener {
            calculateBmi()
        }



        binding?.heightLayoutForUsSystem?.visibility = View.INVISIBLE
        binding?.tvYourBmi?.visibility = View.INVISIBLE
        binding?.bmiCalculated?.visibility = View.INVISIBLE
        binding?.tvSuggestion?.visibility = View.INVISIBLE

    }

    private fun calculateBmi() {
        val heightText= binding?.textinputheight?.text.toString()
        val weightText= binding?.textinputweight?.text.toString()
        val feetText= binding?.tvFeet?.text.toString()
        val inchText= binding?.tvInch?.text.toString()
        val weightPondText= binding?.textinputusweight?.text.toString()


        if(heightText.isNotEmpty() && weightText.isNotEmpty() && feetText.isEmpty() && inchText.isEmpty() && weightPondText.isEmpty()){
            val height= heightText.toFloat()
            val weight= weightText.toFloat()

            val bmi = (weight / ((height / 100.0).pow(2))).toFloat()

            bmiSystemOutput(bmi)

        }
        else{
            val feet= feetText.toFloat()
            val inch= inchText.toFloat()
            val weight= weightPondText.toFloat()
            val totalInches = feet * 12 + inch
            val height = totalInches * 0.0254
            val weightKilograms = weight * 0.453592

            val bmi = (weightKilograms / ((height ).pow(2))).toFloat()

            bmiSystemOutput(bmi)
        }

    }

    private fun bmiSystemOutput(bmi : Float){
        if (bmi < 18.5){
            binding?.tvSuggestion?.text = "You are Underweight you need proper diet."
        }
        else if ((18.5<bmi) && (bmi< 24.9)){
            binding?.tvSuggestion?.text = " You are fit "
        }
        else if( 25<bmi && bmi<29.9){
            binding?.tvSuggestion?.text= " You are overweight, Do exercise and take a better diet plan."
        }
        else if(30<bmi && bmi<34.9){
            binding?.tvSuggestion?.text = "Obese class 1 "
        }
        else if(35<bmi && bmi<39.9){
            binding?.tvSuggestion?.text = "Obese class 2 "
        }
        else{
            binding?.tvSuggestion?.text = "Obese class 3 "
        }

        binding?.tvSuggestion?.visibility= View.VISIBLE
        binding?.tvYourBmi?.visibility= View.VISIBLE

        binding?.bmiCalculated?.text= bmi.toString()
        binding?.bmiCalculated?.visibility= View.VISIBLE
    }

}








//val height = heightText.toFloat()
//val weight = weightText.toFloat()
//val bmi = (weight / ((height / 100.0).pow(2))).toFloat()

//        val radioGroup = binding.radiogroup

//        radioGroup.setOnCheckedChangeListener { _, checkedId ->
//            when (checkedId) {
//                R.id.radio_button_matric -> {
//                    binding.textinputheight.visibility = View.VISIBLE
//                    binding.heightLayoutForUsSystem.visibility = View.INVISIBLE
//                }
//
//                R.id.radio_button_usunit -> {
//                    binding.textinputheight.visibility = View.INVISIBLE
//                    binding.heightLayoutForUsSystem.visibility = View.VISIBLE
//                }
//            }
//        }
