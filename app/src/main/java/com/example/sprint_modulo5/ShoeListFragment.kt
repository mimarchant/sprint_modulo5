package com.example.sprint_modulo5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlin.math.abs
import com.example.sprint_modulo5.databinding.FragmentShoeListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class ShoeListFragment : Fragment() {
    private var binding: FragmentShoeListBinding? = null
    private var param1: String? = null
    private var param2: String? = null
    private var dX = 0f
    private var dY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShoeListBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeDraggable()
        initAdapter()
        showShoppingCart()
    }

    private fun initAdapter() {
        val adapter = Adapter()
        adapter.setFragment(this)
        val shoes = ShoeList.getShoes()
        adapter.setData(shoes)
        binding?.recyclerView?.adapter = adapter
    }

    private fun makeDraggable() {
        binding?.showCart?.let { showCart ->
            var downRawX = 0f
            var downRawY = 0f
            var upRawX: Float
            var upRawY: Float

            showCart.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downRawX = event.rawX
                        downRawY = event.rawY
                        dX = v.x - downRawX
                        dY = v.y - downRawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        var newX = event.rawX + dX
                        var newY = event.rawY + dY

                        // Restrict movement within the parent view (container)
                        if (newX < 0) {
                            newX = 0f
                        } else if (newX > binding?.root?.width!! - v.width) {
                            newX = (binding?.root?.width!! - v.width).toFloat()
                        }

                        if (newY < 0) {
                            newY = 0f
                        } else if (newY > binding?.root?.height!! - v.height) {
                            newY = (binding?.root?.height!! - v.height).toFloat()
                        }

                        v.animate()
                            .x(newX)
                            .y(newY)
                            .setDuration(0)
                            .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        upRawX = event.rawX
                        upRawY = event.rawY

                        val diffX = upRawX - downRawX
                        val diffY = upRawY - downRawY

                        // Consider it a click if user didn't move much
                        if (abs(diffX) < CLICK_DRAG_TOLERANCE && abs(diffY) < CLICK_DRAG_TOLERANCE) {
                            v.performClick()
                        }
                    }
                }
                true
            }
        }
    }

    companion object {
        // Tolerance in px when checking whether it's a click or drag event
        const val CLICK_DRAG_TOLERANCE = 10
    }


    private fun showShoppingCart() {
        binding?.showCart?.setOnClickListener {
            Navigation.findNavController(binding?.showCart!!).navigate(R.id.action_shoeListFragment_to_shoppingCart)
        }
    }



}