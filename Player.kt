package fool

class Player (val name: String, val hand: MutableList<Card> = mutableListOf<Card>()){

    fun showHand(){
        hand.forEach { println("[${it.rank} of ${it.suit}]") }
    }

    fun countCards(hand: MutableList<Card>) = hand.size
}