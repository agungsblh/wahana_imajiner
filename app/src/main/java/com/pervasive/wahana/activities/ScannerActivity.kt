package com.pervasive.wahana.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.pervasive.wahana.databinding.ActivityScannerBinding
import java.security.AccessController.getContext


class ScannerActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var binding:ActivityScannerBinding
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAndroidVersion()

        codeScanner = CodeScanner(this, binding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        onAction()
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan: ${it.text}", Toast.LENGTH_LONG).show()
                /////////////////////
                if (it.text.toString().length==10){
                    when(it.text){
//                        "MSK-123-WH"->{
//                            //kalo ada tiket di database, boleh masuk
//
//                            //else
//
//                            //
//                        }
                        "WHN-001-RH"->{
                            val i = Intent(this,WahanaScanningActivity::class.java)
                            i.putExtra("state","CREATE")
                            i.putExtra("kode","WHN-001-RH")
                            startActivity(i)
                            finish()
                        }
                        "WHN-002-RC"->{
                            val i = Intent(this,WahanaScanningActivity::class.java)
                            i.putExtra("state","CREATE")
                            i.putExtra("kode","WHN-002-RC")
                            startActivity(i)
                            finish()
                        }
                        "WHN-003-KP"->{
                            val i = Intent(this,WahanaScanningActivity::class.java)
                            i.putExtra("state","CREATE")
                            i.putExtra("kode","WHN-003-KP")
                            startActivity(i)
                            finish()
                        }
                        "R01-001-RA"->{
                            val i = Intent(this,RestaurantScanningActivity::class.java)
                            i.putExtra("resto",1)
                            i.putExtra("meja",1)
                            startActivity(i)
                            finish()
                        }
                        "R02-001-RD"->{
                            val i = Intent(this,RestaurantScanningActivity::class.java)
                            i.putExtra("resto",2)
                            i.putExtra("meja",1)
                            startActivity(i)
                            finish()
                        }
                        "R01-002-RA"->{
                            val i = Intent(this,RestaurantScanningActivity::class.java)
                            i.putExtra("resto",1)
                            i.putExtra("meja",2)
                            startActivity(i)
                            finish()
                        }
                        "R02-002-RD"->{
                            val i = Intent(this,RestaurantScanningActivity::class.java)
                            i.putExtra("resto",2)
                            i.putExtra("meja",2)
                            startActivity(i)
                            finish()
                        }
                        "R01-003-RA"->{
                            val i = Intent(this,RestaurantScanningActivity::class.java)
                            i.putExtra("resto",1)
                            i.putExtra("meja",3)
                            startActivity(i)
                            finish()
                        }
                        "R02-003-RD"->{
                            val i = Intent(this,RestaurantScanningActivity::class.java)
                            i.putExtra("resto",2)
                            i.putExtra("meja",3)
                            startActivity(i)
                            finish()
                        }
                        "R01-004-RA"->{
                            val i = Intent(this,RestaurantScanningActivity::class.java)
                            i.putExtra("resto",1)
                            i.putExtra("meja",4)
                            startActivity(i)
                            finish()
                        }
                        "R02-004-RD"->{
                            val i = Intent(this,RestaurantScanningActivity::class.java)
                            i.putExtra("resto",2)
                            i.putExtra("meja",4)
                            startActivity(i)
                            finish()
                        }
                        "R01-005-RA"->{
                            val i = Intent(this,RestaurantScanningActivity::class.java)
                            i.putExtra("resto",1)
                            i.putExtra("meja",5)
                            startActivity(i)
                            finish()
                        }
                        "R02-005-RD"->{
                            val i = Intent(this,RestaurantScanningActivity::class.java)
                            i.putExtra("resto",2)
                            i.putExtra("meja",5)
                            startActivity(i)
                            finish()
                        }
                        "VMC-001-MK"->{
                            //bayar vending machine makanan
                        }
                        "VMC-002-MN"->{
                            //bayar vending machine minuman
                        }
                    }
                }
                //action
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun onAction(){
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
    override fun onResume() {
        super.onResume()

        codeScanner.startPreview()
    }
    private fun checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions()
        } else {
            // code for lollipop and pre-lollipop devices
        }
    }
    private fun checkAndRequestPermissions(): Boolean {
        val camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray<String>(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }


}