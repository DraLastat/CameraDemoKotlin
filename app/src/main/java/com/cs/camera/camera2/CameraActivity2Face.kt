package com.cs.camera.camera2

import android.graphics.RectF
import android.hardware.camera2.params.Face
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import com.cs.camera.R
import com.cs.camera.log
import kotlinx.android.synthetic.main.activity_camera2_face.*
import com.cs.camera.util.ViewClickManager


/**
 * author :
 * data  :
 * desc :
 */
class CameraActivity2Face : AppCompatActivity(), Camera2HelperFace.FaceDetectListener {
    private lateinit var camera2HelperFace: Camera2HelperFace
    private val viewClickManager = ViewClickManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera2_face)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        camera2HelperFace = Camera2HelperFace(this, textureView)
        camera2HelperFace.setFaceDetectListener(this)

        btnTakePic.setOnClickListener { camera2HelperFace.takePic() }
        ivExchange.setOnClickListener { camera2HelperFace.exchangeCamera() }

        viewClickManager.setClickLister(faceView, object : ViewClickManager.OnClickListener {
            override fun onSingleClick() {
                log("single click")
                //value = "btn1 single click"
                //Utils.toast(this@MainActivity, value)
            }

            override fun onDoubleClick() {
                //Utils.toast(this@MainActivity, "btn1 double click:" + value)
                log("double click")
            }
        })
    }

    override fun onFaceDetect(faces: Array<Face>, facesRect: ArrayList<RectF>) {
        faceView.setFaces(facesRect)
    }



    override fun onDestroy() {
        super.onDestroy()
        camera2HelperFace.releaseCamera()
        camera2HelperFace.releaseThread()
    }

}
