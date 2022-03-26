package com.emissa.apps.gamergiveaways.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.emissa.apps.gamergiveaways.databinding.FragmentPs4GiveawaysBinding
import com.emissa.apps.gamergiveaways.model.Giveaways
import com.emissa.apps.gamergiveaways.utils.GiveawaysState

class Ps4GiveawaysFragment : BaseFragment() {

    private val binding by lazy {
        FragmentPs4GiveawaysBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.ps4GiveawaysRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = giveawaysAdapter
        }
        giveawaysViewModel.giveaways.observe(viewLifecycleOwner) { state ->
            when(state) {
                is GiveawaysState.LOADING -> {
                    Toast.makeText(requireContext(), "LOADING...", Toast.LENGTH_LONG).show()
                }
                is GiveawaysState.SUCCESS<*> -> {
                   val giveaways = state.giveaways as List<Giveaways>
                   giveawaysAdapter.setNewGiveaways(giveaways)
                }
                is GiveawaysState.ERROR -> {
                    Toast.makeText(requireContext(), state.error.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
        giveawaysViewModel.getGiveawaysByPlatform()

        // Inflate the layout for this fragment
        return binding.root
    }
}