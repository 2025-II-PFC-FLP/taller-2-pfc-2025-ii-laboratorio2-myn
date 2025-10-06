
package taller // Clase de pruebas para la clase ConjuntosDifusos

//librerias para pruebas
import org.scalatest.funsuite.AnyFunSuite //permite escribir pruebas simples como funciones
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner //permite ejecutar las pruebas de scalaTest usando JUnit

//lo q permite usar el framework
@RunWith(classOf[JUnitRunner])
class ConjuntosDifusosTest extends AnyFunSuite {

  // Prueba 1: Verificar que la función grande devuelve valores entre 0 y 1
  test("valores entre 0 y 1") {
    // Creamos una instancia de la clase ConjuntosDifusos
    val cd = new ConjuntosDifusos()

    // Definimos un conjunto difuso de números grandes con parámetros, d = 1 → escala del conjunto y e = 2 → pendiente de la función
    val grande = cd.grande(1, 2)

    //lista de numeros para probar
    val numeros = List(1, 2, 3, 5, 10)

    println("Pruebas1 función grande")

    //recorrer cada numero y calcular su grado de pertenencia
    numeros.foreach { x =>
      val valor = grande(x) // valor de grandeza para el número x, entre 0 y 1
      println(s"x = $x → grande(x) = $valor")

      // verificacion el valor debe estar entre 0 y 1 - assert debe fallar la prueba si la condición es falsa
      assert(valor >= 0.0 && valor <= 1.0)
    }

    println("Fin de pruebas1")
  }

  //prueba 2: Verificar que la función "complemento" devuelve 1 - grande(x)
  test("complemento de grande es 1 - grande") {
    val cd = new ConjuntosDifusos()
    val grande = cd.grande(1, 2) //esta así para que no la prueba no dependa del anterior

    // Creamos el conjunto difuso complementario
    val complemento = cd.complemento(grande)

    //lista de numero para probar
    val numerosComplemento = List(2, 4, 6, 8)

    numerosComplemento.foreach { x =>
      val valorOriginal = grande(x) // valor original del conjunto
      val valorComplemento = complemento(x) // valor del complemento
      assert(valorComplemento == 1.0 - valorOriginal) // verifica que sea 1 - valorOriginal
    }
  }


  // Prueba 3: Verificar que la función"union devuelve el máximo de dos conjuntos
  test("union devuelve el máximo de dos conjuntos") {
    val cd = new ConjuntosDifusos()
    val grande1 = cd.grande(1, 2) // primer conjunto difuso
    val grande2 = cd.grande(2, 3) // segundo conjunto difuso - esta así para que no la prueba no dependa de grande

    //la unión de ambos conjuntos
    val unionConjuntos = cd.union(grande1, grande2)

    val numeros = List(1, 2, 3, 4, 5)

    numeros.foreach { x =>
      val valorUnion = unionConjuntos(x) // valor de la unión
      val valorEsperado = math.max(grande1(x), grande2(x)) // debería ser el máximo
      assert(valorUnion == valorEsperado) // verifica que el valor de la unión sea el máximo
    }
  }

  // Prueba 4
  test("función interseccion") {

    // instancia de la clase ConjuntosDifusos
    val cd = new ConjuntosDifusos()

    // definimos dos conjuntos difusos distintos usando la función grande
    // d y e controlan la forma de la función difusa
    val grande1 = cd.grande(1, 2) // primer conjunto difuso
    val grande2 = cd.grande(2, 3) // segundo conjunto difuso

    //calculamos la intersección de los dos conjuntos difusos
    //la intersección devuelve un conjunto donde cada x toma el mínimo de los dos conjuntos
    val interC = cd.interseccion(grande1, grande2)

    //lista de números a probar
    val numeros = List(1, 2, 3, 5, 10)

    // Recorremos y verificamos el valor de la intersección
    numeros.foreach { x =>
      val valorInter = interC(x) // valor de la intersección para x
      assert(valorInter >= 0.0 && valorInter <= 1.0) //cada valor debe estar entre 0 y 1
    }
  }
}