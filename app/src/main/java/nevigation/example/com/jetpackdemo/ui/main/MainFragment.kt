package nevigation.example.com.jetpackdemo.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.main_fragment.*
import nevigation.example.com.jetpackdemo.R

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
        btnFirst.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_firstFragment2) //action id
        }

        btnSecond.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_secondFragment)
        }
    }
}
