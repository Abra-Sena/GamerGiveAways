package com.emissa.apps.gamergiveaways.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emissa.apps.gamergiveaways.R
import com.emissa.apps.gamergiveaways.databinding.FragmentGiveawaysBinding
import com.emissa.apps.gamergiveaways.model.Giveaways
import com.emissa.apps.gamergiveaways.utils.GiveawaysState
import com.emissa.apps.gamergiveaways.utils.PlatformType


class GiveawaysFragment : BaseFragment() {

    private val binding by lazy {
        FragmentGiveawaysBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.giveawaysRecycler.apply {
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
        giveawaysViewModel.getSortedGiveaways()

        binding.btnMoveToPc.setOnClickListener {
            giveawaysViewModel.platform = PlatformType.PC
            findNavController().navigate(R.id.action_giveaways_fragment_to_pc_giveaways_fragment)
        }

        binding.btnMoveToPs4.setOnClickListener {
            giveawaysViewModel.platform = PlatformType.PS4
            findNavController().navigate(R.id.action_giveaways_fragment_to_ps4_giveaways_fragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}