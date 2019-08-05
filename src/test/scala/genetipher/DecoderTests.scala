package genetipher

import geneticsearch.Types.{MutationFunc, Population}
import geneticsearch.genotype.Sequence
import org.scalatest.FunSuite

import scala.util.Random


class DecoderTests extends FunSuite {

    test("The main method can solve a substitution cipher") {
        val plaintext = "the quick brown fox jumped over the lazy dog"
        val ciphertext = encode(plaintext)
        val decodedOpt = new Decoder(ciphertext, Some("substitution")).decode()

        assertResult(plaintext)(decodedOpt.get.head)
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
        val numSwaps = 2
        val mutFunc = testDecoder.randCharSwapMutation(numSwaps)

        val genotype = new Sequence[Char](allChars.toSeq, null, null)

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

    private class DecoderTest() extends Decoder("", None) {
        override def randomPop(popSize: Int): Population[Char] = super.randomPop(popSize)
        override def randCharSwapMutation(numSwaps: Int): MutationFunc[Sequence[Char]] = super.randCharSwapMutation(numSwaps)
    }

}
