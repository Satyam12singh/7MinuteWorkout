package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore.Audio.Media
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding
import com.example.a7minuteworkout.databinding.CustomdialogForQuitBinding
import java.lang.Exception
import java.util.Locale



class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityExerciseBinding? = null

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var tts:TextToSpeech? = null

    private var player: MediaPlayer? = null
    private var playerexerchange : MediaPlayer? = null

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var exerciseAdapter: ExerciseStatusAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        tts= TextToSpeech(this, this)

        binding?.tvExerciseName?.visibility= View.INVISIBLE
        binding?.flProgressBarEx1?.visibility= View.INVISIBLE

        setSupportActionBar(binding?.toolbarExercise)


        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }



        setRestView()
        setUpExerciseStatusRecyclerView()

    }

    private fun setRestView(){

        try{
            val soundUri = Uri.parse("android.resource://com.example.a7minuteworkout/"+R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundUri)
            player?.isLooping= false
            player?.start()

        }catch (e: Exception){
            e.printStackTrace()
        }

        if(restTimer != null){
            restTimer?.cancel()
            restProgress=0
        }

        setRestProgress()

    }

    private fun setUpExerciseStatusRecyclerView(){
        binding?.rvexerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvexerciseStatus?.adapter = exerciseAdapter
    }


    private fun setRestProgress(){
        restProgress = 0
        binding?.ProgressBar?.progress = restProgress

        binding?.flProgressBar?.visibility= View.VISIBLE
        binding?.tvGetReady?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility= View.INVISIBLE
        binding?.flProgressBarEx1?.visibility= View.INVISIBLE
        binding?.imgExercise?.visibility = View.INVISIBLE
        binding?.textviewForNxtExercise?.visibility= View.INVISIBLE

        restTimer = object: CountDownTimer(10000, 1000 ){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.ProgressBar?.progress= 10- restProgress
                binding?.tvTimer?.text= (10 - restProgress).toString()
                if (currentExercisePosition > 0 ){
//                    try{
//                        val soundUri1 = Uri.parse("android.resource://com.example.a7minuteworkout/"+ R.raw.break_between_exercose)
//                        playerexerchange= MediaPlayer.create(applicationContext, soundUri1)
//                        playerexerchange?.release()
//                        playerexerchange?.isLooping= false
//                        playerexerchange?.start()
//                    }catch (e: Exception){
//                        e.printStackTrace()
//                    }
                    binding?.tvGetReady?.text= "Get ready for ${exerciseList!![currentExercisePosition].getName()} exercise"

                }

            }

            override fun onFinish() {

                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged()
                setExerciseView()
                setRestExerciseProgress()
            }

        }.start()
    }

    private fun setRestExerciseProgress(){

        restProgress= 0
        binding?.ProgressBar?.progress = restProgress
        restTimer = object: CountDownTimer(60000, 1000 ){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.ProgressBarEx1?.progress= 60- restProgress
                binding?.tvTimerEx1?.text= (60 - restProgress).toString()
            }

            override fun onFinish() {

                if (currentExercisePosition< exerciseList?.size!! -1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter?.notifyDataSetChanged()

                    Toast.makeText(applicationContext, "${exerciseList!![currentExercisePosition].getName()} Is completed", Toast.LENGTH_SHORT).show()
                    setExerciseView()
                    setRestProgress()
                }else{

                    val intent= Intent(this@ExerciseActivity, FinishedActivity::class.java)
                    startActivity(intent)

                }

            }

        }.start()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = CustomdialogForQuitBinding.inflate(layoutInflater)

        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnyes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnno.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }



    private fun setExerciseView() {
        if (currentExercisePosition < exerciseList?.size!!) {
            binding?.flProgressBar?.visibility = View.INVISIBLE
            binding?.tvGetReady?.visibility = View.INVISIBLE
            binding?.tvExerciseName?.visibility = View.VISIBLE
            binding?.flProgressBarEx1?.visibility = View.VISIBLE
            binding?.imgExercise?.visibility = View.VISIBLE
            binding?.textviewForNxtExercise?.visibility = View.VISIBLE

            binding?.imgExercise?.setImageResource(exerciseList!![currentExercisePosition].getImage())
            binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

            if (currentExercisePosition + 1 < exerciseList?.size!!) {
                binding?.textexercise?.text = exerciseList!![currentExercisePosition + 1].getName()
                speakText(exerciseList!![currentExercisePosition].getName())
            } else {
                binding?.textexercise?.text = ""  // Set an empty string for the last exercise
            }
        } else {
            // Handle the case where the currentExercisePosition is out of bounds
            val intent = Intent(this@ExerciseActivity, FinishedActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        restTimer?.cancel()
        restProgress = 0
        binding = null

        tts?.stop()
        tts?.shutdown()

        player?.stop()
    }


    private fun speakText(text:String){
        if(binding?.tvExerciseName?.visibility == View.VISIBLE) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }

    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result= tts?.setLanguage(Locale.ENGLISH)

        }
    }



}