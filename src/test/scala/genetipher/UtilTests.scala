package genetipher

import org.scalatest.FunSuite


class UtilTests extends FunSuite {

    test("buildPlaintext works for lowercase letters") {
        val encoder = Seq('d', 'e', 'f').toVector
        val ciphertext = "abbcab"

        val expected = "deefde"

        val plaintext = Util.buildPlaintext(ciphertext, encoder)

        assertResult(expected)(plaintext)
    }

    test("buildPlaintext works for all chars") {
        val encoder = Seq('d', 't', 'p').toVector
        val ciphertext = "A.ab{C]b?"

        val expected = "D.dt{P]t?"

        val plaintext = Util.buildPlaintext(ciphertext, encoder)

        assertResult(expected)(plaintext)
    }

    test("isLetter returns true for letters") {
        val letter1 = 'a'
        val letter2 = 'Z'
        val letter3 = 'q'

        val expected = true

        assertResult(expected)(Util.isLetter(letter1))
        assertResult(expected)(Util.isLetter(letter2))
        assertResult(expected)(Util.isLetter(letter3))
    }

    test("isLetter returns false for non-letter chars") {
        val char1 = ','
        val char2 = ' '
        val char3 = '}'

        val expected = false

        assertResult(expected)(Util.isLetter(char1))
        assertResult(expected)(Util.isLetter(char2))
        assertResult(expected)(Util.isLetter(char3))
    }

    test("isUpperCase returns true for uppercase letters") {
        val letter1 = 'A'
        val letter2 = 'Z'
        val letter3 = 'T'

        val expected = true

        assertResult(expected)(Util.isUpperCase(letter1))
        assertResult(expected)(Util.isUpperCase(letter2))
        assertResult(expected)(Util.isUpperCase(letter3))
    }

    test("isUpperCase returns false for lowercase letters") {
        val letter1 = 'a'
        val letter2 = 'z'
        val letter3 = 'p'

        val expected = false

        assertResult(expected)(Util.isUpperCase(letter1))
        assertResult(expected)(Util.isUpperCase(letter2))
        assertResult(expected)(Util.isUpperCase(letter3))
    }

}
