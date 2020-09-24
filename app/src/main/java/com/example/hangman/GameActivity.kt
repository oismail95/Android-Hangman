package com.example.hangman

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.game_screen.*
import kotlin.random.Random

class GameActivity : AppCompatActivity(){

    //This array will be for having the letters match to the exact position when playing the game
    private var listPhrase: Array<String> = arrayOf("Omar the Android Developer!",
        "Albert Einstein invented the Theory of Relativity.",
        "Apple was established by Steve Jobs.",
        "Mars is the fourth planet of the Solar System.",
        "Abraham Lincoln was sworn as the Sixteenth President of the U.S.",
        "University of Missouri - St. Louis",
        "Hollywood is popular for actors.",
        "Bill Gates is the founder of Microsoft.",
        "Mesopotamia was the first civilization in history.",
        "Giza Pyramids is one of the seven wonders of the world.")

    //The blank phrases is used to have the user fill out the blanks
    private var blankPhrase: Array<String> = arrayOf("____ ___ _______ _________!",
        "______ ________ ________ ___ ______ __ __________.",
        "_____ ___ ___________ __ _____ ____.",
        "____ __ ___ ______ ______ __ ___ _____ ______.",
        "_______ _______ ___ _____ __ ___ _________ _________ __ ___ _._.",
        "__________ __ ________ - __. _____",
        "_________ __ _______ ___ ______.",
        "____ _____ __ ___ _______ __ _________.",
        "___________ ___ ___ _____ ____________ __ _______.",
        "____ ________ __ ___ __ ___ _____ _______ __ ___ _____.")

    //Modify Phrase is used when the user is in the process of filling out the phrase during the game
    private var modifyPhrase: Array<String> = blankPhrase

    //Here is where the  phrase gets displayed in the text
    private var phrase: TextView? = null

    //Here is where the user picks a letter during the process of the game
    private var letter: EditText? = null

    //Here is where the character gets checked if any of them match in the phrase
    private var char_ck: Char? = null

    //Here is where the display letters takes place when the letters are used
    private var displayLettersUsed: String = ""
    private var usedLetters: TextView? = null

    private var randPhrase: Int = 0

    private var hangman: HangmanConstruct? = null

    //The draw position is initialized to the starting point of the hangman canvas
    private var drawPos: Int = 0

    //Here is where completed is set to false for the process to play the game until the
    //phrase is completed. Once completed, it'll set to true and the letters submitted
    //will not be utilized
    private var completed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_screen)

        //Here is where the draw gets initialized whenever the game starts
        hangman = HangmanConstruct(this)
        hangman?.setDraw()

        phrase = this?.findViewById(R.id.displayPhrase)
        letter = this?.findViewById(R.id.letterSubmit)
        usedLetters = this?.findViewById(R.id.displayLettersUsed)

        //Here is where the phrase number gets generated randomly before in use
        randPhrase = Random.nextInt(10)

        displayPhrase()
        submitButton()
    }

    private fun displayPhrase(){
        //The draw is set in case if the letter selected does not match on the phrase, body part gets drawn
        var draw: Int = 0

        if(!completed) {
            if (char_ck != null) {
                //Here is the process of displaying the letters used in the box.
                if (displayLettersUsed == "") {
                    displayLettersUsed = char_ck?.toUpperCase().toString()
                } else {
                    //The checkLetter is used to test if the letter submitted is used.
                    var checkLetter: Int = 0

                    for (i in 0..displayLettersUsed.length - 1) {

                        //Grabs the character from the text of the letters used
                        var char = displayLettersUsed.get(i)

                        //If there is, checkLetter will get incremented
                        if (char_ck == char || char_ck?.toUpperCase() == char || char_ck?.toLowerCase() == char)
                            checkLetter++
                    }

                    //If the letter does not exist in the string, the new letter will be concatenated
                    //Else, the function will return and not continue on
                    if (checkLetter == 0)
                        displayLettersUsed += ", " + char_ck?.toUpperCase().toString()
                    else {
                        Toast.makeText(applicationContext, "Letter used!", Toast.LENGTH_SHORT).show()
                        return
                    }
                }

                //Here is where the letter picked gets scanned along the phrase if it matches
                for (i in 0..listPhrase[randPhrase].length - 1) {
                    var char = listPhrase[randPhrase].get(i)

                    if (char_ck == char || char_ck?.toUpperCase() == char || char_ck?.toLowerCase() == char) {
                        var change: String = Character.toString(char)
                        modifyPhrase[randPhrase] = modifyPhrase[randPhrase].substring(
                            0,
                            i
                        ) + change + modifyPhrase[randPhrase].substring(i + 1)
                        draw++
                    }
                }

                //Here is where the body part gets drawn by letting the view know if the draw is zero
                if (draw == 0) {
                    drawPos++
                    hangman?.draw()
                    Toast.makeText(applicationContext, "Body part drawn!", Toast.LENGTH_SHORT).show()

                    //Here is where the game is over and gets the phrase displayed if the body gets drawn fully
                    if (drawPos == 6) {
                        modifyPhrase[randPhrase] = listPhrase[randPhrase]
                        phrase?.setText(modifyPhrase[randPhrase])
                        gameOverDisplay()
                    }
                } else {
                    Toast.makeText(applicationContext, "Letter submitted!", Toast.LENGTH_SHORT).show()
                }

                //Here is where the used letters gets displayed in the text box
                usedLetters?.setText(displayLettersUsed)
            }

            //Here is where the phrase gets updated when the user is in progress of playing the game
            phrase?.setText(modifyPhrase[randPhrase])

            if (modifyPhrase[randPhrase] == listPhrase[randPhrase]) {
                Toast.makeText(applicationContext, "Game completed!", Toast.LENGTH_SHORT).show()
                completed = true
            }
        }
        else
            Toast.makeText(applicationContext, "Game completed!", Toast.LENGTH_SHORT).show()
    }

    private fun gameOverDisplay(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game Over")
        builder.setMessage("You lost the game. Do you want to play the game again?")

        builder.setPositiveButton("Yes"){dialog, which ->
            Toast.makeText(applicationContext, "Game Starts!", Toast.LENGTH_SHORT).show()

            //Here is where the info resets to normal when the user choosed to play the game again
            hangman?.setDraw()

            drawPos = 0
            displayLettersUsed = ""
            usedLetters?.setText(displayLettersUsed)

            randPhrase = Random.nextInt(10)
            phrase?.setText(blankPhrase[randPhrase])
            completed = false
        }
        builder.setNegativeButton("No"){dialog, which ->
            Toast.makeText(applicationContext, "Left the game!", Toast.LENGTH_SHORT).show()
            super.onBackPressed()
        }

        builder.show()
    }

    private fun submitButton(){

        buttonSubmit.setOnClickListener{view ->
            var getLetter = letter?.text.toString()

            if(getLetter.length == 1){
                char_ck = getLetter.single()
                if(char_ck?.isLetter() == true) {
                    displayPhrase()
                }
                else
                    Toast.makeText(applicationContext, "Not a letter!", Toast.LENGTH_SHORT).show()
            }
            else if(getLetter.length == 0){
                Toast.makeText(applicationContext, "No letter submitted!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext, "More than one characters used!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Directions")
        builder.setMessage("Are you sure you want to quit the game?")

        builder.setPositiveButton("Yes"){dialog, which ->
            Toast.makeText(applicationContext, "Game Resigns!", Toast.LENGTH_SHORT).show()
            super.onBackPressed()
        }
        builder.setNegativeButton("No"){dialog, which ->
            Toast.makeText(applicationContext, "Game Resumes!", Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }
}