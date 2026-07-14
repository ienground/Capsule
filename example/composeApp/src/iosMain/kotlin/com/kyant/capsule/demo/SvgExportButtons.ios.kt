@file:OptIn(kotlinx.cinterop.BetaInteropApi::class, kotlinx.cinterop.ExperimentalForeignApi::class)

package com.kyant.capsule.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.uikit.LocalUIViewController
import com.kyant.capsule.ContinuousCapsule
import com.kyant.capsule.path.PathSegments
import com.kyant.capsule.path.toSvg
import platform.UIKit.UIActivityViewController
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Foundation.writeToURL
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL

@Composable
actual fun SvgExportButtons(
    modifier: Modifier,
    color: Color,
    contentColor: () -> Color,
    currentPathSegments: PathSegments
) {
    val uiViewController = LocalUIViewController.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .clip(ContinuousCapsule)
                .background(color)
                .clickable {
                    val svg = currentPathSegments.toSvg(asDocument = true)
                    val nsString = NSString.create(string = svg)

                    // Create a temporary file to share
                    val tempDirectory = NSTemporaryDirectory()
                    val fileURL = NSURL.fileURLWithPath(tempDirectory + "continuous_rounded_rect.svg")
                    val nsData = nsString.dataUsingEncoding(NSUTF8StringEncoding)
                    if (nsData != null) {
                        nsData.writeToURL(fileURL, true)
                        val activityItems = listOf(fileURL)
                        val activityViewController = UIActivityViewController(activityItems, null)
                        uiViewController.presentViewController(activityViewController, true, null)
                    }
                }
                .height(48.dp)
                .weight(1f)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                "Share",
                color = contentColor
            )
        }
    }
}
