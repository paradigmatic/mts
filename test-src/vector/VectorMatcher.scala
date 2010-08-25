package ch.unige.mts.test


import org.scalatest.matchers.BeMatcher
import org.scalatest.matchers.MatchResult

import no.uib.cipr.matrix.{ Vector => JVector }
import no.uib.cipr.matrix.{ DenseVector => JDenseVector }



trait VectorMatchers {

  import ch.unige.mts.vector.Vector._
  
  class JVecMatcher( values: Seq[Double] ) extends BeMatcher[JVector]{
    override def apply( left:JVector ) = {
      if( values.size != left.size ) {
        MatchResult( false, 
                    "The sizes are different", 
                    "The sizes are equal",
                    "the sizes are different",                              
                    "The sizes are equal")
      } else {
        var eqValues = true
        for( i <- 0 until values.size ) {
          eqValues &= left.get(i) == values(i)
        }
        MatchResult( eqValues,
                    left.toString + " is not similar to " + values ,
                    left.toString + " is similar to " + values )
      }
    }
  }

  def similarTo( values:Double* ) = new JVecMatcher(values)

}
