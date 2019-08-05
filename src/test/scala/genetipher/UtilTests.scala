package genetipher

import org.scalatest.FunSuite


class UtilTests extends FunSuite {

    test("buildPlaintext works as expected") {
        val encoder = Seq('d', 'e', 'f').toVector
        val ciphertext = "abbcab"

        val expected = "deefde"

        val plaintext = Util.buildPlaintext(ciphertext, encoder)

        assertResult(expected)(plaintext)
    }

}
