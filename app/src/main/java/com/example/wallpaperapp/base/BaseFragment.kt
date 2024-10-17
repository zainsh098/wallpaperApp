package com.example.wallpaperapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding//

/**
 * Generics play a crucial role in view binding, as they allow the binding class to work with different types of UI components. For example, the ActivityMainBinding class might be defined as a generic class, like this:
 *
 * class ActivityMainBinding<T : View> {
 *     val textView: TextView
 *     val button: Button
 *     val root: T
 * }
 *
 *
 */
abstract class BaseFragment<Binding : ViewBinding>(private val bindingInflater: (LayoutInflater) -> Binding) :
    Fragment() {
    protected lateinit var binding: Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater(inflater)
        return binding.root
    }


}