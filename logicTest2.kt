/** You can edit, run, and share this code. play.kotlinlang.org */
fun main() {
    val string = "aka"
    
    findIsPalindome(string)
}

fun findIsPalindome(string :String){
    val originalString = string
    var reversedString = ""
    var lastIndex = string.length - 1
    
    while(lastIndex >= 0){
        reversedString += originalString[lastIndex]
        lastIndex--
    }
    
    if(originalString == reversedString) {
        println("$reversedString is palindome")
    } else {
        println("$reversedString isn't palindome")
    }
}