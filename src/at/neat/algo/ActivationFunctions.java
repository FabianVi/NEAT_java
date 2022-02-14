package at.neat.algo;

import java.util.Arrays;

public enum ActivationFunctions { // see Notion (Java NEAT implementation) i.e. https://www.notion.so/Java-NEAT-implementation-8cdad948fc6a4578adf689634be8d8e4
    // General AF
    Identity('I'),
    BinaryStep('B'),

    Sigmoid('S'),
    TanH('T'), // Hyperbolic tangent
    ReLU('R'), // Rectified Liear Unit
    GELU('G'), // Gaussian Error Linear Unit
    Softplus('F'),
    ELU('E'), // Exponential linear unit
    SELU('U'), // Scaled exponential linear unit
    LeakyReLU('L'), // Leaky rectified linear unit
    PReLU('P'), // Parametric rectified linear unit
    SiLU('s'), // Sigmoid linear unit
    Gaussian('g'),

    // Synthetic AF
    SoftMax('m'),
    Maxout('M');

    private final char code;

    ActivationFunctions(char c) {
        this.code = c;
    }

    ActivationFunctions getActivationFunction(char c) {
        for(ActivationFunctions AF : ActivationFunctions.values())
            if(AF.getCode()==c) return AF;

        return null;
    }

    public char getCode() {
        return code;
    }
}
