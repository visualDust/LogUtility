package com.visualdust.logUtility

class AttributedShellStr {
    var originalStr = ""
    private var processedStr = ""
    private var processed = false
    private var bg = BGs.None
    private var fg = FGs.None
    //    var styles = mutableListOf<Styles>(Styles.None)
    var style = Styles.None

    constructor(string: String) {
        this.originalStr = string
    }

    constructor(obj: Any) {
        this.originalStr = obj.toString()
    }

    fun applyFG(fg: FGs): AttributedShellStr {
        processed = false;this.fg = fg
        return this
    }

    fun applyBG(bg: BGs): AttributedShellStr {
        processed = true;this.bg = bg
        return this
    }

    fun applyStyle(style: Styles): AttributedShellStr {
        processed = false;this.style = style
        return this
    }

    private fun process() {
        processedStr =
            "\u001b[${STYLE_MAP.getValue(style)};${FG_MAP.getValue(fg)};${BG_MAP.getValue(bg)}m${originalStr}\u001B[0m"
        processed = true
    }

    public override fun toString(): String {
        if (!processed) process()
        return processedStr
    }

    companion object {
        enum class Styles { None, Highlight, Underline, Flicker, Inverse, Invisible }

        private val STYLE_MAP = mapOf<Styles, String>(
            Styles.None to "0",
            Styles.Highlight to "1",
            Styles.Underline to "4",
            Styles.Flicker to "5",
            Styles.Inverse to "7",
            Styles.Invisible to "8"
        )

        enum class BGs { None, Black, Red, Green, Yellow, Blue, Purple, LiteGreen, White }

        private val BG_MAP = mapOf<BGs, String>(
            BGs.None to "1",
            BGs.Black to "40",
            BGs.Red to "41",
            BGs.Green to "42",
            BGs.Yellow to "43",
            BGs.Blue to "44",
            BGs.Purple to "45",
            BGs.LiteGreen to "46",
            BGs.White to "47"
        )

        enum class FGs { None, Black, Red, Green, Yellow, Blue, Purple, LiteGreen, White }

        private val FG_MAP = mapOf<FGs, String>(
            FGs.None to "37",
            FGs.Black to "30",
            FGs.Red to "31",
            FGs.Green to "32",
            FGs.Yellow to "33",
            FGs.Blue to "34",
            FGs.Purple to "35",
            FGs.LiteGreen to "36",
            FGs.White to "37"
        )
    }
}