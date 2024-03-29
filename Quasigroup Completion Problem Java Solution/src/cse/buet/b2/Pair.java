package cse.buet.b2;

public class Pair<A, B>
//        implements Comparable<Pair<A,B>>
{
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }

    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return
                    ((  this.first == otherPair.first ||
                            ( this.first != null && otherPair.first != null &&
                                    this.first.equals(otherPair.first))) &&
                            (  this.second == otherPair.second ||
                                    ( this.second != null && otherPair.second != null &&
                                            this.second.equals(otherPair.second))) );
        }

        return false;
    }

    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }

    public A getFirst() {
        return first;
    }

//    public void setFirst(A first) {
//        this.first = first;
//    }

    public B getSecond() {
        return second;
    }

//    @Override
//    public int compareTo(Pair<A, B> o) {
//        Integer thisDomainSize = null;
//        int otherDomainSize = 0;
//        try {
//            thisDomainSize = Varriables.getDomain().get(this).size();
//            otherDomainSize = Varriables.getDomain().get(o).size();
//        } catch (Exception e) {
//            return 0;
//        }
//        return thisDomainSize.compareTo(otherDomainSize);
//    }

//    public void setSecond(B second) {
//        this.second = second;
//    }
}

