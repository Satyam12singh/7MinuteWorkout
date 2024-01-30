package com.example.a7minuteworkout

object Constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel>{

        var exerciseList= ArrayList<ExerciseModel>()
         val plank = ExerciseModel(
             1,
             "Planks",
             R.drawable.plank,
             false,
             false
             )
        exerciseList.add(plank)

        val bend= ExerciseModel(
            2,
            "Bend",
            R.drawable.bend,
            false,
            false
        )
        exerciseList.add(bend)

        val twist= ExerciseModel(
            3,
            "Twist",
            R.drawable.twist,
            false,
            false
        )
        exerciseList.add(twist)

         val locomotion= ExerciseModel(
            4,
            "Locomotion",
            R.drawable.locomotion,
            false,
            false
        )
        exerciseList.add(locomotion)

         val lunge= ExerciseModel(
            5,
            "Lunge",
            R.drawable.lunge,
            false,
            false
        )
        exerciseList.add(lunge)

         val pushup= ExerciseModel(
            6,
            "Push Up",
            R.drawable.pushup,
            false,
            false
        )
        exerciseList.add(pushup)

         val squat= ExerciseModel(
            7,
            "Squat",
            R.drawable.squat,
            false,
            false
        )
        exerciseList.add(squat)




        return exerciseList
    }
}