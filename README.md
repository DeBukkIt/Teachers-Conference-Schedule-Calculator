# Teachers' Conference Schedule Calculator
EN: Calculates an optimised schedule for the order of the report conferences of several learning groups so that the participating teachers can go home as early as possible.

DE: Berechnet eine optimierte Reihenfolge von Zeugniskonferenzen mehrerer Klassen, sodass möglichst viele Lehrkräfte möglichst frühzeitig nachhause fahren können.

## Example
### Input
![grafik](https://github.com/DeBukkIt/Teachers-Conference-Schedule-Calculator/assets/1151380/9e1984c4-b258-48ad-ae6c-39790d23ad01)

### Output
```
=== Conference Order Optimizer ===
Version: 1.1.0 (22.12.2023)       

== Optimal conference order ==
1. ["Class C", 5 teachers]
2. ["Class A", 4 teachers]
3. ["Class D", 2 teachers]
4. ["Class B", 2 teachers]

Found best 1 of 24 possible solutions in 30,95 ms

Checking for possible parallelization of conferences...
The sets of teachers in conferences Class D and Class B are disjunctive.

== Optimal conference order with parallel conferences ==
1. ["Class C", 5 teachers]
2. ["Class A", 4 teachers]
3. ["Class D", 2 teachers || "Class B", 2 teachers]
```
