package ch.unige.mts.vector

import no.uib.cipr.matrix.{ Vector => JVector }
import no.uib.cipr.matrix.{ DenseVector => JDenseVector }

import Vector._

trait Vector {
  def +[W <% Vector]( v: W ): Vector
  def *( alpha: Double ): Vector
  def *[W <% JVector]( v: W ) = mtjVector dot v 
  def mtjVector(): JVector
  def apply( index: Int ) = mtjVector.get( index )
}

trait ModifiableVector extends Vector {
  def update( index: Int, value: Double ): Unit
}

class VectorWrapper( val mtjVector: JVector ) extends ModifiableVector {
  def *( alpha: Double ) = new VectorProxy( new ScaledVector( alpha, this ) )
  def +[W <% Vector]( v: W ) = 
    new VectorProxy( new VectorAdder( List( this, v ) ) )
  def update( index: Int, value: Double ) { mtjVector.set( index, value ) }

}

class VectorProxy( private var delegate: Vector ) extends ModifiableVector {
  def *( alpha: Double ) = {
    delegate = delegate * alpha
    delegate
  }
  def +[W <% Vector]( v: W ) = delegate + v
  def mtjVector() = delegate.mtjVector
  override def apply( index: Int ) = delegate(index)
  override def update( index: Int, value: Double ) {
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

class VectorAdder( vec: List[Vector] )  extends Vector {

  val vectors:List[Vector] = {
    val lstVec = vec map ( (v:Vector) => v match   {
      case adder: VectorAdder => adder.vectors
      case _ => List( v )
    })
    lstVec.flatten
  }

  def mtjVector = {
    val v = vectors.head.mtjVector.copy
    for( w <- vectors.tail ) { 
      w match {
        case scaled: ScaledVector => v.add( scaled.constant, scaled.v)
        case _ => v add w.mtjVector
      }
    }
    v
  }

  def +[W <% Vector]( v: W ) =  new VectorAdder( v :: vectors )

  def *( alpha: Double ) = new VectorProxy( new ScaledVector( alpha, mtjVector ) ) 

} 

class ScaledVector( private var beta: Double, val v: Vector ) extends Vector {
  
  def mtjVector() = v.mtjVector.copy scale beta

  //TODO: Remove copy
  def +[W <% Vector]( v: W ) = new VectorAdder( List( this, v ) )
    
  def *( alpha: Double ) = {
    beta *= alpha
    this
  }

  def constant = beta

}

object Vector {

  implicit def mts2mtj( v: Vector ):JVector = v.mtjVector
  implicit def mtj2mts( v: JVector ):VectorWrapper = new VectorWrapper( v )
  implicit def double2scalar( d: Double): Scalar = new Scalar(d)
  implicit def int2scalar( i: Int): Scalar = new Scalar(i)

  class Scalar( d: Double ) {
    def *(v: Vector ) = v*d
    def *(v: JVector ) = v*d
  }

   def apply( seq: Seq[Double] ):JDenseVector = {
    val length = seq.size
    println(seq)
    println(length)
    val vec = new JDenseVector( length )
    println(vec.size)
    for( i <- 0 until length ) {
      vec.set( i, seq(i) )
    }
    vec
  }

}
