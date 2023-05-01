package com.chow.code_base_sdk.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.chow.code_base_sdk.adapter.BaseAdapter
import com.chow.code_base_sdk.databinding.FragmentCharacterSearchBinding
import com.chow.code_base_sdk.utils.CharactersType
import com.chow.code_base_sdk.utils.UIState
import com.chow.code_base_sdk.viewmodel.BaseViewModel
import kotlinx.coroutines.launch


class CharacterSearchFragment : Fragment() {

    private lateinit var charactersType: CharactersType

    private val binding by lazy {
        FragmentCharacterSearchBinding.inflate(layoutInflater)
    }

    private val baseViewModel by lazy {
        ViewModelProvider(requireActivity())[BaseViewModel::class.java]
    }

    private val baseAdapter by lazy {
        BaseAdapter {
            baseViewModel.setCurrentItem(it)
            binding.splSlidingPaneLayout.openPane()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // retains the state in a fragment on configuration change
        retainInstance = true
        charactersType = requireActivity().intent.getSerializableExtra("CHARACTERS_TYPE") as CharactersType

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        if (baseViewModel.characterView != null) return baseViewModel.characterView!!

        val slidingPaneLayout = binding.splSlidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        // Connect to the SlidingPaneLayout to the system back button.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, CharacterListOnBackPressedCallback(slidingPaneLayout)
        )

        binding.searchLayout.rvCharacterList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = baseAdapter
        }

        binding.searchLayout.svSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    baseViewModel.searchItem(it)
                }
                return false
            }
        })

        lifecycleScope.launch {
            baseViewModel.search.observe(viewLifecycleOwner) {
                it?.let {
                    baseAdapter.updateChars(it)
                }
            }
        }

        lifecycleScope.launch {
            baseViewModel.characters.collect {
                when (it) {
                    is UIState.Loading -> {}
                    is UIState.Success -> {
                        // create your adapter and recyclerview
                        baseViewModel.prevData = it.data
                        baseViewModel.characterSelected.value = it.data.firstOrNull()
                        baseAdapter.updateChars(it.data)
                    }
                    is UIState.Error -> {
                        AlertDialog.Builder(requireActivity()).setTitle("Error")
                            .setMessage(it.error.localizedMessage)
                            .setNegativeButton("DISMISS") { dialog, _ ->
                                dialog.dismiss()
                            }.create().show()
                    }
                }
            }
        }

        if (baseViewModel.prevData == null) {
            val charactersType =
            baseViewModel.getCharacters(charactersType)
        }
        baseViewModel.characterView = binding.root

        return binding.root
    }
}

class CharacterListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(
    // Set the default 'enabled' state to true only if it is slidable (i.e., the panes
    // are overlapping) and open (i.e., the detail pane is visible).
    slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen
), SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(
            this
        )
    }

    override fun handleOnBackPressed() {
        // Return to the list pane when the system back button is pressed.
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelOpened(panel: View) {
        // Intercept the system back button when the detail pane becomes visible.
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        // Disable intercepting the system back button when the user returns to the
        // list pane.
        isEnabled = false
    }

}
