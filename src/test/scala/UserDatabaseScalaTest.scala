import org.example.model.{Channel, User}
import org.scalatest.funsuite.AnyFunSuite
import org.example.repository.UserDatabase

class UserDatabaseScalaTest extends AnyFunSuite{
  var userDatabase=new UserDatabase()
  //Database
  test("Connect Database") {
    val expected = true
    val actual = userDatabase.createDatabaseAndTables()
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

  test("create User") {
    val a = new User("Mutturu", "Rama Subba Reddy", "mutturufgggh@gmail.com", "mutturuttyyy123", "6415@Siva", "6415@Siva")
    val actual = userDatabase.registerUser(a)
    val expected = "Registered Successfully,please login with credentials!"
    assertResult(expected)(actual)
  }
  test("Create user with already registered emailid") {
    val a = new User("Siva", "Rama Subba Reddy", "veera@gmail.com", "siva123", "6415@Siva", "6415@Siva")
    val actual = userDatabase.registerUser(a)
    val expected = "EmailId or Login Id Already Registered,Please Register with different EmailId or LoginId"
    assertResult(expected)(actual)

  }
  //forget password
  test("forget Password") {
    val expected = true
    val actual = userDatabase.forgetPasswordQuery("veera@gmail.com")
    assertResult(expected)(actual)
  }
  test("forget password with wrong username") {
    val expected = false
    val actual = userDatabase.forgetPasswordQuery("veeraa@gmail.com")
    assertResult(expected)(actual)
  }

  //User Login
  test("User Login with valid credentials") {
    val expected = true
    val actual = userDatabase.userLoginQuery("veera@gmail.com", "6415@Veera")
    assertResult(expected)(actual)
  }
  test("User Login with wrong credentials") {
    val expected = false
    val actual = userDatabase.userLoginQuery("veeraa@gmail.com", "641@Veera")
    assertResult(expected)(actual)
  }

  //user operations
  test("Subscribe Channel") {
    val expected = "Subscribed to channel successfully"
    val actual = userDatabase.subscribeTvChannelQuery("veera@gmail.com", 1)
    assertResult(expected)(actual)

  }
  /*test("Subscribe Channel With Wrong channelId") {
    val expected = "No channel Exist with given channelId"
    val actual = userDatabase.subscribeTvChannelQuery("veera@gmail.com", 100)
    assertResult(expected)(actual)
  }*/
  test("Subscribing existing subscribed Channel") {
    val expected = "Already Subscribed to this Channel"
    val actual = userDatabase.subscribeTvChannelQuery("veera@gmail.com", 1)
    assertResult(expected)(actual)
  }

  //unsubscribe
  test("Unsubscribe Tv Channel") {

    val expected = true
    val actual = userDatabase.unSubscribeTvChannelQuery("veera@gmail.com", 1)
    assertResult(expected)(actual)
  }
  test("Unsubscribe Tv channel with wrong details") {
    val expected = false
    val actual = userDatabase.unSubscribeTvChannelQuery("veera@gmail.com", 1000)
    assertResult(expected)(actual)

  }

  //Subscription
  test("...Subscription Details") {
    val expected=true
    val actual = userDatabase.viewSubscriptionDetailsQuery("veera@gmail.com")
    assertResult(expected)(actual)
  }

  //Wallet
  test("Adding Money to wallet") {
    val expected = true
    val actual = userDatabase.addBalanceToWalletQuery("veera@gmail.com", 200)
    assertResult(expected)(actual)

  }


  test("Admin Login with valid credentials") {
    val expected = true
    val actual = userDatabase.adminLoginQuery("veera@gmail.com", "6415@Veera")
    assertResult(expected)(actual)
  }
  /*test("Admin Login with wrong credentials") {
    val expected = false
    val actual = userDatabase.adminLoginQuery("veeraa@gmail.com", "641@Veera")
    assertResult(expected)(actual)
  }*/

  //Add Channel
  test("Adding channels using admin credentials") {
    val k = Channel("NTv", 10, "Telugu", "News Channel")
    val expected = "Added Tv Channel Successfully!"
    val actual = userDatabase.addANewTvChannelQuery(k)
    assertResult(expected)(actual)
  }

  //update Tv Channel
  test("Updating existing channel") {
    val expected = "No channel Exist with given channelId"
    val actual = userDatabase.updateAnExistingTvChannelQuery(100)
    assertResult(expected)(actual)
  }
  //
  test("List All tv Channels") {
    val expected = true
    val actual = userDatabase.ListAllTvChannelsQuery()
    assertResult(expected)(actual)
  }


}
