package com.saidatmaca.currencyapp.presentation.ui.theme

import androidx.compose.ui.tooling.preview.Preview



const val ldpi = "spec:width=240dp,height=320dp"
const val mdpi = "spec:width=320dp,height=480dp"
const val hdpi = "spec:width=480dp,height=800dp"
const val xhdpi = "spec:width=720dp,height=1280dp"
const val xxhdpi = "spec:width=1080dp,height=1920dp"
const val xxxhdpi = "spec:width=1440dp,height=2560dp"


@Preview(showSystemUi = true, device = "spec:width=411dp,height=891dp", name = "Phone")
//@Preview(showSystemUi = true, device = ldpi, name = "ldpi")
@Preview(showSystemUi = true, device = mdpi, name = "mdpi")
@Preview(showSystemUi = true, device = hdpi, name = "hdpi")
@Preview(showSystemUi = true, device = xhdpi, name = "xhdpi")
@Preview(showSystemUi = true, device = xxhdpi, name = "xxhdpi")
@Preview(showSystemUi = true, device = xxxhdpi, name = "xxxhdpi")

/*@Preview(showSystemUi = true, device = "spec:width=673.5dp,height=841dp,dpi=480", name = "Foldable")
@Preview(showSystemUi = true, device = "spec:width=1280dp,height=800dp,dpi=480", name = "Tablet")
@Preview(showSystemUi = true, device = "spec:width=1920dp,height=1080dp,dpi=480", name = "Desktop")*/
annotation class DeviceSizePreviews