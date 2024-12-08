package com.example.todolist.fragmentler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.utils.Post
import com.example.todolist.utils.postadapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Home : Fragment(),PopupMenu.OnMenuItemClickListener, postadapter.postAdaptercallback{

    private var _binding:FragmentHomeBinding? =null
    private val binding get()=_binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var popup:PopupMenu

    val posList:ArrayList<Post> =ArrayList()

    private var adapter :postadapter? =null






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth
        db= Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentHomeBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener { addbtn(it) }

        popup=PopupMenu(requireContext(),binding.floatingActionButton)
        val inflater =popup.menuInflater // bağlandı
        inflater.inflate(R.menu.popupmenu,popup.menu)
        popup.setOnMenuItemClickListener(this)

        firebaseverileriAl()

        adapter=postadapter(posList,this)
        binding.recyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerview.adapter=adapter



    }
    fun addbtn(view: View){
             popup.show()




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
      if(item?.itemId==R.id.ekle){
          val action= HomeDirections.actionHome2ToAddTodo()
          Navigation.findNavController(requireView()).navigate(action)

      } else if (item?.itemId==R.id.cikisyap){
          //çıkış yapma işlemi
          auth.signOut()
          val action =HomeDirections.actionHome2ToSignUp2()
          Navigation.findNavController(requireView()).navigate(action)
      }
        return true
    }

    private fun firebaseverileriAl(){

        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error !=null){
                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{

                if(value !=null){
                    if(!value.isEmpty){
                        //boş değilse
                        posList.clear()

                        val documents=value.documents // dokümanları al
                        for (document in documents){
                            val comment= document.get("comment") as String
                            val email= document.get("email") as String
                            val id=document.id
                            val post= Post(comment, email,id)
                            posList.add(post)
                        }

                        adapter?.notifyDataSetChanged()


                    }
                }
            }

        }
        

    }

    override fun ondeleteclicked(post: Post) {
        db.collection("Posts").document(post.id).delete().addOnSuccessListener {
            posList.remove(post)
            adapter?.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Silindi", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            exception ->
            Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

    override fun oneditclicked(post: Post) {
        val action=HomeDirections.actionHome2ToAddTodo()
        Navigation.findNavController(requireView()).navigate(action)

    }


}