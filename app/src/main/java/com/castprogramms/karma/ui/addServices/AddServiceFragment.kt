package com.castprogramms.karma.ui.addServices

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentAddServiceBinding
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.time.TimeModule
import com.castprogramms.karma.ui.login.LoginFormState
import com.google.firebase.storage.FirebaseStorage
import org.koin.android.viewmodel.ext.android.viewModel

class AddServiceFragment: Fragment() {
    val storage = FirebaseStorage.getInstance("gs://karma-a96b8.appspot.com/")
    val ref = storage.reference
    val unit: Array<String> = arrayOf("Услуга", "День", "Час")
    lateinit var userIcon: ImageView
    var uri : Uri = Uri.parse("")
    private val addServiceViewModel : AddServiceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_service, container, false)
        val binding = FragmentAddServiceBinding.bind(view)
        userIcon = binding.userIcon
        userIcon.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.putExtra("return-data", true)
            startActivityForResult(intent, 1)
        }
        val user = addServiceViewModel.getUser()
        binding.acceptEdit.setOnClickListener {
            if(binding.nameService.text.toString().equals("") or binding.costService.text.toString().equals("") or
                binding.descService.text.toString().equals("") or binding.unitService.text.toString().equals("")) {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                if (user != null) {
                    addServiceViewModel.addService(
                        Service(
                            binding.nameService.text.toString(),
                            binding.costService.text.toString().toInt(),
                            binding.descService.text.toString(),
                            uri.toString(), user.uid, TimeModule.now(),
                            binding.unitService.text.toString(),
                        )
                    )
                    findNavController().navigate(R.id.action_addServiceFragment_to_profileFragment)
                }
            }
        }
        val adapterDrop = ArrayAdapter(requireContext(), R.layout.dropdown_item, unit)
        binding.unitService.setAdapter(adapterDrop)
        binding.unitService.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, dataImg: Intent?) {
        super.onActivityResult(requestCode, resultCode, dataImg)
        if (requestCode == 1 && dataImg != null) {
            val uri: Uri? = dataImg.data
            userIcon.setImageURI(uri)
            if (uri != null)
                load(uri)
        }
    }
    fun load(uri: Uri){
        ref.child(imagesTag + uri.lastPathSegment).putFile(uri)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e("data", uri.toString())
                    download(uri)
                }
                else
                    Log.e("data", it.exception?.message.toString())
            }
    }
    fun download(uri: Uri){
        ref.child(imagesTag + uri.lastPathSegment).downloadUrl
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e("data", it.result.toString())
                    this.uri = Uri.parse(it.result.toString())
                }
                else
                    Log.e("data", it.exception?.message.toString())
            }
    }
    companion object{
        const val imagesTag = "images/"
    }
}