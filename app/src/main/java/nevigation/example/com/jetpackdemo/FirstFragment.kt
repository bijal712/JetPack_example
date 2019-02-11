package nevigation.example.com.jetpackdemo


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.item_view.view.*
import nevigation.example.com.jetpackdemo.database.Person
import nevigation.example.com.jetpackdemo.database.PersonDatabase
import nevigation.example.com.jetpackdemo.ui.main.MainViewModel
import nevigation.example.com.jetpackdemo.utils.MyClickListener

class FirstFragment : Fragment(), View.OnClickListener, MyClickListener {
    private var userList: ArrayList<Person> = ArrayList()
    private var adapter: PersonAdapter? = null
    private var dbInstance: PersonDatabase? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAdd.setOnClickListener(this)
        btnGet.setOnClickListener(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.initialize(activity!!)


        rvGetData.layoutManager = LinearLayoutManager(activity!!)
        adapter = PersonAdapter(userList, this)
        rvGetData.adapter = adapter
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btnAdd -> {
                val name = etName.text.toString().trim()
                val age = etAge.text.toString().trim()

                if(!name.isEmpty() && !age.isEmpty()) {
                    val num = age.toInt()

                    val person = Person(null, name, num)
                    viewModel.insert(person)
                }else{
                    Toast.makeText(activity!!,"something is missing",Toast.LENGTH_LONG).show()
                }
            }
            R.id.btnGet -> {
                viewModel.gteAllTodo()?.observe(this, Observer {
                    if (it != null) {
                        userList.clear()
                        userList.addAll(it)
                        adapter?.notifyDataSetChanged()
                    }
                })
            }
        }


    }

    //adapter views click
    override fun myClick(view: View, position: Int) {
        when (view.id) {
            R.id.btnEdit -> {
                Toast.makeText(activity, "edit", Toast.LENGTH_LONG).show()

            }
            R.id.btnDelete -> {
                Toast.makeText(activity, "delete", Toast.LENGTH_LONG).show()

            }
        }
    }

    class PersonAdapter(val personList: ArrayList<Person>, val listener: MyClickListener) : RecyclerView.Adapter<PersonAdapter.MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
            return MyViewHolder(v)
        }

        override fun getItemCount(): Int {
            return personList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindItems(personList[position])
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            override fun onClick(view: View) {
                when (view.id) {
                    R.id.btnEdit, R.id.btnDelete -> listener.myClick(view, adapterPosition)
                }
            }

            fun bindItems(users: Person) {
                itemView.tvIndex.text = users.id.toString()
                itemView.tvFName.text = users.name
                itemView.tvLName.text = users.age.toString()
                itemView.btnEdit.setOnClickListener(this)
                itemView.btnDelete.setOnClickListener(this)
            }
        }
    }
}
