package com.github.rodionk77.cinemajournalkmp

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlinx.cinterop.UIntVar
import platform.SystemConfiguration.SCNetworkReachabilityCreateWithName
import platform.SystemConfiguration.SCNetworkReachabilityGetFlags
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsReachable
import platform.UIKit.UIDevice
import platform.UIKit.UIMarkupTextPrintFormatter
import platform.UIKit.UIPrintInfo
import platform.UIKit.UIPrintInfoOutputType
import platform.UIKit.UIPrintInteractionController

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@Composable
actual fun rememberPrintAction(html: String, title: String): () -> Unit {
    return {
        val printInfo = UIPrintInfo.printInfo()!!
        printInfo.jobName = title
        printInfo.outputType = UIPrintInfoOutputType.UIPrintInfoOutputGeneral
        val formatter = UIMarkupTextPrintFormatter(markupText = html)
        val controller = UIPrintInteractionController.sharedPrintController()
        controller.printInfo = printInfo
        controller.printFormatter = formatter
        controller.presentAnimated(true) { _, _, _ -> }
    }
}

// iOS simulator connects to host machine via localhost
// For real device: change to your Mac's local IP (e.g. "192.168.1.100")
actual val backendHost: String = "192.168.1.152"

@OptIn(ExperimentalForeignApi::class)
actual fun isOnline(): Boolean {
    val reachability = SCNetworkReachabilityCreateWithName(null, "apple.com") ?: return false
    memScoped {
        val flags = alloc<UIntVar>()
        if (SCNetworkReachabilityGetFlags(reachability, flags.ptr)) {
            return flags.value and kSCNetworkReachabilityFlagsReachable != 0u
        }
    }
    return false
}