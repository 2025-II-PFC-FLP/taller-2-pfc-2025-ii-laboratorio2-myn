package taller

import java.text.DecimalFormat

object ConjuntosDifusosTest {

  // Instanciamos la clase principal para acceder a sus métodos
  val cd = new ConjuntosDifusos()

  // Importamos el tipo ConjDifuso para mayor claridad
  type ConjDifuso = cd.ConjDifuso

  // Tolerancia para la comparación de Doubles debido a la precisión
  val EPSILON = 0.0001

  // FUnción auxiliar para obtener el grado de pertenencia

  def assertDouble(actual: Double, expected: Double, message: String): Unit = {
    if (math.abs(actual - expected) > EPSILON) {
      println(s"[FALLÓ] $message: Esperado $expected, Obtenido $actual")
    } else {
      println(s"[OK] $message: Esperado $expected, Obtenido $actual")
    }
  }

  def assertBoolean(actual: Boolean, expected: Boolean, message: String): Unit = {
    if (actual != expected) {
      println(s"[FALLÓ] $message: Esperado $expected, Obtenido $actual")
    } else {
      println(s"[OK] $message")
    }
  }

  // Función auxiliar para obtener el grado de pertenencia
  def pertenece(elem: Int, s: ConjDifuso): Double = cd.pertenece(elem, s)

  // ----------------------------------------------------
  // Casos de Prueba 1.2.1: Conjunto Grande
  // ----------------------------------------------------

  def testGrande(): Unit = {
    println("\n--- PRUEBAS 1.2.1: Conjunto Difuso de Números Grandes ---")

    // C_g1: Conjunto con d=2 (desplazamiento) y e=3 (exponente).
    // Fórmula: (n / (n + 2))^3
    val C_g1 = cd.grande(d = 2, e = 3)

    // C_g2: Conjunto con d=10 y e=2.
    // Fórmula: (n / (n + 10))^2
    val C_g2 = cd.grande(d = 10, e = 2)

    // P1: n=1. Valor bajo en C_g1.
    // Cálculo esperado: (1 / (1 + 2))^3
    assertDouble(
      pertenece(1, C_g1),
      math.pow(1.0/3.0, 3),
      "P1 (d=2, e=3, n=1): Grado de pertenencia bajo"
    )

    // P2: n=8. Valor intermedio en C_g1.
    // Cálculo esperado: (8 / (8 + 2))^3
    assertDouble(
      pertenece(8, C_g1),
      math.pow(8.0/10.0, 3),
      "P2 (d=2, e=3, n=8):Grado de pertenencia medio"
    )

    // P3: n=98. Valor alto en C_g1.
    // Cálculo esperado: (98 / (98 + 2))^3
    assertDouble(
      pertenece(98, C_g1),
      math.pow(98.0/100.0, 3),
      "P3 (d=2, e=3, n=98): Grado de pertenencia alto"
    )

    // P4: n=5 en C_g2. Prueba n pequeño frente a d=10.
    // Cálculo esperado: (5 / (5 + 10))^2
    assertDouble(
      pertenece(5, C_g2),
      math.pow(5.0/15.0, 2),
      "P4 (d=10, e=2, n=5): Grado de pertenencia bajo"
    )

    // P5: n=90 en C_g2. Prueba n grande frente a d=10.
    // Cálculo esperado: (90 / (90 + 10))^2
    assertDouble(
      pertenece(90, C_g2),
      math.pow(90.0/100.0, 2),
      "P5 (d=10, e=2, n=90): Grado de pertenencia alto"
    )

    // P6: Chequeo de caso límite d=1, e=2, n=1.
    val C_limite = cd.grande(1, 2)
    // Cálculo esperado: (1 / (1 + 1))^2 = 0.25
    assertDouble(
      pertenece(1, C_limite),
      0.25,
      "P6 (d=1, e=2, n=1): Caso límite"
    )
  }

  // ----------------------------------------------------
  // Casos de Prueba 1.2.2: Complemento, Unión e Intersección
  // ----------------------------------------------------

  def testOperaciones(): Unit = {
    println("\n--- PRUEBAS 1.2.2: Complemento, Unión e Intersección ---")

    // C_A: Conjunto base grande
    val C_A = cd.grande(d = 5, e = 2)
    // C_B: Otro conjunto base
    val C_B = cd.grande(d = 2, e = 5)

    // Grados de pertenencia para un elemento (e = 5):
    // G_A(5) = (5/10)^2 = 0.25
    // G_B(5) = (5/7)^5 ≈ 0.1873
    val e = 5
    val G_A_e = pertenece(e, C_A) // 0.25
    val G_B_e = pertenece(e, C_B) // 0.1873

    // P1: Complemento (1 - G_A)
    val C_CompA = cd.complemento(C_A)
    assertDouble(pertenece(e, C_CompA), 1.0 - G_A_e, "P1: Complemento de A en e=5") // 0.75

    // P2: Unión (Max)
    val C_Union = cd.union(C_A, C_B)
    assertDouble(pertenece(e, C_Union), 0.25, "P2: Unión de A y B en e=5 (Max)")

    // P3: Intersección (Min)
    val C_Inter = cd.interseccion(C_A, C_B)
    assertDouble(pertenece(e, C_Inter), G_B_e, "P3: Intersección de A y B en e=5 (Min)")

    // P4: Unión con el Complemento (Debe dar 1.0 si es Ley del Tercio Excluido, pero aquí es difuso)
    val C_UnionComp = cd.union(C_A, C_CompA)
    assertDouble(pertenece(e, C_UnionComp), 0.75, "P4: Unión de A con su complemento") // max(0.25, 0.75) = 0.75

    // P5: Intersección con el Complemento (Debe dar 0.0 si es Ley de Contradicción, pero aquí es difuso)
    val C_InterComp = cd.interseccion(C_A, C_CompA)
    assertDouble(pertenece(e, C_InterComp), 0.25, "P5: Intersección de A con su complemento") // min(0.25, 0.75) = 0.25

    // P6: Operación compuesta: Interseccion(Comp(A), Comp(B))
    val C_CompB = cd.complemento(C_B)
    val C_InterCompAB = cd.interseccion(C_CompA, C_CompB)
    // Expected: min(1 - 0.25, 1 - 0.1873) = min(0.75, 0.8127) = 0.75
    assertDouble(pertenece(e, C_InterCompAB), 0.75, "P6: Interseccion de complementos")
  }

  // ----------------------------------------------------
  // Casos de Prueba 1.2.3: Inclusión e Igualdad (Recursión de Cola)
  // ----------------------------------------------------

  def testRelaciones(): Unit = {
    println("\n--- PRUEBAS 1.2.3: Inclusión e Igualdad ---")

    // C_X: d=1, e=2 (f(n) = n^2 / (n+1)^2)
    val C_X = cd.grande(d = 1, e = 2)
    // C_Y: d=1, e=3 (f(n) = n^3 / (n+1)^3). Para todo n, f_Y(n) < f_X(n)
    val C_Y = cd.grande(d = 1, e = 3)
    // C_Z: Conjunto igual a C_X
    val C_Z = cd.grande(d = 1, e = 2)

    // P1: Inclusión - Caso Verdadero (C_Y ⊆ C_X)
    // Ya que el exponente 3 es mayor, el grado de pertenencia es menor para C_Y
    assertBoolean(cd.inclusion(C_Y, C_X), true, "P1: C_Y está incluido en C_X")

    // P2: Inclusión - Caso Falso (C_X ⊄ C_Y)
    assertBoolean(cd.inclusion(C_X, C_Y), false, "P2: C_X NO está incluido en C_Y")

    // P3: Igualdad - Caso Verdadero (C_X = C_Z)
    assertBoolean(cd.igualdad(C_X, C_Z), true, "P3: C_X es igual a C_Z")

    // P4: Igualdad - Caso Falso (C_X != C_Y)
    assertBoolean(cd.igualdad(C_X, C_Y), false, "P4: C_X NO es igual a C_Y")

    // P5: Inclusión de sí mismo
    assertBoolean(cd.inclusion(C_X, C_X), true, "P5: C_X incluido en sí mismo")

    // P6: Igualdad de un conjunto con su complemento (Debe ser falso, ya que solo pueden ser iguales si son el conjunto vacío o el universo, y aquí no lo son completamente)
    val C_CompX = cd.complemento(C_X)
    assertBoolean(cd.igualdad(C_X, C_CompX), false, "P6: C_X no es igual a su complemento")
  }


  def main(args: Array[String]): Unit = {
    println("--- Ejecutando Casos de Prueba del Taller de Conjuntos Difusos ---")
    testGrande()
    testOperaciones()
    testRelaciones()
    println("--- Fin de la ejecución de Pruebas ---")
  }
}
//prueba