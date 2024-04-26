package clientserveur3.clientserveur3.calcul;

public class OperationDiv implements ICalcul {
    public Double calcul(
            Integer a,
            Integer b
    )
    {
        if (b == 0)
        {
            throw new IllegalArgumentException("Division par zéro !");
        }
        return a.doubleValue() / b.doubleValue();
    }
}
