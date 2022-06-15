package fool

class Round(val deck: MutableList<Card>, val players: List<Player>) {



    fun printRound(){
        println("\n*  *  *  THIS ROUND  *  *  * \n")
        println("\n*  *  *  TURNUP: ${deck.last()}  *  *  * \n")
        println("\nYOUR CURRENT HAND:")
        players.first().showHand()
        println()
    }

}