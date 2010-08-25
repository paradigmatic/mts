package ch.unige.mts.vector

import no.uib.cipr.matrix.{ Vector => JVector }


trait Vector {

  def +[W <% Vector]( v: W ): Vector
  def mtjVector(): JVector

}

object Vector {

  implicit def mts2mtj( v: Vector ) = v.mtjVector
  implicit def mtj2mts( v: JVector ) = new VectorWrapper( v )

}

class VectorWrapper( val mtjVector: JVector ) extends Vector {

  def +[W <% Vector]( v: W ) = new VectorWrapper( mtjVector add v.mtjVector )

}
