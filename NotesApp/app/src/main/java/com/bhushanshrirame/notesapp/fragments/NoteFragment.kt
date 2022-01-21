package com.bhushanshrirame.notesapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.bhushanshrirame.notesapp.R
import com.bhushanshrirame.notesapp.activities.MainActivity
import com.bhushanshrirame.notesapp.databinding.FragmentNoteBinding
import com.bhushanshrirame.notesapp.utils.hideKeyBoard
import com.bhushanshrirame.notesapp.viewModel.NoteActivityViewModel
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NoteFragment : Fragment(R.layout.fragment_note) {


    private lateinit var noteBinding: FragmentNoteBinding
    private val noteActivityViewModel:NoteActivityViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(false).apply {
            duration = 350
        }
        enterTransition = MaterialElevationScale(true).apply {
            duration = 350
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteBinding = FragmentNoteBinding.bind(view)
        val activity  = activity as MainActivity
        val navController = Navigation.findNavController(view)
        requireView().hideKeyBoard()
        CoroutineScope(Dispatchers.Main).launch {
            delay(10)
            activity.window.statusBarColor = Color.WHITE
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.statusBarColor= Color.parseColor("#9E9D9D")
        }

        noteBinding.addNoteFab.setOnClickListener{
            noteBinding.appBarLayout.visibility = View.INVISIBLE
            navController.navigate(NoteFragmentDirections.actionNoteFragmentToSaveOrDeleteFragment2())

        }
        noteBinding.innerFab.setOnClickListener{
            noteBinding.appBarLayout.visibility = View.INVISIBLE
            navController.navigate(NoteFragmentDirections.actionNoteFragmentToSaveOrDeleteFragment2())

        }




    }
}