import sbt._

class MTSProject(info: ProjectInfo) extends DefaultProject(info)
{

  val scalatest = "org.scalatest" % "scalatest" % "1.2"

  //override val mainClass = Some("aggreg.Exp")

  override def mainScalaSourcePath = "src"
  override def mainResourcesPath = "resources"
        
  override def testScalaSourcePath = "test-src"
  override def testResourcesPath = "test-resources"


}
