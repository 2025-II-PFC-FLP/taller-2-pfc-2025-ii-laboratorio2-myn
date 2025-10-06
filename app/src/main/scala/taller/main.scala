package taller

object Main extends App {


  val cd = new ConjuntosDifusos()


  // Prueba 1: función grande
  val grande = cd.grande(1, 2)
  val numeros = List(1, 2, 3, 5, 10)

  println("1. Ejemplos función grande")
  numeros.foreach { x =>
    val valor = grande(x)
    println(s"x = $x → grande(x) = $valor")
  }

  // Prueba 2: función complemento
  // El complemento devuelve 1 - valorOriginal para cada x
  val complementoGrande = cd.complemento(grande)
  val numerosComplemento = List(2, 4, 6, 8) // Números para probar el complemento

  println("2. Ejemplos función complemento")
  numerosComplemento.foreach { x =>
    val valor = complementoGrande(x)
    println(s"x = $x → complemento(grande)(x) = $valor")
  }

  // Prueba 3: función union
  // La unión devuelve el máximo entre dos conjuntos difusos para cada x
  val grande1 = cd.grande(1, 2) // primer conjunto
  val grande2 = cd.grande(2, 3) // segundo conjunto
  val unionConjuntos = cd.union(grande1, grande2)
  val numerosUnion = List(1, 2, 3, 5, 10)

  println("3. Ejemplos función union")
  numerosUnion.foreach { x =>
    val valorUnion = unionConjuntos(x)
    println(s"x = $x → union(grande1, grande2) = $valorUnion")
  }

  // Prueba 4: función intersección
  val interseccionConjuntos = cd.interseccion(grande1, grande2)
  val numerosInter = List(1, 2, 3, 4, 5, 10)

  println("4. Ejemplos función interseccion")
  numerosInter.foreach { x =>
    val valorInter = interseccionConjuntos(x)
    println(s"x = $x → interseccion(grande1, grande2) = $valorInter")
  }
}
