package com.example.todolist.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.RecyclerRowBinding


class postadapter(private val postList:ArrayList<Post>,private val callback:postAdaptercallback):RecyclerView.Adapter<postadapter.postHolder>() {
    class postHolder( val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){


    }

    interface  postAdaptercallback{
        fun ondeleteclicked(post: Post)
        fun oneditclicked(post:Post)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): postHolder {
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return postHolder(binding)
    }

    override fun getItemCount(): Int {
        return  postList.size
    }

    override fun onBindViewHolder(holder: postHolder, position: Int) {

        val commentpost=postList[position]
        holder.binding.recyclerText.text=commentpost.comment


        holder.binding.deletebtn.setOnClickListener{
            callback.ondeleteclicked(commentpost)

        }
        holder.binding.editbtn.setOnClickListener{
            callback.oneditclicked(commentpost)
        }




    }
}