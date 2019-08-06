package genetipher

import geneticsearch.algorithm.{GeneticAlgorithm, GeneticAlgorithmBuilder}
import geneticsearch.operators.{Mutation, Selection}


object AlgorithmFactory {

    def lamdbaMuSubsSolver(cipherText: String, lambda: Int, mu: Int): GeneticAlgorithm[Char] = {
        new GeneticAlgorithmBuilder[Char]()
                .withFitnessOp(Operators.subsFitnessOp(cipherText))
                .withSelectionOp(Selection.selectBest[Char](lambda))
                .withMutationOp(Mutation.appendMutatedPop[Char](mu))
                .withNumIterations(1000)
                .build()
    }

}
