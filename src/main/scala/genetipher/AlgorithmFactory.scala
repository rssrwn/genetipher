package genetipher

import geneticsearch.algorithm.{GeneticAlgorithm, GeneticAlgorithmBuilder}
import geneticsearch.operators.{Completion, Mutation, Selection}


object AlgorithmFactory {

    def lamdbaMuSubsSolver(cipherText: String,
                           lambda: Int,
                           mu: Int,
                           numIters: Int,
                           completionThreshold: Double): GeneticAlgorithm[Char] = {

        new GeneticAlgorithmBuilder[Char]()
                .withFitnessOp(Operators.subsFitnessOp(cipherText))
                .withSelectionOp(Selection.selectBest[Char](lambda))
                .withMutationOp(Mutation.appendMutatedPop[Char](mu))
                .withCompletionOp(Completion.scoreThreshold(completionThreshold), 20)
                .withNumIterations(numIters)
                .build()
    }

}
