
// https://discuss.kotlinlang.org/t/multi-dimensonal-arrays-are-a-pain-point-in-kotlin/561

class BitopiaryGrid(val width: Int, val height: Int){
    private val boolToBinary = { b: Boolean -> if (b) "1" else "0" }

    private val grid = Array(height, {BooleanArray(width)})

    private fun heightFilter(readHead: ReadHead, caret: Caret) = { index: Int, _: BooleanArray ->  index in caret.y until caret.y + readHead.height}
    private fun widthFilter(readHead: ReadHead, caret: Caret) = { index: Int, _: Boolean ->  index in caret.x until caret.x + readHead.width}

    fun getInt(readHead: ReadHead, caret: Caret): Int {
        val filterHeight = heightFilter(readHead, caret)
        val filterWidth = widthFilter(readHead, caret)

        val bitList = grid.filterIndexed(filterHeight).flatMap { array -> array.filterIndexed(filterWidth).toList()}
        return transformToInt(bitList)
    }

    fun getUnsignedInt(readHead: ReadHead, caret: Caret): Int {
        val filterHeight = heightFilter(readHead, caret)
        val filterWidth = widthFilter(readHead, caret)

        val bitList = grid.filterIndexed(filterHeight).flatMap { array -> array.filterIndexed(filterWidth).toList()}
        return Integer.parseInt(bitList.joinToString("", transform = boolToBinary), 2)
    }

    fun getChar(readHead: ReadHead, caret: Caret) = getUnsignedInt(readHead, caret).toChar()

    private fun transformToInt(bits: Iterable<Boolean>): Int {

        return if (bits.first()) { // Handle two's complement, kind of annoying to have to do it oneself
            val inverted = bits.map { b -> !b }.toMutableList()
            for ( i in inverted.size - 1 downTo 0) {
                inverted[i] = !inverted[i]
                if (inverted[i]) break
            }
            Integer.parseInt("-" + inverted.joinToString("", transform = boolToBinary), 2)
        } else {
            Integer.parseInt(bits.joinToString("", transform = boolToBinary), 2)
        }
    }

    fun setInt(readHead: ReadHead, caret: Caret, value: Int) {
        val leastSignificantFirst = Integer.toBinaryString(value).reversed().padEnd(readHead.size, '0')
        if(leastSignificantFirst.equals("10000000")){
            var bla = true
        }
        var bitIndex = 0
        for (y in caret.y + readHead.height-1 downTo caret.y){
            for (x in caret.x + readHead.width-1 downTo caret.x){
                grid[y][x] = leastSignificantFirst[bitIndex] == '1'
                bitIndex++
                if(bitIndex >= leastSignificantFirst.length){
                    return
                }
            }
        }
    }

    fun debugPrint(width: Int = 20, height: Int = 5){
        for (y in 0..height){
            for (x in 0..width){
                print(if (grid[y][x]) "1 " else "_ " )
            }
            println()
        }
        println(".")
    }

}