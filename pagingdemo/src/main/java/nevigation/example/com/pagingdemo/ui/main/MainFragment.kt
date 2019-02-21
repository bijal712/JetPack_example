package nevigation.example.com.pagingdemo.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedListAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.main_fragment.*
import nevigation.example.com.pagingdemo.R
import nevigation.example.com.pagingdemo.paging.Cheese


class MainFragment : Fragment() {
    private var adapter : CheeseAdapter ?= null
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(nevigation.example.com.pagingdemo.R.layout.main_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = CheeseAdapter()

        viewModel.initialize(activity!!).observe(this, Observer {
            rvCheese.adapter = adapter
            adapter?.submitList(it)
            Log.d("pagelist",it.toString())
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addButton.setOnClickListener {
           addCheese()
        }

        // when the user taps the "Done" button in the on screen keyboard, save the item.
        inputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addCheese()
                return@setOnEditorActionListener true
            }
            false // action that isn't DONE occurred - ignore
        }
        // When the user clicks on the button, or presses enter, save the item.
        inputText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addCheese()
                return@setOnKeyListener true
            }
            false // event that isn't DOWN or ENTER occurred - ignore
        }

        initSwipeToDelete()

    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CheeseAdapter.MyViewHolder).cheese?.let {
                    viewModel.remove(it)
                }
            }
        }).attachToRecyclerView(rvCheese)
    }


    private fun addCheese() {
        val newCheese = inputText.text.trim()
        if (newCheese.isNotEmpty()) {
            viewModel.insert(newCheese)
            inputText.setText("")
        }

    }


    class CheeseAdapter : PagedListAdapter<Cheese, CheeseAdapter.MyViewHolder>(diffCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
                MyViewHolder(parent)


        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindTo(getItem(position)!!)
        }

        class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.cheese_item, parent, false)) {
            private val nameView = itemView.findViewById<TextView>(R.id.name)
            var cheese: Cheese? = null

            /**
             * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
             * ViewHolder when Item is loaded.
             */
            fun bindTo(cheese: Cheese?) {
                this.cheese = cheese
                nameView.text = cheese?.name
            }
        }

        companion object {
            private val diffCallback = object : DiffUtil.ItemCallback<Cheese>() {
                override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                        oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                        oldItem == newItem
            }
        }
    }
}
