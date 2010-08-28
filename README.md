Matrix Toolkits Scala (MTS)
==============================

Matrix Toolkits Scala (MTS) is a set of additionnal API using scala language features to leverage the high performance [Matrix Toolkits Java](http://code.google.com/p/matrix-toolkits-java/) (MTJ) project. Notably it allow to use the usual mathematical notation for linear algebra such as:

    def x(t: Double) = x0 + v*t + 0.5*a*t*t

Here `x0`, `v` and `a` are MTJ vectors of same dimension and this function returns a vector convertible implicitely to an MTJ vector.


Status
------

MTS is an *experimental* project under development. Although its API should be stable, changes can still occur.

Currently, vector operations are implemented and developement will sonn move on matrix operations.

Installation
------------

### Dependencies ###

  * MTJ and thus `netlib-java` and `arpack` (the corresponding jars are included in the repository, `lib` directory)
  * SBT to build.
  * Scalatest for testing, automatically installed by SBT
  * Java 6

### Building ###

  1. Make a copy of this repository with git:
    git clone git://github.com/paradigmatic/mts.git
  2. Install java 6 and [SBT](http://code.google.com/p/simple-build-tool/wiki/Setup)
  3. Install the build dependencies by running:
    sbt update
  4. Compile it:
    sbt compile
  5. Test it:
    sbt test
  6. Package it:
    sbt package

### Using ###

After following the procedure above, you should obtain a jar file in the `/target/scala_2.8.0/` called `mts_2.8.0-x.y.z.jar`.

You should then include this jar in the classpath, along with the other jar in the `lib` folder.

Documentation
-------------

Check the project wiki.

License
-------

MTS is a free and open software licensed with the GNU General Public License v3. Chek the LICENSE file for details.



