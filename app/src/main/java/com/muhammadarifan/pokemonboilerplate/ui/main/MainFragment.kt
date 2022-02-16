package com.muhammadarifan.pokemonboilerplate.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.google.android.material.snackbar.Snackbar
import com.muhammadarifan.pokemonboilerplate.R
import com.muhammadarifan.pokemonboilerplate.databinding.MainFragmentBinding
import com.muhammadarifan.pokemonboilerplate.local.model.Pokemon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment), MainAdapter.OnItemClickListener {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding : MainFragmentBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = MainFragmentBinding.bind(view)

        val adapter = MainAdapter(this)

        binding.apply {
            rvPokemon.setHasFixedSize(true)
            rvPokemon.adapter = adapter
        }

        viewModel.connectionCheck(requireContext())

        lifecycleScope.launch {
            viewModel.pokemon.collectLatest {
                adapter.submitData(it)
            }
        }

        viewModel.isConnect.observe(viewLifecycleOwner) {
            if (it == false) {
                Snackbar.make(view, "Sorry, You are disconnected", Snackbar.LENGTH_LONG).show()
            }
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isBackCheck("Are you sure win to quit ?")
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onItemClick(pokemon: Pokemon) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(pokemon.id)
        findNavController().navigate(action)
    }

    fun isBackCheck(alertmessage: String?) {
        val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> Process.killProcess(Process.myPid())
                    DialogInterface.BUTTON_NEGATIVE -> {}
                }
            }
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage(alertmessage)
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }
}