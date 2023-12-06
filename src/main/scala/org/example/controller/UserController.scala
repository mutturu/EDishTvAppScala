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
  def subscribeTvChannel(username:String,channelid:Int): Unit = {
    var k=userDatabase.subscribeTvChannelQuery(username,channelid)
    println(k)
  }

  def unSubscribeTvChannel(username:String,channelid:Int): Unit = {
    userDatabase.unSubscribeTvChannelQuery(username, channelid)
    println("UnSubscribe to a Tv Channel")
  }

  def viewSubscriptionDetails(username:String): Unit = {
    userDatabase.viewSubscriptionDetailsQuery(username)
    println("view Subscription Details")
  }
  def addBalanceToWallet(username:String,money:Int): Unit = {
    userDatabase.addBalanceToWalletQuery(username,money)
  }


}
