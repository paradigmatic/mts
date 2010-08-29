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

import no.uib.cipr.matrix.{DenseMatrix => JDenseMatrix}


class MatricesTest extends FlatSpec with ShouldMatchers {
  
  def mtjMatrix( rows: Array[Double]* ) = new JDenseMatrix( rows.toArray )
   
  "An mtj vector" can "be wrapped and unwrapped" in {
    val jMat = mtjMatrix( Array(0.0, 1.0), Array(-1.0, 0.0) )
    val mat = new MatrixWrapper( jMat )
    val jMat2 = mat.mtjMatrix
    jMat should be theSameInstanceAs(jMat2)
  }

}
