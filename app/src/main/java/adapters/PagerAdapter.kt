package adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fragments.*

class PagerAdapter(
    fragmentManager: FragmentManager,
    val tabCount:Int,
    val uid:String
    ) :FragmentStatePagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment {
        when(position){
            0->{ return friend(uid) }
            1->{ return roomList() }
            2->{ return oneOnOne() }
            3->{ return group() }
            else->{ return friend(uid) }
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}