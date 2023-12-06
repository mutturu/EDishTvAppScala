import org.example.model.User
import org.scalatest.Assertion
import org.scalatest.Assertions.assertResult
import org.scalatest.funsuite.AnyFunSuite
import org.junit.Assert.assertEquals
import org.junit.Test
import org.example.controller.UserController

class UserControllerTest {
 /* var userController: UserController = new UserController()






  @Test
  def forgetpassword(): Unit = {
    var actual=userController.forgetPassword("veera@123")
    var expected=true
    assertEquals(expected,actual)
  }

  @Test
  def forgetpasswordwrong(): Unit = {
    var actual = userController.forgetPassword("phishing@gmail.com")
    var expected = false
    assertEquals(expected, actual)
  }




  /*@Test
  def registerUser(): Unit = {
    var k = new User("wipro", "Technology Solutions", "wipro", "wipro", "6415@Wipro", "6415@Wipro")
    var actual = userController.registerUser(k)
    var expected = "Registered Successfully,please login with credentials!"
    assertEquals(expected, actual)
  }*/
@Test
  def userLogin(): Unit = {
    var actual=userController.userLogin("veera@123","6415@Veera")
    var expected=true
    assertEquals(expected,actual)
  }

  @Test
  def userLoginFalse(): Unit = {
    var actual = userController.userLogin("veera@123", "6415@veera")
    var expected = false
    assertEquals(expected, actual)
  }

  def subscribeTvChannelNoExist(): Unit = {
    var actual=userController.subscribeTvChannel("veera@123",500)
    var expected="No channel Exist with given channelId"
    assertEquals(expected,actual)
  }

  def subscribeTvChannelAlreadySubscribed(): Unit = {
    var actual = userController.subscribeTvChannel("veera@123", 2)
    var expected = "No channel Exist with given channelId"
    assertEquals(expected, actual)
  }

  def subscribeTvChannel(): Unit = {
    val actual = userController.subscribeTvChannel("veera@123", 2)
    val expected = "No channel Exist with given channelId"
    assertEquals(expected, actual)

  }*/

}
