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

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.matchers.BeMatcher
import org.scalatest.matchers.MatchResult

import no.uib.cipr.matrix.{ Vector => JVector }
import no.uib.cipr.matrix.{ DenseVector => JDenseVector }

import ch.unige.mts.vector.Vector._
import ch.unige.mts.MTS._
import ch.unige.mts.test.Matchers


class VectorsSpec extends FlatSpec with ShouldMatchers with Matchers{

  def mtjVector():JVector = 
    new JDenseVector( Array( 0.0, 1.0, 2.0, 3.0) )

  def mtjVector( values: Double*) = {
    new JDenseVector( values.toArray )
  }

  
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
    val v3 = (v1+v2)
    val v4 = new JDenseVector( Array( 0.0, 1.0, 0.0, -1.0) )
    val v5 = (v3 + v4)
    v1 should be ( similarTo( 0.0, 1.0, 2.0, 3.0 ) )
    v2 should be ( similarTo( 0.0, 1.0, 2.0, 3.0 ) )
    v3.mtjVector should be ( similarTo( 0.0, 2.0, 4.0, 6.0 ) )
    v4 should be ( similarTo( 0.0, 1.0, 0.0, -1.0 ) )
    v5.mtjVector should be ( similarTo( 0.0, 3.0, 4.0, 5.0 ) )
  }

  it can "let user to easily access positions" in {
    val v1 = mtjVector( 0, 1, 2 )
    val v2 = mtjVector( 1, 0, -1)
    val w =  (v1+v2)
    w(0) should be (1)
    w(1) should be (1)
    w(2) should be (1)
    v1(0) should be (0)
    v1(1) should be (1)
    v1(2) should be (2)
  }

  it can "let user to easily change value" in {
    val v1 = mtjVector( 0, 1, 2 )
    val v2 = mtjVector( 1, 0, -1)
    val w1 = (v1+v2)
    w1(0) = 12
    w1(0) should be (12)
    val w2 = w1 + v2
    w2(0) should be (13)
  }

  it should "support scalar/vector multiplication" in {
    val v1 = mtjVector( 1, 0, 0)
    val v2 = mtjVector( 0, 1, 0)
    val v3 = mtjVector( 0, 0, 1)
    val v4: JVector = v1*2 + v2*3 + v3*4
    v1 should be (similarTo(1,0,0))
    v2 should be (similarTo(0,1,0))
    v3 should be (similarTo(0,0,1))
    v4 should be (similarTo(2,3,4))
  }

  it should "allow a scalar to come before a vector when multiplying" in {
    val v1 = mtjVector( 1, 0, 0)
    val v2 = mtjVector( 0, 1, 0)
    val v3 = mtjVector( 0, 0, 1)
    val v4: JVector = 2*v1 + 3*v2 + 4*v3
    v1 should be (similarTo(1,0,0))
    v2 should be (similarTo(0,1,0))
    v3 should be (similarTo(0,0,1))
    v4 should be (similarTo(2,3,4))
  }

  it should "respect associativity" in {
    val v1 = mtjVector( 1, 0, 0)
    val v2 = mtjVector( 0, 1, 0)
    val v3 = mtjVector( 0, 0, 1)
    val v4: JVector = 2*v1 + ( 3*v2  + 4*v3 )
    v1 should be (similarTo(1,0,0))
    v2 should be (similarTo(0,1,0))
    v3 should be (similarTo(0,0,1))
    v4 should be (similarTo(2,3,4))
  }

  it should "respect distribution of scalar mult over addition" in {
    val v1 = mtjVector( 1, 0, 0)
    val v2 = mtjVector( 0, 1, 0)
    val v3: JVector = 2*v1 + 2*v2
    val v4: JVector = 2*(v1+v2)
    v1 should be (similarTo(1,0,0))
    v2 should be (similarTo(0,1,0))
    v3 should be (similarTo(2,2,0))
    v4 should be (similarTo(2,2,0))
  }

  it can "be multiplied by another vector" in {
    val v1 = mtjVector( 1, 0, 1 )
    val v2 = mtjVector( 1, 2, 3 )
    val dot = v1*v2
    dot should be (4.0)
    val dot2 = 2*v1*v2
    dot2 should be (8.0)
    val dot3 = v1*2*v2
    dot3 should be (8.0)
  }

  it can "be easily created from a seq" in {
    val v = Vector( Seq( 1.0, 2.0, 4.0) )
    v should be ( similarTo(1.0, 2.0, 4.0) )
  }

  it can "be easily created from an array" in {
    val v = Vector( Array( 1.0, 2.0, 4.0) )
    v should be ( similarTo(1.0, 2.0, 4.0) )
  }

  it can "be easily created from a List" in {
    val v = Vector( List( 1.0, 2.0, 4.0) )
    v should be ( similarTo(1.0, 2.0, 4.0) )
  }

  it can "be easily created from a map" in {
    val v = Vector( Map( 1 -> 2.0, 3 -> -2.0 ))
    v should be ( similarTo(0.0, 2.0, 0.0, -2.0) )
  }


}


