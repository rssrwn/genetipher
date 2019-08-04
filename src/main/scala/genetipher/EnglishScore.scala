package genetipher

import scala.io.Source


object EnglishScore {

    private val TRIGRAM_FILE = "resources/ngrams/english_trigrams.txt"

    private val NGRAM_LENGTH = 3

    private val ngramCountPairs = {
        println(s"Loading scores from ngram file: $TRIGRAM_FILE")

        val bufferedSource = Source.fromFile(TRIGRAM_FILE)
        val pairs = bufferedSource.getLines().map { line =>
            val strs = line.split(" ")
            val ngram = strs(0)
            val count = strs(1).toLong
            (ngram, count)
        }.toSeq

        bufferedSource.close()

        pairs
    }

    private val totalCount = ngramCountPairs.map(_._2).sum[Long].toDouble

    private val scoreMap = {
        val scores = ngramCountPairs.map { case(ngram, count) =>
            (ngram, math.log(count / totalCount))
        }.toMap

        println("Successfully loaded counts into the score map")

        scores
    }

    def score(text: String): Double = {
        text.toSeq.sliding(NGRAM_LENGTH).map { strSeq =>
            val str = strSeq.mkString.toUpperCase
            if (scoreMap.contains(str)) {
                scoreMap(str)
            } else {
                // Give ngrams with 0 occurrences a lower score than 1 occurrence
                math.log(0.01 / totalCount)
            }
        }.sum
    }

}
