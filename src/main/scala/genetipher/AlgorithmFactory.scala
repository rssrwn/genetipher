package genetipher

import geneticsearch.algorithm.{GeneticAlgorithm, GeneticAlgorithmBuilder}
import geneticsearch.operators.{Selection, Mutation}


object AlgorithmFactory {

    def lamdbaMuSubsSolver(cipherText: String, lambda: Int, mu: Int): GeneticAlgorithm[Char] = {
        new GeneticAlgorithmBuilder[Char]()
                .withFitnessOp(Operators.subsFitnessOp(cipherText))
                .withSelectionOp(Selection.selectBest(lambda))
                .withMutationOp(Mutation.appendMutatedPop(mu))
                .build()
    }

}
