import processing.core.PApplet

class Main : PApplet() {

    override fun settings() {
        super.settings()
        size(600, 1024)
    }

    override fun setup() {
        super.setup()
        background(255)
        drawHistgram("sample1.png", 0f, 0f, 256f, 256f)
        drawHistgram("sample2.png", 0f, 256f, 265f, 256f)
        drawHistgram("sample3.png", 0f, 512f, 256f, 256f)
        drawHistgram("ma.png", 0f, 768f, 256f, 256f)
        save("export.png")
        exit()
    }

    fun getImageBrightness(fileName: String): Array<Int> {
        val pImage = loadImage(fileName).apply { loadPixels() }
        val brightness: Array<Int> = Array(256, { 0 })

        pImage.pixels.forEach { color ->
            brightness[brightness(color).toInt()]++
        }

        return brightness
    }

    fun drawHistgram(fileName: String, x: Float, y: Float, width: Float, height: Float) {
        val margin = 30f
        val top = y + margin
        val bottom = y + height - margin
        val right = x + width - margin
        val left = x + margin
        val line_height = bottom - top
        val line_width = right - left

        line(left, top, left, bottom)

        val brightness = getImageBrightness(fileName)
        val min = brightness.min()?.toFloat()!!
        val max = brightness.max()?.toFloat()!!

        brightness
                .map { it.toFloat() }
                .map { map(it, min, max, 0f, line_height) }
                .forEachIndexed { index, fl ->
                    line(left + index, bottom, left + index, bottom - fl)
                }

        fill(0)
        textSize(16f)
        text("0", left - 10f, bottom + 16f)
        text(max.toInt(), left - 10f, top)
        text(255, right + 36f, bottom + 16f)

        drawImage(fileName, left + width + 50f, top, line_width, line_height)
    }

    fun drawImage(fileName: String, x: Float, y: Float, width: Float, height: Float) {
        image(loadImage(fileName), x, y, width, height)
    }
}

fun main(args: Array<String>) {
    PApplet.main("Main")
}
