package clientserveur3.clientserveur3.calcul;

public class OperationMoins implements ICalcul {
    public Double calcul(
            Integer a,
            Integer b
    )
    {
        return a.doubleValue() - b.doubleValue();
    }
}
