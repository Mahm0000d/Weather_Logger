package com.example.weatherlogger.ui.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.weatherlogger.R
import com.example.weatherlogger.databinding.MainBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() ,View.OnClickListener{
    private val mainViewModel : MainViewModel by viewModels()
    private var bottomSheetInterface: BottomSheetInterface?=null


    lateinit var customBottomSheetBinding: MainBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        customBottomSheetBinding= DataBindingUtil.inflate<MainBottomSheetBinding>(inflater,R.layout.main_bottom_sheet, container, false)
       val view:View =customBottomSheetBinding.root
        customBottomSheetBinding.refreshLayout.setOnClickListener(this)
        customBottomSheetBinding.deleteLayout.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        dismiss()
        when(v!!.id){
            R.id.refresh_layout-> bottomSheetInterface!!.refresh()
            R.id.delete_layout->bottomSheetInterface!!.delete()


        }
    }

    interface BottomSheetInterface{
        fun refresh()
        fun delete()
    }

    fun setOnBottomSheetListener(bottomSheetInterface: BottomSheetInterface?){
        this.bottomSheetInterface=bottomSheetInterface
    }

}