/** You can edit, run, and share this code. play.kotlinlang.org */
fun main() {
    val array = arrayOf(1, 3, 5, 7, 9)

    findMiddleIndex(array)
}

fun findMiddleIndex(array: Array<Int>) {
    if (array.size >= 3) {
        for (i in 1 until array.size) {
            var sumLeft = 0
            var sumRight = 0
            for (j in 0 until i) {
                sumLeft += array[j]
            }
            for (k in i + 1 until array.size) {
                sumRight += array[k]
            }
            if (sumLeft == sumRight) {
                print("middle index is $i")
                return
            } else {
                if (i == array.size - 1) {
                    print("index not found")
                    return
                }
            }
        }
    } else {
        print("index not found")
    }
}
