package com.payment.aeps.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import org.w3c.dom.Element
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory

fun View.manageAepsClick(isClick: Boolean){
    if (isClick){
        isEnabled = false
        alpha = 0.5f
    }else{
        isEnabled = true
        alpha = 1f
    }
}


fun View.captureAndShareAepsScreenshot() {
    val screenshot = captureAepsScreenshot(this)
    shareAepsScreenshot(this.context, screenshot)
}

private fun captureAepsScreenshot(view: View): Bitmap {
    view.isDrawingCacheEnabled = true
    val screenshot = Bitmap.createBitmap(view.drawingCache)
    view.isDrawingCacheEnabled = false
    return screenshot
}
private fun shareAepsScreenshot(context: Context, screenshot: Bitmap) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "image/jpeg"
    val imagePath = MediaStore.Images.Media.insertImage(
        context.contentResolver, screenshot, "screenshot", null
    )
    val imageUri = Uri.parse(imagePath)

    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    context.startActivity(Intent.createChooser(shareIntent, "Share Screenshot"))
}

class utils {

    fun morphoDeviceData(context: Context?, mainData: String?): DeviceDataModel? {
        val dataModel = DeviceDataModel()
        dataModel.errMsg = "Please connect your Morpho device!"
        dataModel.errCode = "111"
        try {
            if (mainData != null) {
                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc =
                    dBuilder.parse(ByteArrayInputStream(mainData.toByteArray(charset("UTF-8"))))
                val nodeList = doc.getElementsByTagName("DeviceInfo")
                val n2 = (nodeList.item(0) as Element).getElementsByTagName("additional_info")
                if (n2.item(0) == null) {
                    Toast.makeText(
                        context,
                        "Please connect your Morpho Device!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val serialNo = n2.item(0).childNodes.item(1) as Element
                    dataModel.srno
                    dataModel.srno = "" + serialNo.getAttribute("value")
                    dataModel.sysid = "" + serialNo.getAttribute("value")
                    dataModel.ts = "" + System.currentTimeMillis()
                    if (!dataModel.srno.equals("",true) || !dataModel.sysid.equals("",true)
                    ) {
                        dataModel.errCode = "919"
                    }
                }
            }
        } catch (var10: Exception) {
            Toast.makeText(
                context,
                "Please check your Morpho device or contact customer support!",
                Toast.LENGTH_SHORT
            ).show()
            var10.printStackTrace()
        }
        return dataModel
    }

    fun morphoFingerData(
        ParentLayout: Context?,
        mainData: String?,
        morphoDeviceData: DeviceDataModel
    ): DeviceDataModel? {
        val dataModel = DeviceDataModel()
        dataModel.errMsg = "Please connect your Morpho device!"
        dataModel.errCode = "111"
        try {
            if (mainData != null) {
                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc =
                    dBuilder.parse(ByteArrayInputStream(mainData.toByteArray(charset("UTF-8"))))
                val node = doc.getElementsByTagName("PidData").item(0)
                val element = node as Element
                val nodeList1 = doc.getElementsByTagName("Resp")
                val element1 = nodeList1.item(0) as Element
                dataModel.errCode= element1.getAttribute("errCode")
                dataModel.errMsg = element1.getAttribute("errInfo")
                dataModel.nmPoints = element1.getAttribute("nmPoints")
                if (element1.getAttribute("errCode").equals("0", ignoreCase = true)) {
                    val nodeList2 = doc.getElementsByTagName("Skey")
                    val element2 = nodeList2.item(0) as Element
                    val nodeList3 = doc.getElementsByTagName("DeviceInfo")
                    val element3 = nodeList3.item(0) as Element
                    dataModel.fingerData = "" + getValue("Data", element)
                    dataModel.hmac = "" + getValue("Hmac", element)
                    dataModel.skey = "" + getValue("Skey", element)
                    dataModel.ci = "" + element2.getAttribute("ci")
                    dataModel.dc = "" + element3.getAttribute("dc")
                    dataModel.dpId = "" + element3.getAttribute("dpId")
                    dataModel.mc = "" + element3.getAttribute("mc")
                    dataModel.mi = "" + element3.getAttribute("mi")
                    dataModel.rdsId = "" + element3.getAttribute("rdsId")
                    dataModel.rdsVer = "" + element3.getAttribute("rdsVer")
                    dataModel.srno = "" + morphoDeviceData.srno
                    dataModel.sysid = "" + morphoDeviceData.sysid
                    dataModel.ts = "" + morphoDeviceData.ts
                    Toast.makeText(ParentLayout, "Finger Captured Successfully!", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (var16: Exception) {
            dataModel.errMsg = "Please check your Morpho device or contact customer support!"
            var16.printStackTrace()
        }
        return dataModel
    }

    fun mantraData(mainData: String?, context: Context?): DeviceDataModel {
        val dataModel = DeviceDataModel()
        dataModel.errMsg ="Please connect your Mantra device!"
        dataModel.errCode = "111"
        try {
            if (mainData != null) {
                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc =
                    dBuilder.parse(ByteArrayInputStream(mainData.toByteArray(charset("UTF-8"))))
                val node = doc.getElementsByTagName("PidData").item(0)
                val element = node as Element
                val nodeList1 = doc.getElementsByTagName("Resp")
                val element1 = nodeList1.item(0) as Element
                dataModel.errCode =element1.getAttribute("errCode")
                dataModel.errMsg=element1.getAttribute("errInfo")
                dataModel.nmPoints=element1.getAttribute("nmPoints")
                if (element1.getAttribute("errCode").equals("0", ignoreCase = true)) {
                    val nodeList2 = doc.getElementsByTagName("Skey")
                    val element2 = nodeList2.item(0) as Element
                    val nodeList3 = doc.getElementsByTagName("DeviceInfo")
                    val element3 = nodeList3.item(0) as Element
                    val nodeList4 =
                        (nodeList3.item(0) as Element).getElementsByTagName("additional_info")
                    var element4 = nodeList4.item(0).childNodes.item(1) as Element
                    val srno = element4.getAttribute("value")
                    element4 = nodeList4.item(0).childNodes.item(3) as Element
                    val sysid = element4.getAttribute("value")
                    element4 = nodeList4.item(0).childNodes.item(5) as Element
                    val ts = element4.getAttribute("value")
                    dataModel.fingerData="" + getValue("Data", element)
                    dataModel.hmac= "" + getValue("Hmac", element)
                    dataModel.skey= "" + getValue("Skey", element)
                    dataModel.ci = "" + element2.getAttribute("ci")
                    dataModel.dc = "" + element3.getAttribute("dc")
                    dataModel.dpId = "" + element3.getAttribute("dpId")
                    dataModel.mc = "" + element3.getAttribute("mc")
                    dataModel.mi = "" + element3.getAttribute("mi")
                    dataModel.rdsId = "" + element3.getAttribute("rdsId")
                    dataModel.rdsVer = "" + element3.getAttribute("rdsVer")
                    dataModel.srno = "" + srno
                    dataModel.sysid = "" + sysid
                    dataModel.ts = "" + ts
                    Toast.makeText(context, "Finger Captured Successfully!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (var20: Exception) {
            dataModel.errMsg = "Please check your Mantra device or contact customer support!"
            var20.printStackTrace()
        }
        return dataModel
    }

    private fun getValue(mainStr: String, element: Element): String {
        val nodeList = element.getElementsByTagName(mainStr).item(0).childNodes
        val node = nodeList.item(0)
        return node.nodeValue
    }

    fun secugenData(ParentLayout: Context?, mainData: String?): DeviceDataModel {
        val dataModel = DeviceDataModel()
        dataModel.errMsg = "Please connect your Secugen device!"
        dataModel.errMsg = "111"
        try {
            if (mainData != null) {
                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc =
                    dBuilder.parse(ByteArrayInputStream(mainData.toByteArray(charset("UTF-8"))))
                val node = doc.getElementsByTagName("PidData").item(0)
                val element = node as Element
                val nodeList1 = doc.getElementsByTagName("Resp")
                val element1 = nodeList1.item(0) as Element
                dataModel.errCode = element1.getAttribute("errCode")
                dataModel.errMsg = element1.getAttribute("errInfo")
                dataModel.nmPoints = element1.getAttribute("nmPoints")
                if (element1.getAttribute("errCode").equals("0", ignoreCase = true)) {
                    val nodeList2 = doc.getElementsByTagName("Skey")
                    val element2 = nodeList2.item(0) as Element
                    val nodeList3 = doc.getElementsByTagName("DeviceInfo")
                    val element3 = nodeList3.item(0) as Element
                    val nodeList4 =
                        (nodeList3.item(0) as Element).getElementsByTagName("additional_info")
                    val element4 = nodeList4.item(0).childNodes.item(1) as Element
                    dataModel.fingerData = "" + getValue("Data", element)
                    dataModel.hmac = "" + getValue("Hmac", element)
                    dataModel.skey = "" + getValue("Skey", element)
                    dataModel.ci = "" + element2.getAttribute("ci")
                    dataModel.dc = "" + element3.getAttribute("dc")
                    dataModel.dpId = "" + element3.getAttribute("dpId")
                    dataModel.mc = "" + element3.getAttribute("mc")
                    dataModel.mi = "" + element3.getAttribute("mi")
                    dataModel.rdsId = "" + element3.getAttribute("rdsId")
                    dataModel.rdsVer = "" + element3.getAttribute("rdsVer")
                    dataModel.srno = "" + element4.getAttribute("value")
                    dataModel.sysid = "" + element4.getAttribute("value")
                    dataModel.ts = "" + System.currentTimeMillis()
                    Toast.makeText(
                        ParentLayout,
                        "Finger Captured Successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (var17: Exception) {
            dataModel.errMsg = "Please check your Secugen device or contact customer support!"
            var17.printStackTrace()
        }
        return dataModel
    }

    fun tatvikData(ParentLayout: Context?, mainData: String?): DeviceDataModel {
        val dataModel = DeviceDataModel()
        dataModel.errMsg = "Please connect your Tatvik device!"
        dataModel.errCode = "111"
        try {
            if (mainData != null) {
                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc =
                    dBuilder.parse(ByteArrayInputStream(mainData.toByteArray(charset("UTF-8"))))
                val node = doc.getElementsByTagName("PidData").item(0)
                val element = node as Element
                val nodeList1 = doc.getElementsByTagName("Resp")
                val element1 = nodeList1.item(0) as Element
                dataModel.errCode = element1.getAttribute("errCode")
                dataModel.errMsg = element1.getAttribute("errInfo")
                dataModel.nmPoints = element1.getAttribute("nmPoints")
                if (element1.getAttribute("errCode").equals("0", ignoreCase = true)) {
                    val nodeList2 = doc.getElementsByTagName("Skey")
                    val element2 = nodeList2.item(0) as Element
                    val nodeList3 = doc.getElementsByTagName("DeviceInfo")
                    val element3 = nodeList3.item(0) as Element
                    val nodeList4 =
                        (nodeList3.item(0) as Element).getElementsByTagName("additional_info")
                    val element4 = nodeList4.item(0).childNodes.item(0) as Element
                    dataModel.fingerData = "" + getValue("Data", element)
                    dataModel.hmac = "" + getValue("Hmac", element)
                    dataModel.skey = "" + getValue("Skey", element)
                    dataModel.ci = "" + element2.getAttribute("ci")
                    dataModel.dc = "" + element3.getAttribute("dc")
                    dataModel.dpId = "" + element3.getAttribute("dpId")
                    dataModel.mc = "" + element3.getAttribute("mc")
                    dataModel.mi = "" + element3.getAttribute("mi")
                    dataModel.rdsId = "" + element3.getAttribute("rdsId")
                    dataModel.rdsVer = "" + element3.getAttribute("rdsVer")
                    dataModel.srno = "" + element4.getAttribute("value")
                    dataModel.sysid = "" + element4.getAttribute("value")
                    dataModel.ts = "" + System.currentTimeMillis()
                    Toast.makeText(
                        ParentLayout,
                        "Finger Captured Successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (var17: Exception) {
            dataModel.errMsg = "Please check your Tatvik device or contact customer support!"
            var17.printStackTrace()
        }
        return dataModel
    }

    fun starTekData(ParentLayout: Context?, mainData: String?): DeviceDataModel {
        val dataModel = DeviceDataModel()
        dataModel.errMsg = "Please connect your Startek device!"
        dataModel.errCode = "111"
        try {
            if (mainData != null) {
                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc =
                    dBuilder.parse(ByteArrayInputStream(mainData.toByteArray(charset("UTF-8"))))
                val node = doc.getElementsByTagName("PidData").item(0)
                val element = node as Element
                val nodeList1 = doc.getElementsByTagName("Resp")
                val element1 = nodeList1.item(0) as Element
                dataModel.errCode = element1.getAttribute("errCode")
                dataModel.errMsg = element1.getAttribute("errInfo")
                dataModel.nmPoints = element1.getAttribute("nmPoints")
                if (element1.getAttribute("errCode").equals("0", ignoreCase = true)) {
                    val nodeList2 = doc.getElementsByTagName("Skey")
                    val element2 = nodeList2.item(0) as Element
                    val nodeList3 = doc.getElementsByTagName("DeviceInfo")
                    val element3 = nodeList3.item(0) as Element
                    val nodeList4 =
                        (nodeList3.item(0) as Element).getElementsByTagName("additional_info")
                    val element4 = nodeList4.item(0).childNodes.item(0) as Element
                    dataModel.fingerData = "" + getValue("Data", element)
                    dataModel.hmac = "" + getValue("Hmac", element)
                    dataModel.skey = "" + getValue("Skey", element)
                    dataModel.ci = "" + element2.getAttribute("ci")
                    dataModel.dc = "" + element3.getAttribute("dc")
                    dataModel.dpId = "" + element3.getAttribute("dpId")
                    dataModel.mc = "" + element3.getAttribute("mc")
                    dataModel.mi = "" + element3.getAttribute("mi")
                    dataModel.rdsId = "" + element3.getAttribute("rdsId")
                    dataModel.rdsVer = "" + element3.getAttribute("rdsVer")
                    dataModel.srno = "" + element4.getAttribute("value")
                    dataModel.sysid = "" + element4.getAttribute("value")
                    dataModel.ts = "" + System.currentTimeMillis()
                    Toast.makeText(
                        ParentLayout,
                        "Finger Captured Successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (var17: Exception) {
            dataModel.errMsg = "Please check your Startek device or contact customer support!"
            var17.printStackTrace()
        }
        return dataModel
    }

    fun EvoluteData(ParentLayout: Context?, mainData: String?): DeviceDataModel {
        val dataModel = DeviceDataModel()
        dataModel.errMsg = "Please connect your Evolute device!"
        dataModel.errCode = "111"
        try {
            if (mainData != null) {
                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc =
                    dBuilder.parse(ByteArrayInputStream(mainData.toByteArray(charset("UTF-8"))))
                val node = doc.getElementsByTagName("PidData").item(0)
                val element = node as Element
                val nodeList1 = doc.getElementsByTagName("Resp")
                val element1 = nodeList1.item(0) as Element
                dataModel.errCode = element1.getAttribute("errCode")
                dataModel.errMsg = element1.getAttribute("errInfo")
                dataModel.nmPoints = element1.getAttribute("nmPoints")
                if (element1.getAttribute("errCode").equals("0", ignoreCase = true)) {
                    val nodeList2 = doc.getElementsByTagName("Skey")
                    val element2 = nodeList2.item(0) as Element
                    val nodeList3 = doc.getElementsByTagName("DeviceInfo")
                    val element3 = nodeList3.item(0) as Element
                    val nodeList4 =
                        (nodeList3.item(0) as Element).getElementsByTagName("additional_info")
                    val element4 = nodeList4.item(0).childNodes.item(0) as Element
                    dataModel.fingerData = "" + getValue("Data", element)
                    dataModel.hmac = "" + getValue("Hmac", element)
                    dataModel.skey = "" + getValue("Skey", element)
                    dataModel.ci = "" + element2.getAttribute("ci")
                    dataModel.dc = "" + element3.getAttribute("dc")
                    dataModel.dpId = "" + element3.getAttribute("dpId")
                    dataModel.mc = "" + element3.getAttribute("mc")
                    dataModel.mi = "" + element3.getAttribute("mi")
                    dataModel.rdsId = "" + element3.getAttribute("rdsId")
                    dataModel.rdsVer = "" + element3.getAttribute("rdsVer")
                    dataModel.srno = "" + element4.getAttribute("value")
                    dataModel.sysid = "" + element4.getAttribute("value")
                    dataModel.ts = "" + System.currentTimeMillis()
                    Toast.makeText(
                        ParentLayout,
                        "Finger Captured Successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (var17: Exception) {
            dataModel.errMsg = "Please check your Startek device or contact customer support!"
            var17.printStackTrace()
        }
        return dataModel
    }

    @SuppressLint("HardwareIds")
    fun getImei(context: Context): String? {
        var deviceId: String? = null
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return ""
        }
        deviceId = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < 29) {
                telephonyManager.getImei(0)
            } else if (Build.VERSION.SDK_INT >= 29) {
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            } else {
                telephonyManager.deviceId
            }
        } catch (e: Exception) {
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }
        return deviceId
    }


    fun truncateToTwoDecimalPlacesAeps(input: String): String {
        val decimalIndex = input.indexOf('.')
        if (decimalIndex != -1 && input.length > decimalIndex + 3) {
            return input.substring(0, decimalIndex + 3)
        }
        return input
    }
}