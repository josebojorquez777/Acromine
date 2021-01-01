package com.example.acromine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.acromine.adapters.AcroRecyclerViewAdapter
import com.example.acromine.databinding.ActivityMainBinding
import com.example.acromine.viewmodels.AcroViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: AcroViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(AcroViewModel::class.java)
        binding.adapter = AcroRecyclerViewAdapter()
        observeViewModel()
        binding.btnSearch.setOnClickListener {
            getAcroList()
        }
    }

    private fun observeViewModel() {
        viewModel.acroState.observe(this,
            { state ->
                when (state) {
                    is AcroViewModel.AcroState.Success -> {
                        (binding.rvLongForms.adapter as AcroRecyclerViewAdapter).setDefs(state.list)
                    }
                    is AcroViewModel.AcroState.Failure -> {
                        Toast.makeText(
                            baseContext,
                            getString(R.string.acro_failure),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
    }

    private fun getAcroList() {
        val acronymSearch = binding.etAcro.text.toString()
        if (acronymSearch.isNotEmpty()) {
            viewModel.getDefinitions(acronymSearch)
        }
    }
}