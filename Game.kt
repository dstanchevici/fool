package fool

import kotlin.random.Random

class Game(private val players: Array<Player>) {
    private fun createDeck(): MutableList<Card>{
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

    private val deck = createDeck()
    private val turnUp = deck.last()

    private fun dealCards() {
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

    private fun findFirstAttackerIndex(): Int?{
        val trumpSuit = turnUp.suit
        var lowestTrump = 14
        var attackerIndex: Int? = null

        for (i in players.indices){
            val pTrumps = players[i].hand.filter { it.suit == trumpSuit }
            pTrumps.forEach{
                if (it.rank < lowestTrump){
                    lowestTrump = it.rank
                    attackerIndex = i
                }
            }
        }
        return attackerIndex
    }

    private fun printTable(attacker: Player, defender: Player, playedCards: MutableList<Card>){
        repeat(30) {print("* ")}
        println()
        repeat(10){print(" ")}
        println("TURNUP: $turnUp ")
        repeat(30) {print("* ")}
        println()

        println("ATTACKER ${attacker.name}'s current hand:")
        attacker.showHand()
        println()

        println("DEFENDER ${defender.name}'s current hand:")
        defender.showHand()
        println()

        if (playedCards.isEmpty()){
            println("No cards have been played yet.")
        } else {
            println("ATTACKER PLAYS: \tDEFENDER COVERS WITH:")
            var counter = 1
            for (card in playedCards){
                if (counter % 2 != 0){
                    print("$card\t\t\t")
                    counter++
                } else{
                    print("$card\n")
                    counter++
                }

            }
        }

        println()
        repeat(10) {print("* ")}
        println()
        println()
    }

    private fun getInput(player: Player): Card? {
        println("${player.name}, choose a card from your hand by entering its index number. Or type 's' to stop the attack. ")
        var inputString = readln()

        if (inputString.trim() == "s")
            return null

        while (inputString.contains("""\D""".toRegex()) || inputString.trim().toInt() !in 1..player.hand.lastIndex+1){
            println("Please double check and try again.")
            inputString = readln()
        }
        return player.hand[inputString.trim().toInt() - 1]
    }

    private fun checkIfAttackerCardIsPlayable(attackerCard: Card, playedCards: MutableList<Card>): Boolean{
        if (playedCards.isEmpty())
            return true
        playedCards.forEach {
            if (it.rank == attackerCard.rank)
                return true
        }
        return false
    }

    private fun checkIfDefenderCardIsPlayable(attackerCard: Card, defenderCard: Card): Boolean{
        val trumpSuit = turnUp.suit
        return (
                defenderCard.suit == attackerCard.suit && defenderCard.rank > attackerCard.rank ||
                defenderCard.suit == trumpSuit && attackerCard.suit != trumpSuit
                )
    }

    private fun playRound(attackerIndex: Int): Int {
        val defenderIndex = if (attackerIndex < players.lastIndex) attackerIndex+1 else 0
        val attacker = players[attackerIndex]
        val defender = players[defenderIndex]
        val playedCards = mutableListOf<Card>()

        while (attacker.hand.isNotEmpty() || defender.hand.isNotEmpty()){
            // Attacker's move
            printTable(attacker, defender, playedCards)
            var attackerCard: Card? = getInput(attacker) ?: return if (attackerIndex < players.lastIndex) attackerIndex+1 else 0
            var isAttackerCardPlayable = checkIfAttackerCardIsPlayable(attackerCard!!, playedCards)

            while (isAttackerCardPlayable == false){
                println("${attacker.name}, you can't attack with $attackerCard. Try again.")
                attackerCard = getInput(attacker)?: return if (attackerIndex < players.lastIndex) attackerIndex+1 else 0
                isAttackerCardPlayable = checkIfAttackerCardIsPlayable(attackerCard, playedCards)
            }

            playedCards.add(attackerCard!!)
            attacker.hand.remove(attackerCard)

            // Defender's move
            printTable(attacker, defender, playedCards)
            var defenderCard: Card? = getInput(defender) ?: return if (defenderIndex < players.lastIndex) defenderIndex+1 else 0
            var isDefenderCardPlayable = checkIfDefenderCardIsPlayable(attackerCard, defenderCard!!)

            while (isDefenderCardPlayable == false){
                println("${defender.name}, you can't cover $attackerCard with $defenderCard. Try again.")
                defenderCard = getInput(defender) ?: return if (defenderIndex < players.lastIndex) defenderIndex+1 else 0
                isDefenderCardPlayable = checkIfDefenderCardIsPlayable(attackerCard, defenderCard)
            }

            playedCards.add(defenderCard!!)
            defender.hand.remove(defenderCard)

            printTable(attacker, defender, playedCards)
        }

        return 0
    }

    fun play(){
        dealCards()

        var firstAttackerIndex = findFirstAttackerIndex()
        if (firstAttackerIndex == null)
            firstAttackerIndex = Random.nextInt(0, players.size)

        var nextAttackerIndex = playRound(firstAttackerIndex)

    }

    operator fun invoke() { play() }
}

