package utils;

import java.util.Objects;

/*
Cette classe permet de créer des couples d'entier ou autres elements afin de contourner la méthode contains de l'Arraylist qui ne peut pas
voir si un int[] est déja présent dans l'arraylist.
 */

//Pour notre grid : A = I / B = J

public class Couple<A, B> {
    private A a;
    private B b;

    public Couple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public Couple() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Couple<?, ?> couple = (Couple<?, ?>) o;
        return Objects.equals(a, couple.a) && Objects.equals(b, couple.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public void setA(A a) {
        this.a = a;
    }

    public void setB(B b) {
        this.b = b;
    }

}
