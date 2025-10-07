# Informe de corrección
**Fundamentos de Programación Funcional y Concurrente**
Documento realizado por las estudiantes:
*  **Mariana de los Ángeles Viera | 202569104  | mariana.viera@correounivalle.edu.co
*  **Náthalie Wilches             | 202569482  | nathalie.wilches@correounivalle.edu.co

--------------------------------------------------------------------------------

## Argumentación de corrección de programas
La corrección de un programa $P_f$ se argumenta demostrando que siempre devuelve
el resultado esperado $f(a)$ de acuerdo con su especificación. Es decir, se debe
demostrar el siguiente teorema:

$$ \forall a \in A : P_f(a) == f(a) $$

Para las funciones de alto orden (HOF) que definen conjuntos difusos (1.2.1 y 1.2.2,
la corrección se basa en la validación de la fórmula matemática y el rango de
pertenencia. Para las funciones relacionales que usan recursión de cola (1.2.3), la
corrección se demuestra mediante el formalismo de los invariantes de iteración.
--------------------------------------------------------------------------------

### 1.2.1. Conjunto Difuso de Números Grandes (`grande`)

**Especificación ($f_{grande}$):**
La función $f_{grande}(x; d, e)$ define el grado de pertenencia.
$$ f_{grande}(x) = \left(\frac{x}{x + d}\right)^e $$
donde $d \ge 1$ y $e > 1$.

Programa en Scala:
```scala
def grande(d: Int, e: Int): Int => Double = {
  x => math.pow(x.toDouble / (x + d).toDouble, e.toDouble)
}
```
**Teorema a demostrar:**
$$ \forall x \in \mathbb{N}, d \in \mathbb{N}^+, e \in \mathbb{N} \land e>1: 0.0 \le P_{grande}(x) \le 1.0 $$

*   **Argumentación de Corrección (Criterios 1 y 2):**
    1.  **Análisis de la base de la potencia:** $\left(\frac{x}{x + d}\right)$
        *   Como $x \ge 0$ y $d \ge 1$, la base siempre cumple: $$ 0 \le \frac{x}{x + d} < 1 $$
    2.  **Análisis del exponente:** $()^e$. Dado que la base está entre 0 y 1, elevarla a una potencia positiva $e > 1$ mantiene el resultado dentro del mismo intervalo.

**Conclusión:**
$$ P_{grande}(x) \in [0.0, 1.0] \implies P_{grande} \text{ es correcta con respecto a la especificación de grado de pertenencia.}$$

### 1.2.2. Complemento, Unión e Intersección

La corrección se basa en la equivalencia directa entre el código y la definición formal.

*   **A. Complemento (`complemento`):**
    *   **Especificación:** $f_{\neg S}(x) = 1 - f_{S}(x)$.
    *   **Programa en Scala:**
```scala
def complemento(c: Int => Double): Int => Double = {
  x => 1.0 - c(x)
}
```
    *   **Corrección:** La implementación es una traducción directa de la especificación:
$$ P_{complemento}(c)(x) \equiv 1 - c(x) \equiv f_{\neg S}(x) $$

*   **B. Unión (`union`):**
    *   **Especificación:** $f_{S_1 \cup S_2} = \max(f_{S_1}, f_{S_2})$.
    *   **Programa en Scala:**
```scala
def union(cd1: Int => Double, cd2: Int => Double): Int => Double = {
  x => math.max(cd1(x), cd2(x))
}
```
    *   **Corrección:** La función `math.max` implementa directamente la operación de la especificación.

*   **C. Intersección (`interseccion`):**
    *   **Especificación:** $f_{S_1 \cap S_2} = \min(f_{S_1}, f_{S_2})$.
    *   **Programa en Scala:**
```scala
def interseccion(cd1: Int => Double, cd2: Int => Double): Int => Double = {
  x => math.min(cd1(x), cd2(x))
}
```
    *   **Corrección:** La función `math.min` implementa directamente la operación de la especificación.

--------------------------------------------------------------------------------

### 1.2.3. Inclusión e Igualdad (Recursión de Cola)

#### Argumentando sobre corrección de programas iterativos
Para argumentar la corrección de programas implementados con recursión de cola, se debe formalizar la iteración mediante invariantes.

##### A. Inclusión (`inclusion`)
*   **Especificación ($f_{inclusion}$):** $S_1 \subseteq S_2$ si y solo si $\forall x \in U: f_{S_1}(x) \le f_{S_2}(x)$.
*   **Programa en Scala (Recursión de Cola):**
```scala
def inclusion(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
  @scala.annotation.tailrec
  def check_inclusion(i: Int): Boolean = {
    if (i > 1000) {
      true
    } else if (cd1(i) <= cd2(i)) {
      check_inclusion(i + 1)
    } else {
      false
    }
  }
  check_inclusion(0)
}
```
**Argumentación de Corrección (Invariantes de Iteración) (Criterios 1 y 2):**
1.  **Estado $s$**: Índice de verificación $i$. $$ s = (i) $$
2.  **Estado inicial $s_0$**: $i=0$. $$ s_0 = (0) $$
3.  **Estado final $s_f$**: Cuando $i$ supera el límite superior de búsqueda (1000). $$ s_f = (1001) $$
4.  **Invariante de la iteración $\text{Inv}(s)$**: La inclusión se ha mantenido cierta para todos los números revisados $k \in [0, i-1]$.
    $$ \text{Inv}(i) \equiv \forall k \in [0, i-1] : f_{S_1}(k) \le f_{S_2}(k) $$
5.  **Transformación de estados $\text{transformar}(s)$**:
    $$ \text{transformar}(i) \to (i+1) \quad \text{si } f_{S_1}(i) \le f_{S_2}(i) $$
    Si la condición falla, la función termina inmediatamente devolviendo $false$.

**Explicación de los llamados en la ejecución (Criterio 3):**
La función `check_inclusion` utiliza **recursión de cola** (`@scala.annotation.tailrec`). La llamada recursiva es la **última instrucción** en la función, lo que permite al compilador transformar el proceso en un **bucle optimizado**. Esto implica que **no se acumulan marcos de llamada en la pila de ejecución**, sino que el estado $i$ se actualiza en cada iteración, garantizando la eficiencia y validando la corrección por invariantes.

---

##### B. Igualdad (`igualdad`)
*   **Especificación ($f_{igualdad}$):** Dos conjuntos son iguales si hay inclusión mutua.
    $$ S_1 = S_2 \iff (S_1 \subseteq S_2 \land S_2 \subseteq S_1) $$
*   **Programa en Scala:**

```scala
def igualdad(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
  inclusion(cd1, cd2) && inclusion(cd2, cd1)
}
```
*   **Corrección:** $P_{igualdad}$ es correcto por definición, basándose en la doble aplicación de $P_{inclusion}$, cuya corrección ha sido formalmente demostrada.
```