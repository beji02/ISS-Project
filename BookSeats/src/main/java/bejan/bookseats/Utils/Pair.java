package bejan.bookseats.Utils;

import java.util.Objects;

public class Pair<type1, type2> {
    private type1 first;
    private type2 second;

    public Pair(type1 first, type2 second) {
        this.first = first;
        this.second = second;
    }

    public type1 getFirst() {
        return first;
    }

    public void setFirst(type1 first) {
        this.first = first;
    }

    public type2 getSecond() {
        return second;
    }

    public void setSecond(type2 second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair<?, ?> pair)) return false;

        if (!Objects.equals(first, pair.first)) return false;
        return Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
