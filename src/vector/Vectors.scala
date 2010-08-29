/*
* This file is part of Matrix Toolkits Scala (MTS).
*
* MTS is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* MTS is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with MTS.  If not, see <http://www.gnu.org/licenses/>.
*
* Copyright 2010 Jean-Luc Falcone (University of Geneva)
*
*/

package ch.unige.mts.vector

import no.uib.cipr.matrix.{ Vector => JVector }
import no.uib.cipr.matrix.{ DenseVector => JDenseVector }
import no.uib.cipr.matrix.sparse.{ SparseVector => JSparseVector }

import ch.unige.mts.MTS._

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

   def apply( seq: Seq[Double] ):JDenseVector = {
    val length = seq.size
    val vec = new JDenseVector( length )
    for( i <- 0 until length ) {
      vec.set( i, seq(i) )
    }
    vec
  }

  //TODO: check what is the return type of a collective operation
   def apply( map: Map[Int,Double] ):JSparseVector = {
    val length = map.keys.foldLeft(0){ scala.math.max(_,_) } + 1
    val vec = new JSparseVector( length )
    for( i <- map.keys ) {
      vec.set( i, map(i) )
    }
    vec
  }


}
