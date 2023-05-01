package com.chow.code_base_sdk

import android.content.Context
import android.content.Intent
import com.chow.code_base_sdk.ui.BaseActivity
import com.chow.code_base_sdk.utils.CharactersType

interface IBaseSdk {
    fun init(context: Context, charactersType: CharactersType)
}

object BaseSDK : IBaseSdk {

    private var initialized: Boolean = false

    override fun init(context: Context, charactersType: CharactersType) {
        if (!initialized) {
            initialized = true
            Intent(context, BaseActivity::class.java).apply {
                putExtra("CHARACTERS_TYPE", charactersType)
                // This flag removes all the activities
                // causing all existing tasks with the other activity to be cleared
                // and then this becomes the root to start the next activity task
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                // needs to add a new task to start activity after clearing activity
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }
    }
}