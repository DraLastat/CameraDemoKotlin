package com.cs.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cs.camera.camera1.CameraActivity
import com.cs.camera.camera2.CameraActivity2
import com.cs.camera.camera2.CameraActivity2Face
import com.cs.camera.util.PermissionUtils
import com.cs.camera.util.PermissionUtils.PERMISSION_REQUEST_CODE
import com.cs.camera.util.PermissionUtils.PERMISSION_SETTING_CODE
import com.cs.camera.util.ViewClickManager
import kotlinx.android.synthetic.main.activity_camera2_face.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val permissionsList = arrayOf(Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
//    private val viewClickManager = ViewClickManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //btCapture = (Button)findViewById(R.id.btCapture);

        btCapture.setOnClickListener {
            PermissionUtils.checkPermission(this, permissionsList, Runnable {
                startActivity(Intent(this, CaptureActivity::class.java))
            })
        }
        btCamera.setOnClickListener {
            PermissionUtils.checkPermission(this, permissionsList, Runnable {
                val intent = Intent(this, CameraActivity::class.java)
                intent.putExtra(CameraActivity.TYPE_TAG, CameraActivity.TYPE_CAPTURE)
                startActivity(intent)
            })
        }
        btCameraRecord.setOnClickListener {
            PermissionUtils.checkPermission(this, permissionsList, Runnable {
                val intent = Intent(this, CameraActivity::class.java)
                intent.putExtra(CameraActivity.TYPE_TAG, CameraActivity.TYPE_RECORD)
                startActivity(intent)
            })
        }

        btCamera2.setOnClickListener {
            PermissionUtils.checkPermission(this, permissionsList, Runnable {
                val intent = Intent(this, CameraActivity2::class.java)
                startActivity(intent)
            })
        }

        btCamera2Face.setOnClickListener {
            PermissionUtils.checkPermission(this, permissionsList, Runnable {
                startActivity(Intent(this, CameraActivity2Face::class.java))
            })
        }

//        viewClickManager.setClickLister(btCamera2Face, object : ViewClickManager.OnClickListener {
//            override fun onSingleClick() {
//                log("single click")
//                //value = "btn1 single click"
//                //Utils.toast(this@MainActivity, value)
//            }
//
//            override fun onDoubleClick() {
//                //Utils.toast(this@MainActivity, "btn1 double click:" + value)
//                log("btn1 double click")
//            }
//        })

        PermissionUtils.checkPermission(this, permissionsList, Runnable {
        })
    }


    /**
     * ???????????????????????????????????????
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        log("onRequestPermissionsResult ")

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                var allGranted = true

                grantResults.forEach {
                    if (it != PackageManager.PERMISSION_GRANTED) {
                        allGranted = false
                    }
                }

                if (allGranted) {  //?????????????????????
                    log("onRequestPermissionsResult ?????????????????????")
                } else {
                    log("????????????????????????,??????????????????????????????????????????????????????setting ")

                    // ????????????????????????,?????????????????????????????????????????????
                    PermissionUtils.showPermissionSettingDialog(this)
                }
            }
        }
    }


    /**
     * ??????????????????????????????????????????????????????
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PERMISSION_SETTING_CODE -> { //??????????????????????????????????????????????????????????????????
                log("???????????????????????????????????????????????????")
                PermissionUtils.checkPermission(this, permissionsList, Runnable {
                    val intent = Intent(this, CameraActivity2::class.java)
                    startActivity(intent)
                })
            }

        }
    }
}
