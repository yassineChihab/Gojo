package com.majjane.chefmajjane.views

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.majjane.chefmajjane.R
import com.majjane.chefmajjane.adapters.vosCommadeAdapter
import com.majjane.chefmajjane.databinding.FragmentVosCommandesBinding
import com.majjane.chefmajjane.network.CommandeApi
import com.majjane.chefmajjane.network.RemoteDataSource
import com.majjane.chefmajjane.repository.CommandeRepository
import com.majjane.chefmajjane.responses.CommandeResponseNewItem
import com.majjane.chefmajjane.utils.Constants
import com.majjane.chefmajjane.utils.Constants.Companion.COMMANDE_BUNDLE
import com.majjane.chefmajjane.utils.Resource
import com.majjane.chefmajjane.utils.handleApiError
import com.majjane.chefmajjane.utils.visible
import com.majjane.chefmajjane.viewmodel.CommandeViewModel
import com.majjane.chefmajjane.viewmodel.SharedViewModel
import com.majjane.chefmajjane.views.activities.HomeActivity
import com.majjane.chefmajjane.views.base.BaseFragment


class VosCommandes
    : BaseFragment<CommandeViewModel, FragmentVosCommandesBinding, CommandeRepository>() {

    private lateinit var navController: NavController
    lateinit var sharedViewModel: SharedViewModel
  //  private var commande: List<CommandeResponse>? = null
  private  val TAG = "VosCommandes"
    private val adapter by lazy {
        vosCommadeAdapter({commande,position ->onCommandeClicked(commande, position)})
    }

    private  fun onCommandeClicked(commande: CommandeResponseNewItem, position:Int){
        val bundle= bundleOf(COMMANDE_BUNDLE to commande)
        navController?.navigate(R.id.action_vosCommandeFragment_RepeteFragment,bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {

            sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        }
    }


    override fun onResume() {
        super.onResume()
        ((activity) as HomeActivity).apply {
            toolbarIcon?.setImageResource(R.drawable.back_arrow_ic)
            setToolbarHeight(50)
            this.setToolbar("Vos Commandes")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        geCustomerOrder(1,preferences.getIdCustomer())
        ((activity) as HomeActivity).toolbarIcon?.setOnClickListener {
            navController.navigate(R.id.action_vosCommandeFragment_to_homeFragment)
        }
        binding.vosCommandeRecycelerView.adapter=adapter

       viewModel.allCommandeLiveData.observe(viewLifecycleOwner,{
           when (it) {
               is Resource.Loading -> {
                   Log.d(TAG, "Loading ")
                   binding.progressBar2.visible(true)

               }
               is Resource.Success -> {
                   binding.progressBar2.visible(false)

                   Log.d(TAG, "data :${it.data}")
                   adapter.setItems(it.data)
               }
               is Resource.Failure -> {
                   binding.progressBar2.visible(false)

                   handleApiError(it) {

                   }
               }
           }
       })





    }

    private fun geCustomerOrder(idLang: Int, idCutomer: Int) {
         viewModel.getCustomerOrder(idLang = 1, idCutomer)
    }


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVosCommandesBinding = FragmentVosCommandesBinding.inflate(inflater, container, false)

    override fun createViewModel(): Class<CommandeViewModel> = CommandeViewModel::class.java

    override fun getFragmentRepository(): CommandeRepository =
        CommandeRepository(RemoteDataSource().buildApi(CommandeApi::class.java))


}