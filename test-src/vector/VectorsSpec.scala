package ch.unige.mts.vector

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.matchers.BeMatcher
import org.scalatest.matchers.MatchResult

import no.uib.cipr.matrix.{ Vector => JVector }
import no.uib.cipr.matrix.{ DenseVector => JDenseVector }

import ch.unige.mts.vector.Vector._
import ch.unige.mts.test._


class VectorsSpec extends FlatSpec with ShouldMatchers with VectorMatchers{

  def mtjVector():JVector = 
    new JDenseVector( Array( 0.0, 1.0, 2.0, 3.0) )

  
  "An mtj vector" can "be wrapped and unwrapped" in {
    val jvec = mtjVector
    val vec = new VectorWrapper( jvec )
    val jvec2 = vec.mtjVector
    jvec should be theSameInstanceAs(jvec2)
  }

  it can "be added to another mtj vector" in {
    val v1 = mtjVector
    val v2 = mtjVector
    val v3 = (v1 + v2).mtjVector
    v1 should be ( similarTo( 0.0, 1.0, 2.0, 3.0 ) )
    v2 should be ( similarTo( 0.0, 1.0, 2.0, 3.0 ) )
    v3 should be ( similarTo( 0.0, 2.0, 4.0, 6.0 ) )
  }
  
  it can "be added to several vectors" in {
    val v1 = mtjVector
    val v2 = mtjVector
    val v3:JVector = new JDenseVector( Array( 0.0, 1.0, 0.0, -1.0) )
    val v4 = (v1 + v2 + v3).mtjVector
    v1 should be ( similarTo( 0.0, 1.0, 2.0, 3.0 ) )
    v2 should be ( similarTo( 0.0, 1.0, 2.0, 3.0 ) )
    v3 should be ( similarTo( 0.0, 1.0, 0.0, -1.0 ) )
    v4 should be ( similarTo( 0.0, 3.0, 4.0, 5.0 ) )
  }

}


