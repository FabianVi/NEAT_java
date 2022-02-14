package at.neat.algo;

import java.util.ArrayList;
import java.util.HashMap;

public class Neuron {

    protected NeuronType neuronType;

    protected ActivationFunctions activationFunction;
    protected double alpha;

    protected ArrayList<Neuron> connectedNeuronsPrev;

    protected ArrayList<Neuron> connectedNeuronsAfter;
    protected HashMap<Integer,Double> weights;

    protected double value;

    protected int timesFired;

    public Neuron(NeuronType neuronType, ActivationFunctions activationFunction) {
        this.neuronType = neuronType;
        this.activationFunction = activationFunction;

        connectedNeuronsPrev = new ArrayList<>();
        connectedNeuronsAfter = new ArrayList<>();

        weights = new HashMap<>();

        timesFired = 0;
    }

    public void fire() {
        fire(0.0d, 2);
    }

    public void fire(double in) {
        fire(in, 2);
        try {
            Thread.sleep(100);

        } catch (InterruptedException e) {};
    }

    public void fire(double in, int maxFired) {

        if(timesFired>=maxFired)
            return;

        timesFired++;

        if(neuronType!=NeuronType.Input)
            for(Neuron n : connectedNeuronsPrev)
                in += n.getWeightedValue(this);

        value = execute(in);

        // DEBUGGING
        System.out.printf("Fire of an %s value: %f \r\n",neuronType == NeuronType.Input ? "Input" : neuronType == NeuronType.Hidden ? "Hidden" : "Output",value);

        if(neuronType!=NeuronType.Output) {
            for(Neuron n : connectedNeuronsAfter)
                n.fire(0.0d,maxFired);
        }
    }

    private double execute(double in) {
        switch (activationFunction) {
            case Identity:
                return in;

            case BinaryStep:
                return in>=0 ? 1 : 0;

            case Sigmoid:
                return 1/(1+Math.pow(Math.E,in*-1));

            case TanH:
                return (Math.pow(Math.E,in) - Math.pow(Math.E,in*-1)) / (Math.pow(Math.E,in) + Math.pow(Math.E,in*-1));

            case ReLU:
                return in<=0 ? 0 : in;

            case GELU:
                return in/2 * (1+erf(in/Math.sqrt(2)));

            case Softplus:
                Math.log1p(Math.pow(Math.E,in));

            case ELU:
                return in<=0 ? alpha*(Math.pow(Math.E,in)-1) : in;

            case SELU:
                return in>=0 ? in : alpha*(Math.pow(Math.E,in)-1);

            case LeakyReLU:
                return in<0 ? 0.01*in : in;

            case PReLU:
                return in<0 ? alpha*in : in;

            case SiLU:
                return in/(1+Math.pow(Math.E,-1*in));

            case Gaussian:
                return Math.pow(Math.E,-1*(in*in));

            default:
                return 0.0d;
        }
    }

    public double getValue() {
        return value;
    }

    public double getWeightedValue(Neuron n) {
        return value*weights.get(n.hashCode());
    }

    protected void addNeuronPrev(Neuron n) {
        connectedNeuronsPrev.add(n);
    }

    public void addNeuron(Neuron n) {
        this.addNeuron(n, Math.random());
    }

    public void addNeuron(Neuron n, double w) {
        n.addNeuronPrev(this);
        connectedNeuronsAfter.add(n);
        weights.put(n.hashCode(),w);
    }

    public void reset() {
        timesFired = 0;
        value = 0;
    }

    // This is just an approximation! The source is: https://hewgill.com/picomath/java/Erf.java.html
    private double erf(double x) {
        // constants
        final double a1 =  0.254829592;
        final double a2 = -0.284496736;
        final double a3 =  1.421413741;
        final double a4 = -1.453152027;
        final double a5 =  1.061405429;
        final double p  =  0.3275911;

        // Save the sign of x
        double sign = 1;
        if (x < 0) {
            sign = -1;
        }
        x = Math.abs(x);

        // A&S formula 7.1.26
        double t = 1.0/(1.0 + p*x);
        double y = 1.0 - (((((a5*t + a4)*t) + a3)*t + a2)*t + a1)*t*Math.exp(-x*x);

        return sign*y;
    }

}
