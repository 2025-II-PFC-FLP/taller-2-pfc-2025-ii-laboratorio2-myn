package taller

class ConjuntosDifusos {
  // ---------------------------------------------
  // Definición del tipo
  // ---------------------------------------------
  type ConjDifuso = Int => Double // Un Conjunto difuso es una función de tipo Int a Double [1, 6]

  // Función de pertenencia ya definida en el enunciado [1, 6]
  def pertenece(elem: Int, s: ConjDifuso): Double = {
    s(elem)
  }

  // ----------------------------------------------------
  // 1.2.1. Conjunto difuso de números grandes
  // ----------------------------------------------------

  /**
   * Crea un Conjunto Difuso de números grandes.
   * Grado de pertenencia: (n / (n + d))^e
   * @param d Entero pequeño mayor o igual a 1.
   * @param e Entero mayor que 1 (exponente).
   * @return ConjDifuso (Int => Double).
   */
  def grande(d: Int, e: Int): ConjDifuso = {
    // Retorna una función anónima (ConjDifuso) que implementa la fórmula
    (n: Int) => {
      // Aseguramos que la división y la base sean de tipo Double para la precisión
      val base = n.toDouble / (n.toDouble + d.toDouble)
      math.pow(base, e.toDouble)
    }
  }

  // ----------------------------------------------------
  // 1.2.2. Complemento, Unión e Intersección
  // ----------------------------------------------------

  /**
   * Calcula el complemento de un conjunto difuso.
   * Fórmula: f¬S(s) = 1 - fS(s) [7].
   * @param c El conjunto difuso original.
   * @return El Conjunto Difuso complemento.
   */
  def complemento(c: ConjDifuso): ConjDifuso = {
    // Retorna una función de alto orden (HOF) [5] que encapsula la lógica (closure) [8]
    (x: Int) => 1.0 - c(x)
  }

  /**
   * Calcula la unión de dos conjuntos difusos.
   * Fórmula: fS1∪S2 = máx(fS1, fS2) [9].
   * @param cd1 Primer conjunto difuso.
   * @param cd2 Segundo conjunto difuso.
   * @return El Conjunto Difuso unión.
   */
  def union(cd1: ConjDifuso, cd2: ConjDifuso): ConjDifuso = {
    (x: Int) => math.max(cd1(x), cd2(x))
  }

  /**
   * Calcula la intersección de dos conjuntos difusos.
   * Fórmula: fS1∩S2 = mín(fS1, fS2) [9].
   * @param cd1 Primer conjunto difuso.
   * @param cd2 Segundo conjunto difuso.
   * @return El Conjunto Difuso intersección.
   */
  def interseccion(cd1: ConjDifuso, cd2: ConjDifuso): ConjDifuso = {
    (x: Int) => math.min(cd1(x), cd2(x))
  }

  // ----------------------------------------------------
  // 1.2.3. Inclusión e igualdad
  // ----------------------------------------------------

  /**
   * Verifica la inclusión de S1 en S2 (S1 ⊆ S2).
   * S1 ⊆ S2 si ∀s ∈ U : fS1(s) ≤ fS2(s) [10].
   * Se implementa usando recursión de cola y asumiendo el rango de búsqueda  [3].
   * @param cd1 El supuesto subconjunto difuso (S1).
   * @param cd2 El conjunto difuso (S2).
   * @return true si cd1 está incluido en cd2.
   */
  def inclusion(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {

    // Uso de @tailrec para garantizar la recursión de cola requerida [3]
    @scala.annotation.tailrec
    def check_inclusion(i: Int): Boolean = {
      if (i > 1000) {
        // Caso base: El límite  ha sido revisado completamente [3]
        true
      } else if (cd1(i) <= cd2(i)) {
        // Si la condición se cumple para 'i', revisa el siguiente elemento
        check_inclusion(i + 1)
      } else {
        // Si la condición falla (cd1(i) > cd2(i)), la inclusión es falsa
        false
      }
    }

    // Se inicia la verificación desde el elemento 0.
    check_inclusion(0)
  }

  /**
   * Verifica la igualdad de dos conjuntos difusos.
   * S1 = S2 si S1 ⊆ S2 ∧ S2 ⊆ S1 [10].
   * @param cd1 Primer conjunto difuso.
   * @param cd2 Segundo conjunto difuso.
   * @return true si ambos conjuntos son iguales.
   */
  def igualdad(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
    // La igualdad se define como la inclusión mutua [10].
    inclusion(cd1, cd2) && inclusion(cd2, cd1)
  }


}

