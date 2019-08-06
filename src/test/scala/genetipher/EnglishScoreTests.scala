package genetipher

import org.scalatest.FunSuite


class EnglishScoreTests extends FunSuite {

    // TODO
    test("score function gives high scores for english sentences") {
        val sentence = "the dog barked"
        val score = EnglishScore.score(sentence)

        assert(score > -50)
    }

}
