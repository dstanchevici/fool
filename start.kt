package fool
// Added to git
fun main(){
    val deck = getDeck()
    val yourHand = mutableListOf<Card>()
    val opponentsHand = mutableListOf<Card>()
    dealCards(deck, yourHand, opponentsHand)

    println(deck.forEach { println(it) })
    println("--------")
    println(yourHand.forEach { println(it) })
    println("--------")
    println(opponentsHand.forEach { println(it) })




}