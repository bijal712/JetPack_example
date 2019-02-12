package nevigation.example.com.pagingdemo.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nevigation.example.com.pagingdemo.R
import nevigation.example.com.pagingdemo.paging.Cheese

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


    }

    class CheeseAdapter : PagedListAdapter<Cheese,CheeseAdapter.MyViewHolder>(diffCallback){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
            MyViewHolder(parent)


        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindTo(getItem(position))
        }

        class MyViewHolder(parent : ViewGroup): RecyclerView.ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.cheese_item, parent, false)
        ) {

            private val nameView = itemView.findViewById<TextView>(R.id.name)
            var cheese : Cheese? = null

            fun bindTo(item: Cheese?) {
                this.cheese = item
                nameView.text = item?.name
            }
        }

        companion object {
            /**
             * This diff callback informs the PagedListAdapter how to compute list differences when new
             * PagedLists arrive.
             * <p>
             * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
             * detect there's only a single item difference from before, so it only needs to animate and
             * rebind a single view.**/
            private val diffCallback = object : DiffUtil.ItemCallback<Cheese>() {
                override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                        oldItem.id == newItem.id

                /**
                 * Note that in kotlin, == checking on data classes compares all contents, but in Java,
                 * typically you'll implement Object#equals, and use it to compare object contents.
                 */
                override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                        oldItem == newItem
            }
        }
    }
}
