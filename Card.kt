package fool

class Card (val rank: Int, val suit: String) {
    override fun toString(): String {
        return when (rank) {
            11 -> "[Jack of $suit]"
            12 -> "[Queen of $suit]"
            13 -> "[King of $suit]"
            14 -> "[Ace of $suit]"
            else -> "[$rank of $suit]"
        }
    }
}