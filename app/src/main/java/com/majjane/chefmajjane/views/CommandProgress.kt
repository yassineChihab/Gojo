package com.majjane.chefmajjane.views

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.majjane.chefmajjane.DisplayAllCommandesAdapter
import com.majjane.chefmajjane.R
import com.majjane.chefmajjane.databinding.FragmentCommandProgressBinding
import com.majjane.chefmajjane.network.CommandeApi
import com.majjane.chefmajjane.network.RemoteDataSource
import com.majjane.chefmajjane.repository.CommandeRepository
import com.majjane.chefmajjane.responses.CommandeResponseItem
import com.majjane.chefmajjane.responses.CommandeResponseNewItem
import com.majjane.chefmajjane.utils.Resource
import com.majjane.chefmajjane.utils.handleApiError
import com.majjane.chefmajjane.utils.startNewActivity
import com.majjane.chefmajjane.viewmodel.CommandeViewModel
import com.majjane.chefmajjane.viewmodel.SharedViewModel
import com.majjane.chefmajjane.views.activities.HomeActivity
import com.majjane.chefmajjane.views.base.BaseFragment


class CommandProgress :
    BaseFragment<CommandeViewModel, FragmentCommandProgressBinding, CommandeRepository>(){

    private  var navController: NavController?=null
    lateinit var sharedViewModel: SharedViewModel
    private  val TAG = "CommandProgress"

    private val adapter by lazy {
        DisplayAllCommandesAdapter()
    }
    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).apply {
            setHeaderVisibility(false)
            setToolbar("Temps d'arrive estime")
            setToolbarHeight(50)
            setToolbarVisibility(true)
            toolbarIcon?.setImageResource(R.drawable.back_arrow_ic)

        }

    }

    private fun geCustomerOrder(idLang: Int, idCutomer: Int) {
        viewModel.getCustomerOrder(idLang = 1, idCutomer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BottomSheetBehavior.from(binding.frameLayout).apply {
            peekHeight = 400
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        ((activity) as HomeActivity).toolbarIcon?.setOnClickListener {
            requireActivity().startNewActivity(HomeActivity::class.java)
        //  navController?.navigate(R.id.action_ProgressCommande_to_espaceSushiFragment)
        }
        binding.back.setOnClickListener{
            requireActivity().startNewActivity(HomeActivity::class.java)
        // navController?.navigate(R.id.action_ProgressCommande_to_espaceSushiFragment)
        }

        //  binding.progressBar9.max=1000
        geCustomerOrder(1,preferences.getIdCustomer())
        val currentProgress = 600
        ObjectAnimator.ofInt(binding.progressBar9,"progress",currentProgress)
            .setDuration(1800000)
            .start()
        navController=Navigation.findNavController(view)
         binding.recyclerView.adapter=adapter
        sharedViewModel.commandList.observe(viewLifecycleOwner, {
            adapter.setItems(it.toList())
        })

        sharedViewModel.selectedCity.observe(viewLifecycleOwner, {city->
            sharedViewModel.sharedTotalSum.observe(viewLifecycleOwner,{

                binding.deliveryCost.text = String.format("%.2f %s", city.price, "MAD")
                val total = it.plus(city.price)
                binding.totalPrice.text = String.format("%.2f %s", total, "MAD")
            })
        })
        binding.adrressLocal.text="gojo imm 42 Rabat Maroc"
        sharedViewModel.address.observe(viewLifecycleOwner,{
            binding.adresse.text=it.address
        })

        sharedViewModel.commandList.observe(viewLifecycleOwner,{
          var totalSum = 0.0
            it?.let { articleList ->
                articleList.forEach { article -> totalSum += article.prixTTC * article.selectedQuantity }
                sharedViewModel.sharedTotalSum.value = totalSum
                binding.apply {
                    articleTotalPrice.text = totalSum.toString() + " MAD"
                }
            }
        })
        viewModel.allCommandeLiveData.observe(viewLifecycleOwner,{
            when (it) {
                is Resource.Loading -> {
                    Log.d(TAG, "Loading ")

                }
                is Resource.Success -> {
                    Log.d(TAG, "data :${it.data}")
                      var MyCommde:CommandeResponseNewItem= it.data.last()
                      binding.state.text=MyCommde.order_status.name
                      binding.date.text=MyCommde.date
                      binding.numCmd.text=" Commande : " + MyCommde.name
                      binding.nbrCommande.text=MyCommde.name
                    }
                is Resource.Failure -> {

                    handleApiError(it) {

                    }
                }
            }
        })




    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommandProgressBinding = FragmentCommandProgressBinding.inflate(inflater, container, false)

    override fun createViewModel(): Class<CommandeViewModel> = CommandeViewModel::class.java

    override fun getFragmentRepository(): CommandeRepository =
        CommandeRepository(RemoteDataSource().buildApi(CommandeApi::class.java))


}