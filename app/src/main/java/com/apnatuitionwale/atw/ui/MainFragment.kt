package com.apnatuitionwale.atw.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.apnatuitionwale.atw.R

import com.apnatuitionwale.atw.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        //return binding.root

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.course -> replaceFragment( CourseFragment())

                R.id.qna -> replaceFragment(QAFragment())

                R.id.profile -> replaceFragment(ProfileFragment())

            }
        true
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment(fragment:Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frameLayout, fragment)
        transaction?.disallowAddToBackStack()
        transaction?.commit()
    }
}