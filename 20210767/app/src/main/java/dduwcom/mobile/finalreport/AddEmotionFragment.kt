package dduwcom.mobile.finalreport

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class AddEmotionFragment : DialogFragment() {

    companion object {
        fun newInstance(): AddEmotionFragment {
            return AddEmotionFragment()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_add_emotion, null)

        // ImageButton 클릭 이벤트 처리
        val imageButton1: ImageButton = view.findViewById(R.id.imageButton1)
        val imageButton2: ImageButton = view.findViewById(R.id.imageButton2)
        val imageButton3: ImageButton = view.findViewById(R.id.imageButton3)
        val imageButton4: ImageButton = view.findViewById(R.id.imageButton4)
        val imageButton5: ImageButton = view.findViewById(R.id.imageButton5)
        val imageButton6: ImageButton = view.findViewById(R.id.imageButton6)

        imageButton1.setOnClickListener {
            val emotion = R.mipmap.emotion1
            (requireActivity() as MainActivity).openAddActivity(emotion)
            dismiss()
        }

        imageButton2.setOnClickListener {
            val emotion = R.mipmap.emotion2
            (requireActivity() as MainActivity).openAddActivity(emotion)
            dismiss()
        }

        imageButton3.setOnClickListener {
            val emotion = R.mipmap.emotion3
            (requireActivity() as MainActivity).openAddActivity(emotion)
            dismiss()
        }

        imageButton4.setOnClickListener {
            val emotion = R.mipmap.emotion4
            (requireActivity() as MainActivity).openAddActivity(emotion)
            dismiss()
        }

        imageButton5.setOnClickListener {
            val emotion = R.mipmap.emotion5
            (requireActivity() as MainActivity).openAddActivity(emotion)
            dismiss()
        }

        imageButton6.setOnClickListener {
            val emotion = R.mipmap.emotion6
            (requireActivity() as MainActivity).openAddActivity(emotion)
            println("feeling: $emotion ")
            dismiss()
        }


        builder.setView(view)
        return builder.create()
    }
}


