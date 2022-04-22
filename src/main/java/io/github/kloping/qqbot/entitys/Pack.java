package io.github.kloping.qqbot.entitys;

import java.util.Objects;

/**
 * @author github-kloping
 */
public class Pack<T> {
    private Number op;
    private Number s;
    private T d;
    private String t;

    public Number getOp() {
        return this.op;
    }

    public Pack setOp(Number op) {
        this.op = op;
        return this;
    }

    public Number getS() {
        return this.s;
    }

    public Pack setS(Number s) {
        this.s = s;
        return this;
    }

    public T getD() {
        return this.d;
    }

    public Pack setD(T d) {
        this.d = d;
        return this;
    }

    public String getT() {
        return this.t;
    }

    public Pack setT(String t) {
        this.t = t;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pack pack = (Pack) o;
        return Objects.equals(op, pack.op) && Objects.equals(s, pack.s) && Objects.equals(d, pack.d) && Objects.equals(t, pack.t);
    }

    @Override
    public int hashCode() {
        return Objects.hash(op, s, d, t);
    }

    @Override
    public String toString() {
        return "Pack{" +
                "op=" + op +
                ", s=" + s +
                ", d=" + d +
                ", t='" + t + '\'' +
                '}';
    }
}