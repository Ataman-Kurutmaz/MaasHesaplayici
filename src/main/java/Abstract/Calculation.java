package Abstract;

import Entity.Employee;

public interface Calculation<T> {
    float calculate(T t);
}
