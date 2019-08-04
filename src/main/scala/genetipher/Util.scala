package genetipher


object Util {

    private val ASCII_UPPER_A = 65
    private val ASCII_LOWER_A = 97
    private val ASCII_UPPER_Z = 90

    /**
      * Construct the plaintext from a given ciphertext and decoding strategy
      * @param ciphertext Text to be decoded
      * @param subs substitution to be used for decoding (idx 0 refers to 'a' or 'A', idx 1 for 'b' or 'B', etc)
      * @return Plaintext string
      */
    def buildPlaintext(ciphertext: String, subs: Vector[Char]): String = {
        ciphertext.map { char: Char =>
            val ascii = char.toInt
            val idx = if (isUpperCase(char)) {
                ascii - ASCII_UPPER_A
            } else {
                ascii - ASCII_LOWER_A
            }

            subs(idx)
        }
    }

    /**
      * Returns whether the char is uppercase or not
      * Pre: The character is a letter
      * @param char Character to be checked
      * @return Whether the char is uppercase or not
      */
    private def isUpperCase(char: Char): Boolean = {
        val ascii = char.toInt
        if (ascii >= ASCII_UPPER_A && ascii <= ASCII_UPPER_Z) {
            true
        } else {
            false
        }
    }

}
