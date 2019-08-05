package genetipher

import scala.io.Source


object EnglishScore {

    private val TRIGRAM_FILE = "resources/ngrams/english_trigrams.txt"

    private val NGRAM_LENGTH = 3

    private val (totalCount: Double, scoreMap: Map[String, Double]) = {
        println(s"Loading scores from ngram file: $TRIGRAM_FILE")

        val bufferedSource = Source.fromFile(TRIGRAM_FILE)
        val pairs = bufferedSource.getLines().map { line =>
            val strs = line.split(" ")
            val ngram = strs(0)
            val count = strs(1).toLong
            (ngram, count)
        }.toSeq

        val ngramCount = pairs.map(_._2).sum[Long].toDouble

        val scores = pairs.map { case(ngram, count) =>
            (ngram, math.log(count / ngramCount))
        }.toMap

        println("Successfully loaded counts into the score map")

        bufferedSource.close()

        (ngramCount, scores)
    }

    def score(text: String): Double = {
        text.replace(" ", "").sliding(NGRAM_LENGTH).map { strSeq =>
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
