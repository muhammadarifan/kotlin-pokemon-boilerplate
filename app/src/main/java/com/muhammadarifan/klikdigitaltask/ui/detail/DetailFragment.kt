package com.muhammadarifan.klikdigitaltask.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.muhammadarifan.klikdigitaltask.R
import com.muhammadarifan.klikdigitaltask.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewPagerAdapter: DetailViewPagerAdapter
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)
        viewPagerAdapter = DetailViewPagerAdapter(args.pokemonId, requireActivity().supportFragmentManager, requireActivity().lifecycle)

        binding.apply {
            vpFragment.adapter = viewPagerAdapter
            vpFragment.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when(position) {
                        0 -> {
                            bnFragment.selectedItemId = R.id.m_information
                        }
                        1 -> {
                            bnFragment.selectedItemId = R.id.m_evolution
                        }
                    }
                }
            })

            bnFragment.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.m_information -> {
                        vpFragment.currentItem = 0
                        return@setOnItemSelectedListener true
                    }
                    R.id.m_evolution -> {
                        vpFragment.currentItem = 1
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
        }

        viewModel.connectionCheck(requireContext())

        viewModel.isConnect.observe(viewLifecycleOwner, {
            if (!it) {
                Snackbar.make(view, "Sorry, You are disconnected", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        viewModel.connectionCheck(requireContext())
    }
}