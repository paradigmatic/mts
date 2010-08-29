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

package ch.unige.mts

import ch.unige.mts.vector._
import ch.unige.mts.matrix._
import no.uib.cipr.matrix.{ Vector => JVector }
import no.uib.cipr.matrix.{ DenseVector => JDenseVector }
import no.uib.cipr.matrix.sparse.{ SparseVector => JSparseVector }
import no.uib.cipr.matrix.{ Matrix => JMatrix }

object MTS {

  implicit def mtsv2mtjv( v: Vector ):JVector = v.mtjVector
  implicit def mtjv2mtsv( v: JVector ):VectorWrapper = new VectorWrapper( v )

  implicit def mtsm2mtjm( m: Matrix ):JMatrix = m.mtjMatrix
  implicit def mtjm2mtsm( m: JMatrix ):MatrixWrapper = new MatrixWrapper( m )


  implicit def double2scalar( d: Double): Scalar = new Scalar(d)
  implicit def int2scalar( i: Int): Scalar = new Scalar(i)

  class Scalar( d: Double ) {
    def *(v: Vector ) = v*d
    def *(v: JVector ) = v*d
  }

}
