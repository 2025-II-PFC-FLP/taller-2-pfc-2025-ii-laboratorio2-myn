# Taller 2: Funciones de alto orden
## Fundamentos de Programación funcional y concurrente

**Estudiante(s):** Mariana de los Ángeles Viera Serna - Náthalie Wilches Tamayo

**Correo(s):** mariana.viera@correounivalle.edu.co - nathalie.wilches@correounivalle.edu.co

**Fecha entrega:** 6 Septiembre 2025

---

## Introducción al Taller

En este taller se trabajará con **conjuntos difusos**, donde se modelan situaciones en las que no es posible clasificar un elemento como perteneciente o no a un conjunto. A diferencia de los conjuntos clásicos, en los conjuntos difusos cada elemento tiene un **grado de pertenencia** que varía entre 0 y 1.

El objetivo principal es implementar funciones de **alto orden** que operen sobre conjuntos difusos de números enteros, incluyendo:

- Creación de conjuntos difusos (`grande`)
- Operaciones con conjuntos (`complemento`, `union`, `interseccion`)
- Comparación de conjuntos (`inclusion`, `igualdad`)


---

## Función `grande` - Conjuntos Difusos

### Objetivo
La función `grande` crea un **conjunto difuso de números enteros grandes**. La idea es asignar un **grado de pertenencia** a cada número entero:

- Si un número `x` es pequeño, pertenece poco (valor cercano a 0).
- Si un número `x` es grande, pertenece mucho (valor cercano a 1).
- Para números intermedios, el grado de pertenencia crece proporcionalmente.

Esto se logra mediante: 

$$
\left(\frac{n}{n + d}\right)^e
$$

donde `d` es un número pequeño `e` es un número mayor que 1 que ajusta el crecimiento de esa función

### Implementación en Scala

```scala
  // función grande que devuelve una función Int => Double que toma un número entero x y devuelve un valor difuso entre 0 y 1
  def grande(d: Int, e: Int): Int => Double = {
    x => math.pow(x.toDouble / (x + d).toDouble, e.toDouble)
    // x.toDouble / (x + d).toDouble pasa de entero a decimal
    // e.toDouble) eleva al exponente e
  }
 ```

Comprendido. Siguiendo estrictamente las directrices del formato **Markdown** y la notación **LaTeX**, y basándome en la estructura yComprendido. Siguiendo estrictamente las directrices del formato **Markdown** y la notación **LaTeX**, y basándome en la estructura y el contenido detallado en nuestra conversación previa sobre el Taller 2 de Conjuntos Difusos, se presenta el informe detallado.

Este informe explica el objetivo del taller, el funcionamiento matemático de cada punto y su implementación correspondiente en Scala.

***

## Operaciones con conjuntos (`complemento`, `union`, `interseccion`) (1.2.2)

Estas funciones toman uno o dos conjuntos difusos como entrada y devuelven un nuevo conjunto difuso, implementando las operaciones estándar de la teoría de conjuntos difusos.

### 1. Función `complemento`

*   **Objetivo y Funcionamiento:** Implementar la operación de negación.
*   **Explicación Matemática:** El complemento $f_{\neg S}$ se define como:

$$
f_{\neg S} = 1 - f_S
$$

*   **Implementación en Scala:**

```scala
def complemento(c: Int => Double): Int => Double = {
  x => 1.0 - c(x)
}
```

### 2. Función `union`

*   **Objetivo y Funcionamiento:** Implementar la unión, utilizando el máximo grado de pertenencia (T-Conorma estándar).
*   **Explicación Matemática:** La unión $S_1 \cup S_2$ se define como:

$$
f_{S_1 \cup S_2} = \max(f_{S_1}, f_{S_2})
$$

*   **Implementación en Scala:**

```scala
def union(cd1: Int => Double, cd2: Int => Double): Int => Double = {
  x => math.max(cd1(x), cd2(x))
}
```

### 3. Función `interseccion`

*   **Objetivo y Funcionamiento:** Implementar la intersección, utilizando el mínimo grado de pertenencia (T-Norma estándar).
*   **Explicación Matemática:** La intersección $S_1 \cap S_2$ se define como:

$$
f_{S_1 \cap S_2} = \min(f_{S_1}, f_{S_2})
$$

*   **Implementación en Scala:**

```scala
def interseccion(cd1: Int => Double, cd2: Int => Double): Int => Double = {
  x => math.min(cd1(x), cd2(x))
}
```

---

## Comparación de conjuntos (`inclusion`, `igualdad`) (1.2.3)

Estas funciones verifican las relaciones entre dos conjuntos difusos.

### 1. Función `inclusion`

*   **Objetivo y Funcionamiento:** Verificar si un conjunto $S_1$ está incluido en $S_2$. Se requiere que esta función se implemente usando **recursión de cola** para iterar eficientemente sobre el universo de búsqueda (asumido en $$).
*   **Explicación Matemática:** La inclusión $S_1 \subseteq S_2$ es cierta si, para cada elemento $s$ en el universo $U$, el grado de pertenencia a $S_1$ es menor o igual que a $S_2$.

$$
S_1 \subseteq S_2 \iff \forall s \in U : f_{S_1}(s) \le f_{S_2}(s)
$$

*   **Implementación en Scala (Recursión de Cola):** Se utiliza una función auxiliar interna con la etiqueta `@scala.annotation.tailrec`.

```scala
def inclusion(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
  @scala.annotation.tailrec
  def check_inclusion(i: Int): Boolean = {
    if (i > 1000) { // Caso base de terminación del rango
      true
    } else if (cd1(i) <= cd2(i)) {
      check_inclusion(i + 1) // Llamada de cola optimizada
    } else {
      false
    }
  }
  check_inclusion(0)
}
```

*   **Lo que se espera probar:** Se debe probar que el proceso es eficiente (no acumula pila) y que solo devuelve `true` si no se encuentra ningún contraejemplo ($f_{S_1}(s) > f_{S_2}(s)$) en todo el rango $$.

### 2. Función `igualdad`

*   **Objetivo y Funcionamiento:** Determinar si dos conjuntos difusos son idénticos.
*   **Explicación Matemática:** La igualdad se define por la inclusión mutua, lo que reduce el problema a dos verificaciones de inclusión.

$$
S_1 = S_2 \iff S_1 \subseteq S_2 \land S_2 \subseteq S_1
$$

*   **Implementación en Scala:**

```scala
def igualdad(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
  // Llama a la función inclusion dos veces
  inclusion(cd1, cd2) && inclusion(cd2, cd1)
}
```

*   **Lo que se espera probar:** Se prueba la simetría y la transitividad de la igualdad, verificando que $P_{igualdad}$ solo sea `true` si la inclusión se cumple en ambas direcciones.
```