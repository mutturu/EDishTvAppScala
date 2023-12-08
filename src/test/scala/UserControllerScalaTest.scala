
import org.scalatest.funsuite.{AnyFunSuite}
import org.example.controller.UserController
import org.example.model.User

class UserControllerScalaTest extends AnyFunSuite{
  var userController=new UserController()


//Database
  test("Connect Database")
  {
    val expected=true
    val actual=userController.createDatabaseAndTablesController()
    assertResult(expected)(actual)

  }
  /*test("Closing Database connection")
  {
    val expected=true
    val actual=userController.closeConnectionToJdbc()
    assertResult(true)(false)
  }*/
  //User Registeration
 /* test("create User with password and confirmpassword is not same") {
    val a = new User("Siva", "Rama Subba Reddy", "siva@gmail.com", "siva123", "6415@iva", "6415@Siva")
    val actual = userController.registerUser(a)
    val expected = "Registered Successfully,please login with credentials!"
    assertResult(expected)(actual)
  }*/

  test("create User"){
    val a=new User("Siva","Rama Subba Reddy","sivaghh@gmail.com","siva12366","6415@Siva","6415@Siva")
    val actual=userController.registerUser(a)
    val expected="Registered Successfully,please login with credentials!"
    assertResult(expected)(actual)
  }
  test("Create user with already registered emailid")
  {
    val a=new User("Siva","Rama Subba Reddy","veera@gmail.com","siva123","6415@Siva","6415@Siva")
    val actual = userController.registerUser(a)
    val expected = "EmailId or Login Id Already Registered,Please Register with different EmailId or LoginId"
    assertResult(expected)( actual)

  }
  //forget password
  test("forget Password"){
    val expected=true
    val actual=userController.forgetPassword("veera@gmail.com")
    assertResult(expected)( actual)
  }
  test("forget password with wrong username"){
    val expected = false
    val actual = userController.forgetPassword("veeraa@gmail.com")
    assertResult(expected)(actual)
  }

  //User Login
  test("User Login with valid credentials"){
    val expected = true
    val actual = userController.userLogin("veera@gmail.com","6415@Veera")
    assertResult(expected)(actual)
  }
  test("User Login with wrong credentials"){
    val expected = false
    val actual = userController.userLogin("veeraa@gmail.com", "641@Veera")
    assertResult(expected)(actual)
  }

  //user operations
  test("Subscribe Channel")
  {
    val expected = "Subscribed to channel successfully"
    val actual = userController.subscribeTvChannel("veera@gmail.com",1)
    assertResult(expected)(actual)

  }
  /*test("Subscribe Channel With Wrong channelId"){
    val expected = "No channel Exist with given channelId"
    val actual = userController.subscribeTvChannel("veera@gmail.com", 100)
    assertResult(expected)(actual)
  }*/
  test("Subscribing existing subscribed Channel"){
    val expected = "Already Subscribed to this Channel"
    val actual = userController.subscribeTvChannel("veera@gmail.com", 1)
    assertResult(expected)(actual)
  }

  //unsubscribe
  test("Unsubscribe Tv Channel"){

    val expected = "UnSubscribed Tv Channel Successfully!"
    val actual = userController.unSubscribeTvChannel("veera@gmail.com", 1)
    assertResult(expected)(actual)
  }
  test("Unsubscribe Tv channel with wrong details"){
    val expected = "Failed To UnSubsribe tv Channel!"
    val actual = userController.unSubscribeTvChannel("veera@gmail.com", 1000)
    assertResult(expected)(actual)

  }

  //Subscription
  test("View Subscription Channels")
  {
      val expected = "Displayed Subscription Details Sucessfully"
      val actual = userController.viewSubscriptionDetails("veera@gmail.com")
      assertResult(expected)(actual)
  }

  //Wallet
  test("Adding Money to wallet"){
    val expected = true
    val actual = userController.addBalanceToWallet("veera@gmail.com",400)
    assertResult(expected)(actual)

  }




}
