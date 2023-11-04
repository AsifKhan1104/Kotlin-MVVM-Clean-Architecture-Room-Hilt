package com.example.practiceapp1.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practiceapp1.R

class HigherOrderFunctionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_higher_order_functions)

        genericFunc<String>()

        higherOrderFunc({
            println("This is first lambda")
            return
        }, {
            println("This is second lambda")
            // return
        }, {
            println("This is first lambda")
            // return
        })
    }

    private inline fun higherOrderFunc(
        lmbd1: () -> Unit,
        noinline lmbd2: () -> Unit,
        crossinline lmbd3: () -> Unit
    ) {
        lmbd1()
        lmbd2()
        lmbd3()
    }

    private inline fun <reified T> genericFunc() {
        print(T::class)
    }
}