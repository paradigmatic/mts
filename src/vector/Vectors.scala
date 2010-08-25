package ch.unige.mts.vector

import no.uib.cipr.matrix.{ Vector => JVector }


trait Vector {

  def +[W <% Vector]( v: W ): Vector
  def mtjVector(): JVector
  def apply( index: Int ) = mtjVector.get( index )

}

trait ModifiableVector extends Vector {
  def update( index: Int, value: Double ) = mtjVector.set( index, value )
}

class VectorWrapper( val mtjVector: JVector ) extends ModifiableVector {

  def +[W <% Vector]( v: W ) = 
    new VectorProxy( new VectorAdder( List( this, v ) ) )

}

class VectorProxy( private var delegate: Vector ) extends ModifiableVector {

  def +[W <% Vector]( v: W ) = delegate + v
  def mtjVector() = delegate.mtjVector
  override def apply( index: Int ) = delegate(index)
  override def update( index: Int, value: Double ) = {
    delegate match {
      case m: ModifiableVector => m(index) = value
      case _ => {
        val wrap = new VectorWrapper( delegate.mtjVector )
        wrap(index) = value
        delegate = wrap
        delegate
      }
    }
  }

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
