package br.dev.com.camera.ui.permissao

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.dev.com.camera.R
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


open class Permissao(var context: Context): AppCompatActivity() ,
    EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    private val RCCAMERAPERM = 123
    private val MENSAGEMPERMISSAOCAMERA = context.resources.getString(R.string.rationale_camera)
    //private var context = activity

    //Easypermission
    private fun hasCameraPermission():Boolean {
      return EasyPermissions.hasPermissions(context , Manifest.permission.CAMERA)
    }

    fun checkPermission():Boolean {

        return if (hasCameraPermission()){
            true
        }else{
            // Ask for one permission
            EasyPermissions.requestPermissions(context as Activity , MENSAGEMPERMISSAOCAMERA , RCCAMERAPERM , Manifest.permission.CAMERA)
            false
        }

    }

    override fun onRequestPermissionsResult(requestCode:Int,permissions:Array<String>,grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, context as Activity)
    }

    @SuppressLint("StringFormatMatches")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE)
        {
            val yes = getString(R.string.Sim)
            val no = getString(R.string.Não)
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                context,
                getString(R.string.returned_from_app_settings_to_activity,
                if (hasCameraPermission()) yes else no),
                Toast.LENGTH_LONG)
                .show()
        }
    }
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.e("TAG", "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.e("TAG", "onPermissionsDenied:" + requestCode + ":" + perms.size)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
        {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
    override fun onRationaleAccepted(requestCode: Int) {
        Log.e("TAG", "onRationaleAccepted:$requestCode")
    }
    override fun onRationaleDenied(requestCode: Int) {
        Log.e("TAG", "onRationaleDenied:$requestCode")
    }

}