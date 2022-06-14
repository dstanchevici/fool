package fool

class Game(val deck: MutableList<Card>, val players: List<Player>) {
    fun dealCards() {
        for (player in players){
            while (player.hand.size < 6){
                if (deck.isNotEmpty()){
                    player.hand.add(deck.first())
                    deck.removeFirst()
                } else {
                    println("The deck is empty.")
                }
            }
        }
    }
    fun getTrumps(): Card {
        val trumpCard = deck.first()
        deck.removeFirst()
        deck.add(trumpCard)
        return trumpCard
    }
    fun printYourHandAndTrump(){
        val trumpCard = getTrumps()
        repeat(10) {print("*   ")}
        println("\nThe trumps are the ${trumpCard.suit.uppercase()}, and your current hand includes")
        players.first().showHand()
        repeat(10) {print("*   ")}
        println()
    }

    fun playRound(){}
}