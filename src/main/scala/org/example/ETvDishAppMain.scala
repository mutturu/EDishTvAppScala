package org.example
import org.example.controller.{AdminController, UserController}
import org.example.model.User
import org.example.model.Channel
import org.example.repository.UserDatabase

import scala.io.StdIn

import org.apache.log4j.BasicConfigurator
import org.apache.log4j.LogManager

object ETvDishAppMain {
  System.setProperty("log4j.Configuration", "log4j2.xml")

  val logger = LogManager.getLogger(getClass)


  var userController: UserController = new UserController()
  var adminController: AdminController = new AdminController()
  var userDatabase: UserDatabase = new UserDatabase()

  def main(args: Array[String]): Unit = {
    BasicConfigurator.configure()

    if (userController.createDatabaseAndTablesController()) {
      var exit = false
      while (!exit) {
        welcomeToDissTv()
        val option = StdIn.readInt()
        option match {
          case 1 => registerAsUser()
          case 2 => userLogin()
          case 3 => adminLogin()
          case 4 => viewAllExistingTvchannels()
          case 5 => forgetPassword()
          case 6 => exit = true
          case _ => println("Invalid Option")
        }
      }
      userController.closeConnectionToJdbc()
    }
    else {
      logger.warn("Database Not created")
    }


    def welcomeToDissTv(): Unit = {
      logger.info("-- Welcome to eDish app --\n1-Register as User\n2-User login\n3-Admin Login\n4-View All existing Tv Channels\n5 -forget Password \n6-Exit\n Enter your option")

    }

    def registerAsUser(): Unit = {
      logger.info("Signup,All fields are mandatory!")
      logger.info("Enter First Name:")
      var firstName: String = StdIn.readLine()
      logger.info("Enter Last Name:")

      var lastName: String = StdIn.readLine()
      logger.info("Enter Email Id:")

      var email: String = StdIn.readLine()
      logger.info("Enter Login Id:")

      var loginId: String = StdIn.readLine()
      logger.info("Enter Password:")

      var password: String = StdIn.readLine()
      logger.info("Enter confirm Password:")

      var confirmPassword: String = StdIn.readLine()
      if (password.equals(confirmPassword)) {
        var kk = new User(firstName, lastName, email, loginId, password, confirmPassword)
        var kkk = userController.registerUser(kk)
        logger.info(kkk)
      }
      else {
        logger.info("password and confirmed to be same!")
      }
    }


    // admin Related Methods
    def adminLogin(): Unit = {
      logger.info("Enter your admin credentials!")
      logger.info("Enter your username:")
      var username = StdIn.readLine()
      logger.info("Enter your password")
      var password = StdIn.readLine()
      if (adminController.adminLogin(username, password)) {
        adminLogins(username)
      }
      else {
        logger.warn("Invalid username or password")

      }
    }

    def adminLogins(username: String): Unit = {
      var logout = false
      while (!logout) {
        welcomeToEDishTvAdmin(username)

        val option = StdIn.readInt()
        option match {
          case 1 => addANewTvChannel()
          case 2 => updateAnExistingTvChannel()
          case 3 => ListAllTvChannels()
          case 4 => logout = true
          case _ => println("Invalid Option,please Enter Correct Option")
        }


      }


    }

    def addANewTvChannel(): Unit = {
      println("Enter new Tv Channel name:")
      var name = StdIn.readLine()
      println("Enter montly subscription fee")
      var montlyfee = StdIn.readLong()
      println("Enter Language")
      var language = StdIn.readLine()
      println("Describe Tv Chanel")
      var describe = StdIn.readLine()
      var cha = new Channel(name, montlyfee, language, describe)
      logger.info(adminController.addANewTvChannel(cha))

    }

    def updateAnExistingTvChannel(): Unit = {
      println("Enter ChannelId which to be Updated:")
      var k = StdIn.readInt()

      adminController.updateAnExistingTvChannel(k)


    }

    def ListAllTvChannels(): Unit = {
      adminController.ListAllTvChannels()
      logger.info("All Existing Tv Channels Displayed Sucessfully")
    }

    //User Related Methods
    def userLogin(): Unit = {
      logger.info("Enter your user credentials!")
      logger.info("Enter your username:")
      var username = StdIn.readLine()
      logger.info("Enter your password")
      var password = StdIn.readLine()
      if (userController.userLogin(username, password)) {
        userLogins(username)
      }
      else {
        logger.warn("Invalid username or password")

      }
    }


    def userLogins(username: String): Unit = {
      var logout = false
      while (!logout) {
        welcomeToEDishTvUser(username)

        val option = StdIn.readInt()
        option match {
          case 1 => subscribeTvChannel(username)
          case 2 => unSubscribeTvChannel(username)
          case 3 => viewSubscriptionDetails(username)
          case 4 => addBalanceToWallet(username)
          case 5 => logout = true
          case _ => logger.info("Invalid Option,Please enter correct option")
        }


      }
    }

    def subscribeTvChannel(username: String): Unit = {
      logger.info("Enter Channelid to subscribe:")
      var channeid = StdIn.readInt()
      userController.subscribeTvChannel(username, channeid)
    }

    def unSubscribeTvChannel(username: String): Unit = {
      logger.info("Enter channelid which to do be unscribe:")
      var channelid = StdIn.readInt()
      userController.unSubscribeTvChannel(username, channelid)
      logger.info("UnSubscribe to a Tv Channel")
    }

    def viewSubscriptionDetails(username: String): Unit = {
      userController.viewSubscriptionDetails(username)

    }

    def addBalanceToWallet(username: String): Unit = {
      logger.info("Add Money to the wallet:")
      var money = StdIn.readInt()
      if (money >= 0) {
        userController.addBalanceToWallet(username, money)
      }
      else {
        logger.warn("enter amount is more than 0")
      }

    }

    def welcomeToEDishTvUser(username: String): Unit = {
      println(s"-- Welcome to eDishTv(logged in as $username)--\n1 - Subscribe to a Tv Channel\n2 - Unsubscribe to a TV Channel\n3 - View Subsription Details\n4 - Add Balance\n5 - logout\nEnter your option:")

    }


    def welcomeToEDishTvAdmin(userName: String): Unit = {
      println(s"-- Welcome to eDishTv(logged in as Admin:$userName)--\n1 - Add a new Tv Channel\n2 - Update an existing TV Channel\n3 - List all TV Channels\n4 - logout")


    }

    def viewAllExistingTvchannels(): Unit = {
      ListAllTvChannels()
    }

    def forgetPassword(): Unit = {
      logger.info("Enter your Email Id:")
      var emailid = StdIn.readLine()
      if (userController.forgetPassword(emailid)) {
        logger.info("success")
      }
      else {
        logger.warn("Invalid Details")
      }

    }
  }
}
