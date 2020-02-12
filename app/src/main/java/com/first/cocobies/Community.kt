package com.first.cocobies

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Community.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Community.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Community : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

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

        val view= inflater.inflate(R.layout.fragment_community, container, false)
        (activity as Homepage)
            .setActionBarTitle("CoCobies")

        val   c1 = view.findViewById<View>(R.id.farmercardid) as CardView
        val c2 = view.findViewById<View>(R.id.enterprisescarid) as CardView
        val c3 = view.findViewById<View>(R.id.researchcartid) as CardView
        val c4 = view.findViewById<View>(R.id.technicalcartid) as CardView
        val c5 = view.findViewById<View>(R.id.digitalcartid) as CardView
        val   c6 = view.findViewById<View>(R.id.consumercartid) as CardView

        val uptodown = AnimationUtils.loadAnimation(context,R.anim.updown);
        val downtoup = AnimationUtils.loadAnimation(context,R.anim.downtoup);


        c1.setAnimation(uptodown)
        c2.setAnimation(downtoup)
        c3.setAnimation(uptodown)
        c4.setAnimation(downtoup)
        c5.setAnimation(uptodown)
        c6.setAnimation(downtoup)
       // val fling = FlingAnimation(view, DynamicAnimation.SCROLL_X)
        c1.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Persons::class.java)
            startActivity(intent)
        })



        c2.setOnClickListener(View.OnClickListener {
            val fragment = Micro_enterprises.newInstance("", "")
            setme(fragment)
        })

        c3.setOnClickListener(View.OnClickListener {
            val fragment = Research.newInstance("", "")
            setme(fragment)
        })
        c4.setOnClickListener(View.OnClickListener {
//            val intent = Intent(context, Persons::class.java)
//            startActivity(intent)
            val fragment = Production.newInstance("", "")
            setme(fragment)
        })
        c5.setOnClickListener(View.OnClickListener {
//            val fragment = Micro_enterprices.newInstance("", "")
//            setme(fragment)
        })
        c6.setOnClickListener(View.OnClickListener {
//            val fragment = Micro_enterprices.newInstance("", "")
//            setme(fragment)
        })

        return view
    }
    private fun setme(f: Fragment) {
        val activity = context as AppCompatActivity
        val fm = activity.getSupportFragmentManager()
        val ft = fm.beginTransaction()
        ft.addToBackStack(null)
        ft.replace(R.id.frame, f)
        ft.commit()

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
//        else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Community.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Community().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
