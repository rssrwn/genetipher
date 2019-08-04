package genetipher

import scala.io.Source


object EnglishScore {

    private val TRIGRAM_FILE = "resources/ngrams/english_trigrams.txt"

    private val NGRAM_LENGTH = 3

    private val scoreMap = {
        println(s"Loading scores from ngram file: $TRIGRAM_FILE")

        val bufferedSource = Source.fromFile(TRIGRAM_FILE)
        val ngramCountPairs = bufferedSource.getLines().map { line =>
            val strs = line.split(" ")
            val ngram = strs(0)
            val count = strs(1).toLong
            (ngram, count)
        }

        val totalCount = ngramCountPairs.map(_._2).sum[Long]

        val scores = ngramCountPairs.map { case(ngram, count) =>
            (ngram, math.log(count / totalCount))
        }.toMap

        bufferedSource.close()

        println("Successfully loaded counts into the score map")

        scores
    }

    def score(text: String): Double = {
        text.toSeq.sliding(NGRAM_LENGTH).map {
            str => scoreMap(str.mkString)
        }.sum
    }

}
