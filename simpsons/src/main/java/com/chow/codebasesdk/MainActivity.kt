package com.chow.codebasesdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chow.code_base_sdk.BaseSDK
import com.chow.code_base_sdk.utils.CharactersType
import com.chow.codebasesdk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        BaseSDK.init(applicationContext, CharactersType.SIMPSONS)
    }
}