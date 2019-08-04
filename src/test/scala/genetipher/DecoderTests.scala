package genetipher

import org.scalatest.FunSuite

import scala.util.Random


class DecoderTests extends FunSuite {

    test("The main method can solve a substitution cipher") {
        val plaintext = "the quick brown fox jumped"
        val ciphertext = encode(plaintext)
        val decodedOpt = new Decoder(ciphertext, Some("substitution")).decode()

        assertResult(plaintext)(decodedOpt.get.head)
    }

    private def encode(text: String): String = {
        val charMap = text.foldLeft(Map[Char, Char](' ' -> ' ')) { (charMap, char) =>
            if (charMap.contains(char)) {
                charMap
            } else {
                val currChars = charMap.values.toSet
                val remainingChars = allChars.diff(currChars)
                val randChar = randCharFromSet(remainingChars)

                charMap + (char -> randChar)
            }
        }

        text.map(char => charMap(char))
    }

    private def randCharFromSet(chars: Set[Char]): Char = {
        val size = chars.size
        val rand = Random.nextInt(size)
        chars.toVector(rand)
    }

    private val allChars: Set[Char] = {
        (Util.ASCII_LOWER_A to Util.ASCII_LOWER_Z).map(i => i.toChar).toSet
    }

}
