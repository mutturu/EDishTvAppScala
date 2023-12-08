package org.example.controller
import org.example.model.User
import org.example.repository.UserDatabase

import scala.::
import scala.collection.mutable.ListBuffer

class UserController {
  var userlist:ListBuffer[User]=new ListBuffer[User]
  var userDatabase:UserDatabase=new UserDatabase()

  def createDatabaseAndTablesController(): Boolean = {
    var k=userDatabase.createDatabaseAndTables()
    true
  }
  def closeConnectionToJdbc(): Unit = {
    userDatabase.closeConnectionToJdbcQuery()
  }
  def registerUser(user: User): String = {

    var k=userDatabase.registerUser(user)
    k

  }
  def forgetPassword(username:String): Boolean = {
    var k=userDatabase.forgetPasswordQuery(username)
    k
  }

  def userLogin(username:String,password:String): Boolean = {
    var k=userDatabase.userLoginQuery(username, password)
    k


  }
  def subscribeTvChannel(username:String,channelid:Int): String = {
    var k=userDatabase.subscribeTvChannelQuery(username,channelid)
    k
  }

  def unSubscribeTvChannel(username:String,channelid:Int): String = {
    if(userDatabase.unSubscribeTvChannelQuery(username, channelid))
      {
        "UnSubscribed Tv Channel Successfully!"
      }
    else
      {
        "Failed To UnSubsribe tv Channel!"
      }

  }

  def viewSubscriptionDetails(username:String): String = {
    if(userDatabase.viewSubscriptionDetailsQuery(username)) {
      "Displayed Subscription Details Sucessfully"
    }
    else{
      "Failed to Fetch Subscription Details"
    }
  }
  def addBalanceToWallet(username:String,money:Int): Boolean = {
    if(userDatabase.addBalanceToWalletQuery(username,money))
      {
        true
      }
    else
      {
        false
      }
  }


}
