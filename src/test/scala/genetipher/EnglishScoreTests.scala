package genetipher

import org.scalatest.FunSuite


class EnglishScoreTests extends FunSuite {

    test("avgNgramScore function gives high scores for english sentences") {
        val sentence = "the dog barked"
        val score = EnglishScore.avgNgramScore(sentence)

        assert(score > 1.0)
    }

    test("avgNgramScore gives low scores for non-english sentences") {
        val sentence = "tkb yxs rdalop"
        val score = EnglishScore.avgNgramScore(sentence)

        assert(score < -1.0)
    }

}
