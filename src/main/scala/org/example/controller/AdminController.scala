package org.example.controller
import org.example.model.User
import org.example.model.Channel
import org.example.repository.UserDatabase

import scala.collection.mutable.ListBuffer
import scala.io.StdIn


class AdminController {
  var channelList:ListBuffer[Channel]=new ListBuffer[Channel]
  var userDatabase:UserDatabase=new UserDatabase()

  def adminLogin(username: String, password: String): Boolean = {
    var k = userDatabase.adminLoginQuery(username, password)
    k


  }



  def addANewTvChannel(channel: Channel): String = {
    var k=userDatabase.addANewTvChannelQuery(channel)
    k

  }

  def updateAnExistingTvChannel(channeid:Int): Unit = {
    userDatabase.updateAnExistingTvChannelQuery(channeid)
    println("update An Existing Tv Channel")
  }

  def ListAllTvChannels(): Unit = {
    userDatabase.ListAllTvChannelsQuery()
  }


}
