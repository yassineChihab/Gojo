package com.majjane.chefmajjane.views

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.majjane.chefmajjane.DisplayAllCommandesAdapter
import com.majjane.chefmajjane.R
import com.majjane.chefmajjane.databinding.FragmentCommandProgressBinding
import com.majjane.chefmajjane.databinding.FragmentRepeteCommadneBinding
import com.majjane.chefmajjane.network.CommandeApi
import com.majjane.chefmajjane.network.RemoteDataSource
import com.majjane.chefmajjane.repository.CommandeRepository
import com.majjane.chefmajjane.responses.Article
import com.majjane.chefmajjane.responses.ArticleX
import com.majjane.chefmajjane.responses.CommandeResponseNewItem
import com.majjane.chefmajjane.responses.orderDetailResponseItem
import com.majjane.chefmajjane.utils.Constants.Companion.ARTICLEX_BUNDLE
import com.majjane.chefmajjane.utils.Constants.Companion.COMMANDE_BUNDLE
import com.majjane.chefmajjane.utils.Resource
import com.majjane.chefmajjane.utils.handleApiError
import com.majjane.chefmajjane.viewmodel.CommandeViewModel
import com.majjane.chefmajjane.viewmodel.SharedViewModel
import com.majjane.chefmajjane.views.activities.HomeActivity
import com.majjane.chefmajjane.views.base.BaseFragment


class RepeteCommadne :
    BaseFragment<CommandeViewModel, FragmentRepeteCommadneBinding, CommandeRepository>() {
    private var navController: NavController? = null
    lateinit var sharedViewModel: SharedViewModel

    private var commande: CommandeResponseNewItem? = null
    private val TAG = "RepeteCommadne"

    private val adapter by lazy {
        DisplayAllCommandesAdapterX()
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).apply {
            setHeaderVisibility(true)
            setToolbar("Votre Commande")
            setToolbarHeight(50)
            toolbarIcon?.setImageResource(R.drawable.back_arrow_ic)

        }

    }

    /*private fun geCustomerOrder(idLang: Int, idCutomer: Int) {
        viewModel.getCustomerOrder(idLang = 1, idCutomer)
    }*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ((activity) as HomeActivity).toolbarIcon?.setOnClickListener {
            navController?.navigate(R.id.action_RepeteCommande_to_mesCommande)
        }

        orderDetail(commande?.id!!, preferences.getIdCustomer(), 1)
        viewModel.orderDetailLivaData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    Log.d(TAG, "Loading ")

                }
                is Resource.Success -> {
                    Log.d(TAG, "data :${it.data}")
                    binding.state.text = it.data.state
                    binding.date.text = it.data.date
                    binding.numCmd.text = " Commande : " + it.data.num
                    binding.nbrCommande.text = it.data.num
                    binding.adresse.text = it.data.address
                    binding.deliveryCost.text = it.data.total_shipping.toString() + "MAD"
                    binding.totalPrice.text = it.data.total_paid.toString() + "MAD"
                    binding.articleTotalPrice.text = it.data.total_products.toString() + "MAD"
                    binding.adrressLocal.text="gojo imm 42 Rabat Maroc"
                    binding.nbrCommande.text=it.data.articles.size.toString()+"articles"
                    adapter.setItems(it.data.articles)
                    var  listArticle=ArrayList(it.data.articles)
                    Log.d(TAG, "onViewCreated: {${it.data}}")
                    binding.RepeteCommande.setOnClickListener {
                        val bundle= bundleOf(ARTICLEX_BUNDLE to listArticle )
                        navController?.navigate(R.id.action_RepeteCommande_to_ConfirmFragment,bundle)
                        sharedViewModel.commandListX.value =listArticle.toList()
                    }
                }
                is Resource.Failure -> {

                    handleApiError(it) {

                    }
                }
            }
        })

        //  binding.progressBar9.max=1000
        //     geCustomerOrder(1,preferences.getIdCustomer())

        navController = Navigation.findNavController(view)
        binding.recyclerView.adapter = adapter



    }

    private fun orderDetail(idOrder: Int, idCustomer: Int, idLang: Int) {
        viewModel.getOrderDetail(idOrder, idCustomer, idLang = 1)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commande = requireArguments().getParcelable(COMMANDE_BUNDLE)
        Log.d(TAG, "onCreate: {${commande?.name}}")
        activity?.run {
            sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        }

    }


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRepeteCommadneBinding =
        FragmentRepeteCommadneBinding.inflate(inflater, container, false)

    override fun createViewModel(): Class<CommandeViewModel> = CommandeViewModel::class.java

    override fun getFragmentRepository(): CommandeRepository =
        CommandeRepository(RemoteDataSource().buildApi(CommandeApi::class.java))


}