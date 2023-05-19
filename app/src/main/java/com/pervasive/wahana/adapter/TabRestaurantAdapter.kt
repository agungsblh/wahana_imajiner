package com.pervasive.wahana.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pervasive.wahana.fragments.RestoDuaFragment
import com.pervasive.wahana.fragments.RestoSatuFragment

class TabRestaurantAdapter(fragmentManager:FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        when(position){
            0->{
                return RestoSatuFragment()
            }
            1->return RestoDuaFragment()
            else->return RestoSatuFragment()
        }
    }
    fun getItemPosition(`object`:Any?):Int{
        return POSITION_NONE
    }
}