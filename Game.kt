package fool

import kotlin.random.Random

fun getDeck(): MutableList<Card>{
    val suits = listOf("S", "C", "H", "D")
    val deck = mutableListOf<Card>()

    for (s in suits){
        for (rank in 6..14){
            val card = Card(rank = rank, suit = s)
            deck.add(card)
        }
    }

    deck.shuffle()
    return deck
}

fun dealCards(deck: MutableList<Card>, players: List<Player>) {
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
fun findFirstAttacker(players: List<Player>, trump: String): Player{
    var lowestTrumps = Array<Card?>(players.size){null}

    for (i in players.indices){
        var lowestTrumpInHand: Card? = null

        for (card in players[i].hand){
            if (lowestTrumpInHand == null && card.suit == trump){
                lowestTrumpInHand = card
            }

            if (lowestTrumpInHand != null && card.suit == trump){
                if (card.rank < lowestTrumpInHand.rank)
                    lowestTrumpInHand = card
            }
        }

        lowestTrumps[i] = lowestTrumpInHand
    }

    println("Lowest Trumps AFTER CALCULATION: ")
    lowestTrumps.forEach { print(it) }
    println("\n")
    // So far ok.

    if (lowestTrumps.all { it == null }){
        println("No trumps on hands found. The first attacker is chosen randomly.")
        return players[Random.nextInt(0, players.size)]
    }

    var lowestRank = 6
    var indexOfSmallestTrump = 0
    for (i in 0 until lowestTrumps.size){
        if (lowestTrumps[i] == null){
            continue
        }
        if (lowestTrumps[i]!!.rank <= lowestRank){
            indexOfSmallestTrump = i
            lowestRank = lowestTrumps[i]!!.rank
        }
    }

    println("The smallest Trump at the start of game is: ${lowestTrumps[indexOfSmallestTrump]}")

    return players[indexOfSmallestTrump]
}

class Game(val players: List<Player>) {
    val deck = getDeck()

    fun play(){
        dealCards(deck, players)
        val turnUp = deck.last()
        println("Turnup: $turnUp")
        deck.forEach { println(it) }

        println("${players[0].name}'s Cards:")
        players[0].showHand()

        println("\n${players[1].name}'s Cards:")
        players[1].showHand()

        var attacker = findFirstAttacker(players, turnUp.suit)
        println("\nFirst attacker is ${attacker.name}")


    }
}

