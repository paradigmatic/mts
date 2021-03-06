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

import no.uib.cipr.matrix.{ Matrix => JMatrix }
import no.uib.cipr.matrix.DenseMatrix

import ch.unige.mts.MTS._

trait Matrix {

  def mtjMatrix(): JMatrix
  def t(): Matrix
  def *(alpha: Double): Matrix

}

class MatrixWrapper( private val mtjMat: JMatrix, 
                    private var toTranspose: Boolean,
                    private var toScale: Boolean,
                    private var factor: Double )
extends Matrix {

  def this( mtjMat: JMatrix ) = this( mtjMat, false, false, 1 )

  def mtjMatrix() = {
    if( toTranspose ) {
      val newMat = mtjMat.transpose( transposedShape )
      if( toScale ) {
        newMat.scale(factor)
      }
      newMat
    } else if( toScale ) {
      mtjMat.copy.scale( factor)
    } else mtjMat
  }

  def t() = {
    toTranspose = ! toTranspose
    this
  }

  def *( alpha: Double ) = {
    toScale = true
    factor *= alpha
    this
  }

  //TODO: auto-choose best matrix kind
  lazy val transposedShape = if( mtjMat.isSquare ) {
    mtjMat.copy
  } else {
    new DenseMatrix( mtjMat.numColumns, mtjMat.numRows )
  }

}
