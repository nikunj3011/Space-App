package space.ui.characterinfo

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import space.databinding.FragmentInfoBottomSheetBinding

class InfoBottomSheetFragment :  BottomSheetDialogFragment() {

    private lateinit var binding: FragmentInfoBottomSheetBinding
    private lateinit var info : String

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding = FragmentInfoBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        binding = dataBinding
        info = arguments?.getString("info").toString()
        if(info == null)
        {
            info = "-"
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.textViewInfoCharacters.text =
                Html.fromHtml(info, Html.FROM_HTML_MODE_COMPACT);
        } else {
            binding.textViewInfoCharacters.text = Html.fromHtml(info);
        }
        view?.parent
        return binding.root
    }

}