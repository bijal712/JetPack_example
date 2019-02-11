package nevigation.example.com.apicall.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_view.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import nevigation.example.com.apicall.R
import nevigation.example.com.apicall.UserModel


class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var usersList: List<UserModel>? = ArrayList()
    private var adapter: UserAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(nevigation.example.com.apicall.R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        rvGetData.layoutManager = LinearLayoutManager(activity!!)
        adapter = UserAdapter(usersList)
        rvGetData.adapter = adapter

        viewModel.getPhotographer().observe(this, Observer<List<UserModel>> { usersData ->
            usersList = usersData
            adapter?.notifyDataSetChanged()
            Log.d("TAG", "success $usersData")
        })    }

    class UserAdapter(private val usersList: List<UserModel>?) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
            return MyViewHolder(v)
        }

        override fun getItemCount(): Int {
            return usersList!!.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            if (usersList?.isNotEmpty()!!) {
                holder.bindItems(usersList[position])
            }
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bindItems(users: UserModel) {
                itemView.tvTitle.text = users.title
            }
        }
    }
}
