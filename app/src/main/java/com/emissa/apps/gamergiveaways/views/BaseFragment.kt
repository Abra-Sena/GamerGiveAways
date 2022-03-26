package com.emissa.apps.gamergiveaways.views

import androidx.fragment.app.Fragment
import com.emissa.apps.gamergiveaways.adapter.GiveawaysAdapter
import com.emissa.apps.gamergiveaways.viewmodel.GiveawaysViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * open means the class can be extended, it is not by default final as all classes in kotlin
 */
open class BaseFragment : Fragment() {

    protected val giveawaysViewModel: GiveawaysViewModel by sharedViewModel()

    protected val giveawaysAdapter by lazy {
        GiveawaysAdapter()
    }
}