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

package ch.unige.mts.matrix

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import ch.unige.mts.test.Matchers

import no.uib.cipr.matrix.{Matrix => JMatrix}
import no.uib.cipr.matrix.{DenseMatrix => JDenseMatrix}


import ch.unige.mts.MTS._

class MatricesSpec extends FlatSpec with ShouldMatchers with Matchers{
  
  def mtjMatrix( rows: Array[Double]* ) = new JDenseMatrix( rows.toArray )
   
  "An mtj matrix" can "be transposed" in {
    val M = mtjMatrix( Array(1.0,2,3), Array(4.0,5,6) )
    val N:JMatrix = M.t
    M should be (similarTo( Array(1.0,2,3), Array(4.0,5,6) ) )
    N should be (similarTo( Array(1.0,4), Array(2.0,5), Array(3.0,6) ) )

  }

}
