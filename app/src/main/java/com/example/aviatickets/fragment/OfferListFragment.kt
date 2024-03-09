package com.example.aviatickets.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aviatickets.R
import com.example.aviatickets.adapter.OfferListAdapter
import com.example.aviatickets.databinding.FragmentOfferListBinding
import com.example.aviatickets.model.entity.Offer
import com.example.aviatickets.model.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OfferListFragment : Fragment() {

    companion object {
        fun newInstance() = OfferListFragment()
    }

    private var _binding: FragmentOfferListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: OfferListAdapter by lazy {
        OfferListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferListBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.offerList.layoutManager = LinearLayoutManager(requireContext())
        binding.offerList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.offerList.adapter = adapter

        setupUI()
        fetchOfferList()
    }

    /**
     * Task 1 Implementation
     */
    private fun fetchOfferList() {
        val client = ApiClient.offerServiceInstance
        client.fetchOfferList().enqueue(object : Callback<List<Offer>> {

            override fun onResponse(call: Call<List<Offer>>, response: Response<List<Offer>>) {
                if (response.isSuccessful) {
                    val offerList = response.body()
                    if (offerList != null) {
                        adapter.submitList(offerList)
                    } else {
                        Log.e(TAG, "Response body is null")
                    }
                } else {
                    Log.e(TAG, "Failed to fetch data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Offer>>, t: Throwable) {
                Log.e(TAG, "Error fetching offer list: ${t.message}")
            }
        })
    }


    private fun setupUI() {
        with(binding) {
            offerList.adapter = adapter

            sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.sort_by_price -> {
                        adapter.sortByPrice()
                    }

                    R.id.sort_by_duration -> {
                        adapter.sortByDuration()
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}