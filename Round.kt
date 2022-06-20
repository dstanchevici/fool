package fool

class Round(private val players: List<Player>, private val turnUp: Card) {
    fun printRound(){
        println("\n*  *  *  THIS ROUND  *  *  * \n")
        println("\n*  *  *  TURNUP:   *  *  * \n")
        println("\nYOUR CURRENT HAND:")
        players.first().showHand()
        println()
    }

}