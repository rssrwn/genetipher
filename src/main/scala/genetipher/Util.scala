package genetipher

import scala.util.Random


object Util {

    val ASCII_UPPER_A = 65
    val ASCII_LOWER_A = 97
    val ASCII_UPPER_Z = 90
    val ASCII_LOWER_Z = 122

    val NUM_LETTERS: Int = (ASCII_UPPER_Z - ASCII_UPPER_A) + 1

    /**
      * Construct the plaintext from a given ciphertext and decoding strategy
      * @param ciphertext Text to be decoded
      * @param subs substitution to be used for decoding (idx 0 refers to 'a' or 'A', idx 1 for 'b' or 'B', etc)
      * @return Plaintext string
      */
    def buildPlaintext(ciphertext: String, subs: Vector[Char]): String = {
        ciphertext.map { char: Char =>
            if (!isLetter(char)) {
                char
            } else {
                val ascii = char.toInt
                if (isUpperCase(char)) {
                    subs(ascii - ASCII_UPPER_A).toUpper
                } else {
                    subs(ascii - ASCII_LOWER_A)
                }
            }
        }
    }

    /**
      * Returns whether the char is uppercase or not
      * Pre: The character is a letter
      * @param char Character to be checked
      * @return Whether the char is uppercase or not
      */
    def isUpperCase(char: Char): Boolean = {
        val ascii = char.toInt
        if (ascii >= ASCII_UPPER_A && ascii <= ASCII_UPPER_Z) {
            true
        } else {
            false
        }
    }

    /**
      * Generates a lowercase character uniformly randomly selected
      * @return Random char
      */
    def randChar(): Char = {
        (Util.ASCII_LOWER_A + Random.nextInt(Util.NUM_LETTERS)).toChar
    }

    /**
      * Generates a Seq of all lowercase letters
      * @return Seq of shuffled letters
      */
    def randDistinctCharSeq(): Seq[Char] = {
        Random.shuffle(allLetters).toSeq
    }

    def randCharFromSet(chars: Set[Char]): Char = {
        val size = chars.size
        val rand = Random.nextInt(size)
        chars.toVector(rand)
    }

    val allLetters: Set[Char] = {
        (Util.ASCII_LOWER_A to Util.ASCII_LOWER_Z).map(i => i.toChar).toSet
    }

    def isLetter(letter: Char): Boolean = {
        allLetters.contains(letter) || allLetters.map(_.toUpper).contains(letter)
    }

}
