package com.example.todolist.fragmentler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.todolist.R
import com.example.todolist.databinding.FragmentGirisfragmentBinding
import com.example.todolist.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUp : Fragment() {
    private var _binding: FragmentSignUpBinding? =null
    private val binding get()=_binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.kayitol.setOnClickListener { kayit(it) }
        binding.girisyap.setOnClickListener {  girisyap(it)}

        //Kullanıcı Giriş Yaptıysa
        val guncelkullanici=auth.currentUser
        if(guncelkullanici !=null){
            //kullanıcı daha önce giriş yapmış action ver
            val action=SignUpDirections.actionSignUpToHome2()
            Navigation.findNavController(view).navigate(action)
        }
    }
    fun kayit(view: View){

        val email=binding.signEmail.text.toString()
        val pass= binding.signpass.text.toString()

        if (email.isNotEmpty()&& pass.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    val action=SignUpDirections.actionSignUpToHome2()
                    Navigation.findNavController(view).navigate(action)
                }

            }.addOnFailureListener {
                    exception ->
                Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }



    }

    fun girisyap(view: View){

        val email=binding.signEmail.text.toString()
        val pass= binding.signpass.text.toString()
        if(email.isNotEmpty()&& pass.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener {
                val action=SignUpDirections.actionSignUpToHome2()
                Navigation.findNavController(view).navigate(action)
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()

            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }


}