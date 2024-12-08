package com.example.todolist.fragmentler

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.navigation.Navigation
import com.example.todolist.R
import com.example.todolist.databinding.FragmentGirisfragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class girisfragment : Fragment() {

    private var _binding:FragmentGirisfragmentBinding? =null
    private val binding get()=_binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding = FragmentGirisfragmentBinding.inflate(inflater,container,false)
        val view=binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth=FirebaseAuth.getInstance()

        Handler(Looper.myLooper()!!).postDelayed({
            if (auth.currentUser !=null){
                val action= girisfragmentDirections.actionGirisfragmentToHome2()
                Navigation.findNavController(view).navigate(action)

            }else{
                val action=girisfragmentDirections.actionGirisfragmentToSignUp()
                Navigation.findNavController(view).navigate(action)

            }
        },6000)

        val textView = view.findViewById<TextView>(R.id.textView) // View'dan erişim sağlanır

        // Başlangıçta görünmez yap
        textView.visibility = View.INVISIBLE

        // Görünür yap ve animasyonu uygula
        textView.postDelayed({
            textView.visibility = View.VISIBLE
            val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            textView.startAnimation(fadeIn)
        }, 3000) // 500ms gecikme ile
    }










    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}