package com.majjane.chefmajjane.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.majjane.chefmajjane.R
import com.majjane.chefmajjane.databinding.FragmentCommandeConfirmBinding
import com.majjane.chefmajjane.databinding.FragmentMesCommandesBinding
import com.majjane.chefmajjane.network.CommandeApi
import com.majjane.chefmajjane.network.RemoteDataSource
import com.majjane.chefmajjane.repository.CommandeRepository
import com.majjane.chefmajjane.viewmodel.CommandeViewModel
import com.majjane.chefmajjane.views.activities.HomeActivity
import com.majjane.chefmajjane.views.base.BaseFragment


class CommandeConfirm :BaseFragment<CommandeViewModel,FragmentCommandeConfirmBinding,CommandeRepository>(){

    private lateinit var navController: NavController
    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).apply {
            setHeaderVisibility(false)
            setToolbar(" ")
            setToolbarHeight(50)


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Log.d("tag","back button pressed")    // Handle the back button event
        }

        callback.isEnabled
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.VoirCommandeButton.setOnClickListener{
            navController.navigate(R.id.action_voirCommande_to_fragmentProgress)

        }

    }


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommandeConfirmBinding = FragmentCommandeConfirmBinding.inflate(inflater, container, false)

    override fun createViewModel(): Class<CommandeViewModel> = CommandeViewModel::class.java

    override fun getFragmentRepository(): CommandeRepository =
        CommandeRepository(RemoteDataSource().buildApi(CommandeApi::class.java))

}