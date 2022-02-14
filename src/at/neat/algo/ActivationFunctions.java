package at.neat.algo;

public enum ActivationFunctions { // see Notion (Java NEAT implementation) i.e. https://www.notion.so/Java-NEAT-implementation-8cdad948fc6a4578adf689634be8d8e4
    // General AF
    Identity,
    BinaryStep,

    Sigmoid,
    TanH, // Hyperbolic tangent
    ReLU, // Rectified Liear Unit
    GELU, // Gaussian Error Linear Unit
    Softplus,
    ELU, // Exponential linear unit
    SELU, // Scaled exponential linear unit
    LeakyReLU, // Leaky rectified linear unit
    PReLU, // Parametric rectified linear unit
    SiLU, // Sigmoid linear unit
    Gaussian,

    // Synthetic AF
    SoftMax,
    Maxout
}
