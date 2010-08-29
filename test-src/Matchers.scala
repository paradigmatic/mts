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


package ch.unige.mts.test

import org.scalatest.matchers.BeMatcher
import org.scalatest.matchers.MatchResult

import no.uib.cipr.matrix.{ Vector => JVector }
import no.uib.cipr.matrix.{ Matrix => JMatrix }
//import no.uib.cipr.matrix.{ DenseVector => JDenseVector }

trait Matchers {
  
  class JVecMatcher( values: Seq[Double] ) extends BeMatcher[JVector]{
    override def apply( left:JVector ) = {
      if( values.size != left.size ) {
        MatchResult( false, 
                    "Size " + left.size + " is not equal to size " + values.size, 
"Size " + left.size + " is equal to size " + values.size, 
"size " + left.size + " is not equal to size " + values.size, 
"size " + left.size + " is equal to size " + values.size)

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

 class JMatMatcher( values: Seq[Seq[Double]] ) extends BeMatcher[JMatrix]{
    override def apply( left:JMatrix ) = {
      if( values.size != left.numRows ) {
        MatchResult( false, 
                    "Number of rows " + left.numRows + 
                    " is not equal to " + values.size, 
                    "Number of rows " + left.numRows + 
                    " is equal to " + values.size, 
                   "number of rows " + left.numRows + 
                    " is not equal to " + values.size, 
                    "number of rows " + left.numRows + 
                    " is equal to " + values.size )

      } else {
        var eqValues = true
        for( i <- 0 until values.size; j <- 0 until values(i).size ) {
          eqValues &= left.get(i,j) == values(i)(j)
        }
        MatchResult( eqValues,
                    left.toString + " is not similar to " + values ,
                    left.toString + " is similar to " + values )
      }
    }
  }

  def similarTo( values:Double* ) = new JVecMatcher(values)
  def similarTo( values: Seq[Double]* ) = new JMatMatcher(values)


}
