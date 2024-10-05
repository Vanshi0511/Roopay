package com.vanshika.roopay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vanshika.roopay.databinding.FragmentHomeBinding // Import the binding class

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // Create a non-nullable property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false) // Inflate the binding
        return binding.root // Return the root view of the binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSliderAdapter() // Call to set the slider adapter after view is created
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding reference when the view is destroyed
    }

    private fun setSliderAdapter() {
        val imageList: MutableList<Any> = ArrayList()

        // Add images to the list
        imageList.add(R.drawable.img) // Drawable resource
        imageList.add(R.drawable.image_2)

        // Create the adapter using the activity context
        val adapter = context?.let { ImageSliderAdapter(it, imageList) }

        // Set the adapter to the slider
        if (adapter != null) {
            binding.imageSlider.setSliderAdapter(adapter)
        } // Use binding to access imageSlider
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {

            }
    }
}
