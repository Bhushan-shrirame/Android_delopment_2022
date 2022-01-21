package com.bhushanshrirame.notesapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bhushanshrirame.notesapp.R
import com.bhushanshrirame.notesapp.activities.MainActivity
import com.bhushanshrirame.notesapp.databinding.BottomSheetLayoutBinding
import com.bhushanshrirame.notesapp.databinding.FragmentSaveOrDeleteBinding
import com.bhushanshrirame.notesapp.model.Note
import com.bhushanshrirame.notesapp.utils.hideKeyBoard
import com.bhushanshrirame.notesapp.viewModel.NoteActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.*


class SaveOrDeleteFragment : Fragment(R.layout.fragment_save_or_delete) {

    private lateinit var navController: NavController
    private lateinit var contentBinding: FragmentSaveOrDeleteBinding
    private var note: Note?=null
    private var color=-1
    private val noteActivityViewModel: NoteActivityViewModel by activityViewModels()
    private val currentDate = SimpleDateFormat.getInstance().format(Date())
    private val job = CoroutineScope(Dispatchers.Main)
    private val args: SaveOrDeleteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragment
            scrimColor = Color.TRANSPARENT
            duration = 300L


        }
        sharedElementEnterTransition = animation

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentBinding = FragmentSaveOrDeleteBinding.bind(view)

        navController = Navigation.findNavController(view)
        val activity = activity as MainActivity

        contentBinding.backBtn.setOnClickListener {
            requireView().hideKeyBoard()
            navController.popBackStack()
        }
        contentBinding.saveNote.setOnClickListener {
            savNote()

        }
        try {
            contentBinding.etNoteContent.setOnFocusChangeListener{_,hasFocus->
                if (hasFocus)
                {
                    contentBinding.bottomBar.visibility = View.VISIBLE
                    contentBinding.etNoteContent.setStylesBar(contentBinding.styleBar)
                }else contentBinding.bottomBar.visibility=View.GONE
            }

        }  catch (e:Throwable)
        {
            Log.d("Tag",e.stackTraceToString());
        }


         contentBinding.fabColorPick.setOnClickListener{
             val bottomSheetDialog = BottomSheetDialog(
                 requireContext(),
                 R.style.BottomSheetDialogTheme
             )
             val bottomSheetView:View = layoutInflater.inflate(
                 R.layout.bottom_sheet_layout,
                 null
             )
             with(bottomSheetDialog)
             {
                 setContentView(bottomSheetView)
                 show()
             }
             val bottomSheetBinding = BottomSheetLayoutBinding.bind(bottomSheetView)
             bottomSheetBinding.apply {
                 colorPicker.apply {
                     setSelectedColor(color)
                     setOnColorSelectedListener {
                         value->
                         color=value
                         contentBinding.apply {
                             noteContentFragmentParent.setBackgroundColor(color)
                             toolbarFragmentNoteContent.setBackgroundColor(color)
                             bottomBar.setBackgroundColor(color)
                             activity.window.statusBarColor=color
                         }
                         bottomSheetBinding.bottomSheetParent.setCardBackgroundColor(color)
                     }
                 }
                 bottomSheetParent.setCardBackgroundColor(color)
             }
             bottomSheetView.post{
                 bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
             }
         }

    }

    private fun savNote() {
        if (contentBinding.etNoteContent.text.toString().isEmpty() ||
                contentBinding.erTitle.text.toString().isEmpty())
        {
            Toast.makeText(activity,"Something is Empty",Toast.LENGTH_SHORT).show()
        }
        else
        {
            note=args.note
            when(note){
                null-> {
                    noteActivityViewModel.saveNote(
                        Note(
                            0,
                            contentBinding.erTitle.text.toString(),
                            contentBinding.etNoteContent.getMD(),
                            currentDate,
                            color

                        )
                    )
                    navController.navigate(SaveOrDeleteFragmentDirections.actionSaveOrDeleteFragmentToNoteFragment2())
                }
                else ->
                {
                    //update note
                }

            }

        }
    }


}