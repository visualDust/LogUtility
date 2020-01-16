package com.visualdust.logUtility

import java.awt.Color

class AttributedHtmlStr {
    var originalStr: String
    private var processedStr: String = ""
    private var processed = false
    private var bgColor: Color? = null
    private var fgColor: Color? = null

    constructor(string: String) {
        originalStr = string
    }

    constructor(obj: Any) {
        originalStr = obj.toString()
    }

    fun applyFG(color: BuiltInColors): AttributedHtmlStr {
        fgColor = BIColorMap.getValue(color);processed = false
        return this
    }

    fun applyFG(color: Color): AttributedHtmlStr {
        fgColor = color;processed = false
        return this
    }

    fun applyBG(color: Color): AttributedHtmlStr {
        bgColor = color;processed = false
        return this
    }

    fun applyBG(color: BuiltInColors): AttributedHtmlStr {
        bgColor = BIColorMap.getValue(color);processed = false
        return this
    }

    private fun process() {
        processedStr = prefix_prefix;
        if (bgColor != null)
            processedStr += "background-color:rgb(${bgColor!!.red},${bgColor!!.green},${bgColor!!.blue});"
        if (fgColor != null)
            processedStr += "color:rgb(${fgColor!!.red},${fgColor!!.green},${fgColor!!.blue});"
        processedStr += prefix_postfix +
                originalStr +
                postfix
        processed = true
    }

    override fun toString(): String {
        if (!processed) process()
        return processedStr
    }

    companion object {
        private val fontFamily =
            "font-family:'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif"

        private val prefix_prefix = "<a style=\"${fontFamily};"
        private val prefix_postfix = "\">"
        private val postfix = "</a>"

        enum class BuiltInColors { White, Black, Red, Yellow, Blue, Green, Orange, Gray }

        private val BIColorMap = mapOf<BuiltInColors, Color>(
            BuiltInColors.Black to Color.black,
            BuiltInColors.White to Color.white,
            BuiltInColors.Red to Color.red,
            BuiltInColors.Blue to Color.blue,
            BuiltInColors.Yellow to Color.yellow,
            BuiltInColors.Green to Color.green,
            BuiltInColors.Orange to Color.orange,
            BuiltInColors.Gray to Color.gray
        )
    }

//    class ColorOnWeb : Color {
//        constructor(r: Int, g: Int, b: Int) : super(r, g, b)
//        constructor(color: Color) : super(color.rgb)
//        /**
//         * Returns the HEX value representing the colour in the default sRGB ColorModel.
//         *
//         * @return the HEX value of the colour in the default sRGB ColorModel
//         */
//        val webHex: String
//            get() = toHex(red, green, blue)
//
//        override fun toString(): String = webHex
//
//        companion object {
//            /**
//             * Returns a web browser-friendly HEX value representing the colour in the default sRGB
//             * ColorModel.
//             *
//             * @param r red
//             * @param g green
//             * @param b blue
//             * @return a browser-friendly HEX value
//             */
//            fun toHex(r: Int, g: Int, b: Int): String {
//                return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b)
//            }
//
//            private fun toBrowserHexValue(number: Int): String {
//                val builder = StringBuilder(Integer.toHexString(number and 0xff))
//                while (builder.length < 2) {
//                    builder.append("0")
//                }
//                return builder.toString().toUpperCase()
//            }
//        }
//    }
}