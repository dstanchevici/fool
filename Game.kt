package fool

import kotlin.random.Random

fun getDeck(players: Array<Player>): MutableList<Card>{
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

fun dealCards(deck: MutableList<Card>, players: Array<Player>) {
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

fun findFirstAttacker(players: Array<Player>, trump: String): Player{
    var lowestTrumps = Array<Card?>(players.size){null}


    for (i in players.indices){
        var lowestTrumpInHand: Card? = null

        for (card in players[i].hand){
            if (lowestTrumpInHand == null && card.suit == trump){
                lowestTrumpInHand = card
            } else if (lowestTrumpInHand != null && card.suit == trump){
                if (card.rank < lowestTrumpInHand.rank)
                    lowestTrumpInHand = card
            }
        }

        lowestTrumps[i] = lowestTrumpInHand
    }

    if (lowestTrumps.all { it == null })
        return players[Random.nextInt(0, players.size)]

    var lowestRank = 6
    var indexOfSmallestTrump = 0
    for (i in 0 until lowestTrumps.size){
        if (lowestTrumps[i] != null && lowestTrumps[i]!!.rank <= lowestRank){
            indexOfSmallestTrump = i
            lowestRank = lowestTrumps[i]!!.rank
        }
    }

    if (lowestTrumps[indexOfSmallestTrump] != null){
        println("The smallest Trump at the start of game is: ${lowestTrumps[indexOfSmallestTrump]}")
    } else {
        "There is no Trump in hand at the start of the game."
    }


    return players[indexOfSmallestTrump]
}

class Game(val players: Array<Player>) {
    val deck = getDeck(players)

    val turnUp: Card
        get() {
            val turnUp = deck.first()
            deck.removeFirst()
            deck.add(turnUp)

            return turnUp
        }


    fun play(){
        dealCards(deck, players)
        println("TURNUP Card: $turnUp")
        deck.forEach { println(it) }


        println("${players[0].name}'s Cards:")
        players[0].showHand()

        println("\n${players[1].name}'s Cards:")
        players[0].showHand()

        var attacker = findFirstAttacker(players, turnUp.suit)
        println("\nFirst attacker is ${attacker.name}")

    }
}