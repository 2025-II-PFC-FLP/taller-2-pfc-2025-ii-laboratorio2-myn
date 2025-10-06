package taller  // Clase que representa conjuntos difusos cuyos valores están entre 0 y 1

class ConjuntosDifusos {

  // función grande que devuelve una función Int => Double que toma un número entero x y devuelve un valor difuso entre 0 y 1
  def grande(d: Int, e: Int): Int => Double = {
    x => math.pow(x.toDouble / (x + d).toDouble, e.toDouble)
    // x.toDouble / (x + d).toDouble pasa de entero a decimal
    // e.toDouble) eleva al exponente e
  }

  // función complemento - conjunto difuso complementario para cada x, devuelve 1 - el valor original segun complemento(c)(x) = 1 - c(x)
  def complemento(c: Int => Double): Int => Double = {
    x => 1.0 - c(x)
  }

  // función union - genera un nuevo conjunto que es la unión de dos conjuntos
  //   cd1: primer conjunto cd2: segundo conjunto
  def union(cd1: Int => Double, cd2: Int => Double): Int => Double = {
    x => math.max(cd1(x), cd2(x))
    // cd1(x) - valor del primer conjunto - cd2(x) → valor del segundo conjunto - math.max() valor mayor entre los dos
  }

  // funcion interseccion - representa la intersección de dos conjuntos toma el valor mínimo entre los dos conjuntos
  def interseccion(cd1: Int => Double, cd2: Int => Double): Int => Double = {
    x => math.min(cd1(x), cd2(x))
    // math.min() → toma el valor menor de ambos
  }

}