# Taller 2: Funciones de alto orden
## Fundamentos de Programación funcional y concurrente

**Estudiante(s):** Mariana de los Ángeles Viera Serna - Náthalie Wilches

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

