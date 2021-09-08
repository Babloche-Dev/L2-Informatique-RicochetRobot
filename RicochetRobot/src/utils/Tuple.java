package utils;

/*
Cette classe permet de créer des tuples d'element afin de contourner le probleme du [] mais aussi pour que le
grid puisse contenir une target un robot dans une meme case contenant un mur (soit 3 elements).
 */

//Pour notre grid A = Wall / B = Robot / C = Target

import java.util.Objects;

public class Tuple<A,B,C> {

    private A a;
    private B b;
    private C c;

    public Tuple(A a, B b, C c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?, ?> tuple = (Tuple<?, ?, ?>) o;
        return Objects.equals(a, tuple.a) && Objects.equals(b, tuple.b) && Objects.equals(c, tuple.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }

    public void setA(A a) {
        this.a = a;
    }

    public void setB(B b) {
        this.b = b;
    }

    public void setC(C c) {
        this.c = c;
    }
}
