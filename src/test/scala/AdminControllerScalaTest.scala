
import org.scalatest.funsuite.AnyFunSuite
import org.example.model.Channel

import org.example.controller.AdminController
class AdminControllerScalaTest extends AnyFunSuite{
  var adminController=new AdminController()


  //Admin Login
  test("Admin Login with valid credentials") {
    val expected = true
    val actual = adminController.adminLogin("veera@gmail.com", "6415@Veera")
    assertResult(expected)(actual)
  }
 /* test("Admin Login with wrong credentials") {
    val expected = false
    val actual = adminController.adminLogin("veeraa@gmail.com", "641@Veera")
    assertResult(expected)(actual)
  }*/

  //Add Channel
  test("Adding channels using admin credentials"){
    val k=Channel("NTv",10,"Telugu","News Channel")
    val expected = "Added Tv Channel Successfully!"
    val actual = adminController.addANewTvChannel(k)
    assertResult(expected)(actual)
  }

  //update Tv Channel
  test("Updating existing channel")
  {
    val expected = "No channel Exist with given channelId"
    val actual = adminController.updateAnExistingTvChannel(100)
    assertResult(expected)(actual)
  }
  //
  test("List All tv Channels"){
    val expected = true
    val actual = adminController.ListAllTvChannels()
    assertResult(expected)(actual)
  }

}
