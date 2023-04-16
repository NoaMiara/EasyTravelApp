package com.app.easy.travel.view.camera

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.adapter.ImageRecyclerViewAdapter
import com.app.easy.travel.databinding.FragmentCameraBinding
import com.app.easy.travel.databinding.FragmentHotelTicketBinding
import com.app.easy.travel.helpers.*
import com.app.easy.travel.intarface.RecyclerViewInterface
import com.app.easy.travel.model.Image
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import com.karumi.dexter.listener.PermissionRequest
import java.io.IOException
import java.io.OutputStream
import java.util.*

class CameraFragment : Fragment(), RecyclerViewInterface {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var saveImageToInternalStorage: Uri? = null
    private lateinit var recyclerview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerview = binding.rvTakePicture
        imageUpdate()
        return root
    }


    private fun imageUpdate() {
        recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            recyclerview.adapter = ImageRecyclerViewAdapter(imageData, this@CameraFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        imageManagement()
        binding.btnAddImage.setOnClickListener {
            alertDialogForImage()
        }


    }

    private fun imageManagement() {
        if (imageData.isEmpty()) {
            alertDialogForImage()
        } else {
            binding.btnAddImage.visibility = View.VISIBLE
        }

    }

    private fun alertDialogForImage() {
        val pictureDialog = AlertDialog.Builder(requireActivity())

        pictureDialog.setTitle("Select Action").setNegativeButton("Cancel") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val pictureDialogItems =
            arrayOf("Select photo from Gallery", "Capture photo from camera")

        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> photoFromCamera()
            }


        }.show()
    }


    private fun alertDialogForImageDelete(position: Int) {
        val pictureDialog = AlertDialog.Builder(requireActivity())

        pictureDialog.setTitle("Would you like to remove the image?")
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.setPositiveButton("Delete") { _, _ ->


                val image = imageData.removeAt(position)
                hotel.file?.remove(image.pid)
                recyclerview.adapter?.notifyItemRemoved(position)

            }.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    val uri = data.data
                    try {
                        val selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                            requireActivity().contentResolver,
                            uri)
                        saveImageToInternalStorage = saveImageRoInternalStorage(selectedImageBitmap)
                        saveImageOnObj(selectedImageBitmap)
                        imageUpdate()

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } else if (requestCode == CAMERA) {
                val pic: Bitmap = data!!.extras!!.get("data") as Bitmap
                saveImageToInternalStorage = saveImageRoInternalStorage(pic)
                saveImageOnObj(pic)
                imageUpdate()

            }
        }


    }
    private fun saveImageOnObj(selectedImageBitmap: Bitmap) {
        val imageId = "${UUID.randomUUID()}"
        imageData.add(Image(imageId, selectedImageBitmap))
        hotel.file?.put(imageId,imageId)
    }

    private fun photoFromCamera() {
        Dexter.withContext(requireActivity()).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA

        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report!!.areAllPermissionsGranted()) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA)
                }


            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken
            ) {
                showRationalDialogForPermission()

            }
        }).onSameThread().check()
    }


    private fun choosePhotoFromGallery() {
        Dexter.withContext(requireActivity()).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report!!.areAllPermissionsGranted()) {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY)
                    Toast.makeText(
                        requireActivity(),
                        "Now you can select an image from GALLERY",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken
            ) {
                showRationalDialogForPermission()
            }


        }).onSameThread().check()


    }

    private fun showRationalDialogForPermission() {
        AlertDialog.Builder(requireActivity())
            .setMessage("You turned off permission required for this feature. It can be enabled under the Applications setting ")
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }

            }.setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()

            }.show()

    }

    private fun saveImageRoInternalStorage(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(requireActivity())
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)


    }

    override fun onItemClick(delete: Boolean, position: Int) {
        alertDialogForImageDelete(position)
    }


}