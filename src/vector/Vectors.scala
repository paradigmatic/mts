package ch.unige.mts.vector

import no.uib.cipr.matrix.{ Vector => JVector }


trait Vector {

  def +[W <% Vector]( v: W ): Vector
  def mtjVector(): JVector
  def apply( index: Int ) = mtjVector.get( index )

}

class VectorWrapper( val mtjVector: JVector ) extends Vector {

  def +[W <% Vector]( v: W ) = 
    new VectorAdder( List( this, v ) )

}

class VectorAdder( val vectors: List[Vector] )  extends Vector {

  lazy val mtjVector = {
    val v = vectors.head.mtjVector.copy
    for( w <- vectors.tail ) { v add w.mtjVector }
    v
  }

  def +[W <% Vector]( v: W ) = 
    new VectorAdder( v :: vectors )

} 

object Vector {

  implicit def mts2mtj( v: Vector ) = v.mtjVector
  implicit def mtj2mts( v: JVector ) = new VectorWrapper( v )

}
