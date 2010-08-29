New features
============

Vector
------

  * Vectors out of ranges:
    val v = Vector( 0 until 10 )
  * Vector map:
    val v = Vector( Array(0,1,2) ) map ( _ - 1 ) //=> [-1,0,1]
  * Vector exists and forall
    val v = Vector( Array(0,1,2) ) forall ( _ >= 0 ) // false
    val v = Vector( Array(0,1,2) ) exists ( _ >= 0 ) // true
  * Vector foldleft and foldright:
    val v = Vector( Array(0,1,2) ) foldleft(0) ( _ + _ ) // 3
  * Operation: vector subtraction


Matrix
------

  * Operations: *, +, t