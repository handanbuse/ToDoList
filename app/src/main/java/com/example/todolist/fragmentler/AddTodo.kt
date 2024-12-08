package com.example.todolist.fragmentler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddTodoBinding
import com.example.todolist.utils.Post
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddTodo : Fragment() {
  private var _binding:FragmentAddTodoBinding? =null
    private val binding get()= _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=Firebase.auth
        db=Firebase.firestore

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding= FragmentAddTodoBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addbtn.setOnClickListener{addbtn(it)}
        binding.floatingActionButton2.setOnClickListener { gerıdon(it) }
    }

    fun addbtn(view: View){

        val addNote= binding.addEdittext.text.toString()
        if (addNote !=null){
            if(auth.currentUser !=null) {
                //veri tabanı kaydı yap
                val postMap= hashMapOf<String,Any>()
                postMap.put("email",auth.currentUser!!.email.toString())
                postMap.put("comment",binding.addEdittext.text.toString())
                postMap.put("date",Timestamp.now())

                db.collection("Posts").add(postMap).addOnSuccessListener {
                    documentReference ->
                    //veri database yüklendi
                    val action =AddTodoDirections.actionAddTodoToHome2()
                    Navigation.findNavController(view).navigate(action)
                    Toast.makeText(requireContext(),"veritabanına kayıt eklendi",Toast.LENGTH_LONG).show()

                }.addOnFailureListener { exception ->
                    Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()
                }

            }
        }


    }
    fun gerıdon(view: View){
        val action=AddTodoDirections.actionAddTodoToHome2()
        Navigation.findNavController(view).navigate(action)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



    }



