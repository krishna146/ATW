package com.apnatuitionwale.atw.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.apnatuitionwale.atw.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPlay.setOnClickListener {
            val url =
                "https://firebasestorage.googleapis.com/v0/b/apna-tuition-wala.appspot.com/o/10th%2FMathematics%2F1NumberSystem%2FVID20220703213129%5B1%5D.mp4?alt=media&token=d347f376-265f-4fd1-bc9c-119f7290184b"
            val uri = Uri.parse(url)
            binding.videoView3.setVideoURI(uri)
            val mediaController = MediaController(requireContext())
            mediaController.setAnchorView(binding.videoView3)
            mediaController.setMediaPlayer(binding.videoView3)
            binding.videoView3.setMediaController(mediaController)
            binding.videoView3.start()



        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}