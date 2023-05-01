package com.chow.wireviewercharacter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chow.code_base_sdk.BaseSDK
import com.chow.code_base_sdk.utils.CharactersType
import com.chow.wireviewercharacter.databinding.ActivityWireBinding
import dagger.hilt.android.AndroidEntryPoint

class WireActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWireBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BaseSDK.init(applicationContext, CharactersType.THE_WIRE)
    }
}