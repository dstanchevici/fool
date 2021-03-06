package fool


class Player (val name: String, val hand: MutableList<Card> = mutableListOf()){
    fun showHand(){
        for ((index, value) in hand.withIndex()){
            print("${index + 1}. $value; ")
        }

        println()
    }
}