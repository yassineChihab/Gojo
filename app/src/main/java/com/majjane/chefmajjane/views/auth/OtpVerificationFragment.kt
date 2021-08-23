package com.majjane.chefmajjane.views.auth

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.majjane.chefmajjane.R
import com.majjane.chefmajjane.databinding.FragmentOptVerificationBinding
import com.majjane.chefmajjane.network.AuthApi
import com.majjane.chefmajjane.network.RemoteDataSource
import com.majjane.chefmajjane.repository.AuthRepository
import com.majjane.chefmajjane.utils.Constants.Companion.PHONE_NUMBER_KEY
import com.majjane.chefmajjane.utils.Resource
import com.majjane.chefmajjane.utils.snackbar
import com.majjane.chefmajjane.utils.startNewActivity
import com.majjane.chefmajjane.utils.visible
import com.majjane.chefmajjane.viewmodel.AuthViewModel
import com.majjane.chefmajjane.views.activities.HomeActivity
import com.majjane.chefmajjane.views.base.BaseFragment


class OtpVerificationFragment :
    BaseFragment<AuthViewModel, FragmentOptVerificationBinding, AuthRepository>() {
    private var phoneNumberArg: String? = null
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getString("OTP_VERIFICATION", "00000000")?.let {
                phoneNumberArg = it
            }
        }
    }

    private val TAG = "OtpVerificationFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.tvPleaseTypeVerifCode.text =
            getString(R.string.please_type_code) + "\n " + getString(R.string.too)+" " + phoneNumberArg
        Log.d(TAG, "onViewCreated: $phoneNumberArg")
        binding.pinView.doOnTextChanged { text, _, _, _ ->
            text.let {
                if (it.toString().length == 5) {
                    Log.d(TAG, "onViewCreated: $it")
                      phoneNumberArg = phoneNumberArg?.removePrefix("+212")?.trim()
                       phoneNumberArg?.let { phoneNumber ->
                        Log.d(TAG, "onViewCreated: $phoneNumber")
                        viewModel.verifyOTP(
                            phoneNumber
                           ,"ma",
                            it.toString().toInt()

                        )
                    }
                }
            }
        }

        viewModel.verificationOtpResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visible(false)
                    if (it.data.success == 1) {
                        if (it.data.id_customer == null) {
                            navController.navigate(
                                R.id.action_optVerificationFragment_to_signUpFragment2,
                                bundleOf(
                                    PHONE_NUMBER_KEY to phoneNumberArg?.removePrefix("+212 ")
                                        ?.trim()
                                )                            )
                        } else {
                            preferences.saveIdCustomer(it.data.id_customer)
                            requireActivity().startNewActivity(HomeActivity::class.java)
                        }
                        return@observe
                    }
                    if (it.data.success == 0) {
                        requireView().snackbar(it.data.message)
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visible(true)
                }
                is Resource.Failure -> {
                    binding.progressBar.visible(false)
                   // Toast.makeText(requireContext(), "oups", Toast.LENGTH_SHORT).show()
                    requireView().snackbar(getString(R.string.went_wrong))
                }
            }
        })

        binding.tvResendCode.setOnClickListener {
            phoneNumberArg?.let { phoneNumber -> viewModel.sendOTP(phoneNumber,"ma") }
        }
        onBackPressed()
    }

    private fun onBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigate(R.id.action_optVerificationFragment_to_loginFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOptVerificationBinding =
        FragmentOptVerificationBinding.inflate(layoutInflater, container, false)

    override fun createViewModel(): Class<AuthViewModel> = AuthViewModel::class.java

    override fun getFragmentRepository(): AuthRepository =
        AuthRepository(RemoteDataSource().buildApi(AuthApi::class.java))

}