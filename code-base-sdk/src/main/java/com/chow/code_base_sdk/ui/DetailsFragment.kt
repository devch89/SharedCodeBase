package com.chow.code_base_sdk.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.chow.code_base_sdk.R
import com.chow.code_base_sdk.databinding.FragmentDetailsBinding
import com.chow.code_base_sdk.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private val baseViewModel by lazy {
        ViewModelProvider(requireActivity())[BaseViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        baseViewModel.viewModelScope.launch {
            baseViewModel.characterSelected.collect {
                it.let {
                    binding.apply {
                        charTitle.text = it?.name
                        charDescription.text = it?.description

                        Glide.with(root.context.applicationContext).load(it?.image)
                            .error(R.drawable.error_img_holder_24)
                            .placeholder(R.drawable.load_img_holder).into(charImage)
                    }
                }
            }
        }
        return binding.root
    }
}

