package genetipher

import geneticsearch.Types.{MutationFunc, Population}
import geneticsearch.genotype.Sequence
import org.scalatest.FunSuite

import scala.util.Random


class DecoderTests extends FunSuite {

    test("The main method can solve a substitution cipher") {
//        val plaintext = "the quick brown fox jumped over the lazy dogs"
//
//        val plaintext = "It was a bright cold day in April, and the clocks were striking thirteen. Winston Smith, his chin nuzzled into his breast in an effort to escape the vile wind, slipped quickly through the glass doors of Victory Mansions, though not quickly enough to prevent a swirl of gritty dust from entering along with him.\n\nThe hallway smelt of boiled cabbage and old rag mats. At one end of it a coloured poster, too large for indoor display, had been tacked to the wall. It depicted simply an enormous face, more than a metre wide: the face of a man of about forty-five, with a heavy black moustache and ruggedly handsome features. Winston made for the stairs. It was no use trying the lift. Even at the best of times it was seldom working, and at present the electric current was cut off during daylight hours. It was part of the economy drive in preparation for Hate Week. The flat was seven flights up, and Winston, who was thirty-nine and had a varicose ulcer above his right ankle, went slowly, resting several times on the way. On each landing, opposite the lift-shaft, the poster with the enormous face gazed from the wall. It was one of those pictures which are so contrived that the eyes follow you about when you move. BIG BROTHER IS WATCHING YOU, the caption beneath it ran."
//
//        val ciphertext = encode(plaintext)
//        val decodedOpt = new Decoder(ciphertext, Some("substitution")).decode()
//
//        assertResult(plaintext)(decodedOpt.get.head)
    }

    test("randomPop returns a pop of expected length") {
        val testDecoder = new DecoderTest()
        val popSize = 100
        val pop = testDecoder.randomPop(popSize)

        assertResult(popSize)(pop.length)
    }

    test("randomPop contains no duplicate chars") {
        val testDecoder = new DecoderTest()
        val pop = testDecoder.randomPop(100)

        val expectedDistinctLength = 26

        for (genotype <- pop) {
            assertResult(expectedDistinctLength)(genotype.distinct.length)
        }
    }

    test("randCharSwapMutation swaps the correct number of elements") {
        val testDecoder = new DecoderTest()
        val numSwaps = 1
        val mutFunc = testDecoder.randCharSwapMutation(numSwaps)

        val genotype = new Sequence[Char](allLetters.toSeq, null, null)

        val mutated = mutFunc(genotype)

        assert(genotype.length == mutated.length)

        val numChanged = genotype.zip(mutated).map{ case (original, mut) =>
            if (original == mut) {
                0
            } else {
                1
            }
        }.sum

        assertResult(numSwaps * 2)(numChanged)
    }

    private def encode(text: String): String = {
        val charMap = text.foldLeft(Map[Char, Char](' ' -> ' ')) { (charMap, char) =>
            if (charMap.contains(char)) {
                charMap
            } else {
                val currChars = charMap.values.toSet
                val remainingChars = allLetters.diff(currChars)

                if (isLetter(char)) {
                    val lowerChar = randCharFromSet(remainingChars)
                    if (isUpperCase(char)) {
                        charMap + (char -> lowerChar.toUpper) + (char.toLower -> lowerChar)
                    } else {
                        charMap + (char -> lowerChar) + (char.toUpper -> lowerChar.toUpper)
                    }
                } else {
                    charMap + (char -> char)
                }
            }
        }

        val notMapped: Seq[Char] = allLetters.diff(charMap.values.toSet.filter(char => Util.isLetter(char) && !Util.isUpperCase(char))).toSeq
        val newMap: Map[Char, Char] = charMap ++ Map('x' -> notMapped(0), 'j' -> notMapped(1))

        val letters = newMap.filter(p => Util.isLetter(p._1))
        val decoder = letters.map(_.swap).toSeq.sortWith(_._1 < _._1).map(_._2).filter(!Util.isUpperCase(_)).toVector

        val ct = text.map(char => charMap(char))

        val pt = Util.buildPlaintext(ct, decoder)

        println(EnglishScore.score(ct))
        println(EnglishScore.score(pt))

        ct
    }

    private def randCharFromSet(chars: Set[Char]): Char = {
        val rand = Random.nextInt(chars.size)
        chars.toVector(rand)
    }

    private val allLetters: Set[Char] = {
        (Util.ASCII_LOWER_A to Util.ASCII_LOWER_Z).map(i => i.toChar).toSet
    }

    private def isLetter(letter: Char): Boolean = {
        allLetters.contains(letter) || allLetters.map(_.toUpper).contains(letter)
    }

    private class DecoderTest() extends Decoder("", None) {
        override def randomPop(popSize: Int): Population[Char] = super.randomPop(popSize)
        override def randCharSwapMutation(numSwaps: Int): MutationFunc[Sequence[Char]] = super.randCharSwapMutation(numSwaps)
    }

    private def isUpperCase(char: Char): Boolean = {
        val ascii = char.toInt
        if (ascii >= 65 && ascii <= 90) {
            true
        } else {
            false
        }
    }

}
