package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.AsteroidApplicationClass
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapters.AsteroidRecyclerViewAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.models.domainmodels.Asteroid
import com.udacity.asteroidradar.utils.Resource

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels{MainViewModelFactory(AsteroidApplicationClass())}
    private lateinit var adapter : AsteroidRecyclerViewAdapter
    private lateinit var binding : FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        setupRecyclerView()
        observeMutables()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter= AsteroidRecyclerViewAdapter().apply {
            setOnItemClickListener {
                navigate(it)
            }
        }

        binding.asteroidRecycler.adapter = adapter
    }

    private fun navigate(asteroid: Asteroid) {

        findNavController().navigate(R.id.action_mainFragment_to_detailFragment,Bundle().apply {
            putParcelable("selectedAsteroid", asteroid)
        } )
    }

    private fun observeMutables() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.statusLoadingWheel.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.statusLoadingWheel.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success ->{
                    binding.statusLoadingWheel.visibility= View.GONE
                }
            }
        })

        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.differ.submitList(it)
            }
        })

        viewModel.pictureOfDay.observe(viewLifecycleOwner, Observer {
            it?.let {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
