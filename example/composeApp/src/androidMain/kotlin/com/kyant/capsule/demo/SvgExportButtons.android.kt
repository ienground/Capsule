package com.kyant.capsule.demo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.kyant.capsule.ContinuousCapsule
import com.kyant.capsule.path.PathSegments
import com.kyant.capsule.path.toSvg
import java.io.File

@Composable
actual fun SvgExportButtons(
    modifier: Modifier,
    color: Color,
    contentColor: () -> Color,
    currentPathSegments: PathSegments
) {
    val context = LocalContext.current
    val createFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    val svg = currentPathSegments.toSvg(asDocument = true)
                    outputStream.write(svg.toByteArray())
                }
            }
        }
    }

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
                    val tempFile = File(context.cacheDir, "continuous_rounded_rect.svg").apply {
                        writeBytes(svg.toByteArray())
                    }
                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        tempFile
                    )
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "image/svg+xml"
                        putExtra(Intent.EXTRA_STREAM, uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share SVG"))
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

        Box(
            Modifier
                .clip(ContinuousCapsule)
                .background(color)
                .clickable {
                    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/svg+xml"
                        putExtra(Intent.EXTRA_TITLE, "continuous_rounded_rect.svg")
                    }
                    createFileLauncher.launch(intent)
                }
                .height(48.dp)
                .weight(1f)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                "Save",
                color = contentColor
            )
        }
    }
}
