package com.payment.ayushdigitils.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.avatarfirst.avatargenlib.AvatarConstants
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentProfileBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.profile.about.AboutUserFragment
import com.payment.ayushdigitils.ui.fragments.profile.setting.SettingFragment
import com.payment.ayushdigitils.ui.fragments.profile.support.SupportFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {


    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val titles = arrayOf("About", "Setting", "Support")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }
    override fun initView() {

        binding.lblName.text = prefs.getUserName()
        binding.lblEditProfile.text = "id : ${prefs.getUserId()}"


        Glide.with(binding.imgProfile)
            .load(
                AvatarGenerator.avatarImage(
                    requireContext(),
                    100,
                    AvatarConstants.CIRCLE,
                    prefs.getUserName()
                )
            )
            .into(binding.imgProfile)


        val adapter = ViewPagerFragmentAdapter(this, titles)
        binding.viewPager2.adapter = adapter
        binding.viewPager2.offscreenPageLimit = 1
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager2
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
//            tab.setIcon(icons[position])
        }.attach()

        binding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.viewPager2.currentItem = position
            }
        })

        binding.viewPager2.isUserInputEnabled = false

    }

    override fun initClick() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    private class ViewPagerFragmentAdapter(
        @NonNull context: Fragment,
        var titles: Array<String>
    ) :
        FragmentStateAdapter(context) {
        @OptIn(ExperimentalCoroutinesApi::class)
        @NonNull
        override fun createFragment(position: Int): Fragment {
            when (position) {


                0 -> return AboutUserFragment()
                1 -> return SettingFragment()
                2 -> return SupportFragment()
            }
            return AboutUserFragment()
        }

        override fun getItemCount(): Int {
            return titles.size
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}