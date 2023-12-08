package org.example.repository
import org.example.ETvDishAppMain
import org.example.model.User
import org.example.model.Channel

import scala.io.StdIn
import java.sql.{Connection, Date, DriverManager, PreparedStatement, ResultSet, SQLException, Statement}


class UserDatabase {
  private var connection: Connection = _
  private var logger=ETvDishAppMain.logger

  val url = "jdbc:mysql://localhost:3306/eDishTvApp"
  val driver = "com.mysql.cj.jdbc.Driver"
  val username = "root"
  val password = "6415@Veera"
  Class.forName(driver)
  connection = DriverManager.getConnection(url, username, password)

  def createDatabaseAndTables(): Boolean = {
    try {
      //creating database eDishTvApp
      var statement = connection.createStatement()
      var query = s"create database if not exists eDishTvApp;"
      statement.executeUpdate(query)

      //use database eDishTvApp
      statement = connection.createStatement()
      query = s"use eDishTvApp;"
      statement.executeUpdate(query)

      //create users table
      statement = connection.createStatement()
      query = s"create table  if not exists users(userid int primary key auto_increment ,firstName varchar(40) not null,lastName varchar(40) not null,emailId varchar(30) unique not null,loginId varchar(20) unique not null,passwordfirst varchar(20) not null,confirmpassword varchar(20) not null);"
      statement.executeUpdate(query)
      //create channels table
      statement = connection.createStatement()
      query = s"create table  if not exists channels(channelid int primary key auto_increment ,tvChannelName varchar(40) not null,monthlySubscriptionFee long not null,language varchar(30)  not null,description varchar(250) not null);"
      statement.executeUpdate(query)
      //create subscriptions table
      statement = connection.createStatement()
      query = s"create table  if not exists subscriptions(subscriptionid int primary key auto_increment ,userid int not null ,channelid int not null,foreign key(userid) references users(userid),foreign key(channelid) references channels(channelid));"
      statement.executeUpdate(query)
      //create billings table
      statement = connection.createStatement()
      query = s"create table  if not exists billings(billingid int primary key auto_increment ,userid int not null unique, currentBalance int unsigned,expiryDate date,sumOfChannels int,expireDays int,startDate date);"
      statement.executeUpdate(query)
      true
    }
    catch{
      case e: Exception => logger.error(e.printStackTrace)
        false
    }


  }

  def registerUser(user: User): String = {
    try {
      var statement = connection.createStatement()
      var query = s"select * from users where (emailId='${user.emailId}' OR loginId='${user.loginId}');"
      var resultSet: ResultSet = statement.executeQuery(query)

      if(!resultSet.next()){
      statement = connection.createStatement()
      query = s"INSERT INTO USERS(firstName,lastName,emailId,loginId,passwordfirst,confirmpassword) VALUES ('${user.firstName}', '${user.lastName}','${user.emailId}','${user.loginId}','${user.password}','${user.confirmPassword}');"
      val assignmentCount: Int = statement.executeUpdate(query)
      "Registered Successfully,please login with credentials!"
    }
      else
        {
          "EmailId or Login Id Already Registered,Please Register with different EmailId or LoginId"
        }

    }


    catch {
      case e: Exception => logger.error(e.printStackTrace)
        "Entered information is not valid!  check below constraints is not satisfied\n" +
          "* Password and Confirm Password must be same\n*All details fields must be mandatory\n*Login Id and Email must be unique\nplease register again"


    }
  }

  def userLoginQuery(username: String, password: String): Boolean = {
    try {
      var valid = false
      val statement = connection.createStatement()
      val query = s"select * from users where (emailId='$username');"
      val resultSet: ResultSet = statement.executeQuery(query)
      var k: String = ""
      while (resultSet.next()) {
        k = resultSet.getString("passwordfirst")
      }
      if (k.equals(password)) {
        valid = true

      }
      valid
    }
    catch {
      case e: Exception => logger.error(e.printStackTrace)
        false


    }


  }
  def adminLoginQuery(username:String,password:String): Boolean = {
    try {
      var valid = false
      val statement = connection.createStatement()
      val query = s"select * from users where (emailId='$username');"
      val resultSet: ResultSet = statement.executeQuery(query)
      var k: String = ""
      var row=0;
      while(!resultSet.next())
        {
          logger.info("Not registered with these username")
        }
      do {
        k = resultSet.getString("passwordfirst")
        row=resultSet.getInt("userid")

      }while(resultSet.next())
      if (k.equals(password) && row==1) {
        valid = true

      }
      valid
    }
    catch {
      case e: Exception => logger.error(e.printStackTrace)
        false


    }


  }

  def forgetPasswordQuery(username: String): Boolean = {
    try {
      val statement = connection.createStatement()
      val query = s"select * from users where (emailId='$username');"
      val resultSet: ResultSet = statement.executeQuery(query)
      var k: String = ""
      if (!resultSet.next()) {
        false
      }
      else {
        do {
          k = resultSet.getString("passwordfirst")
        } while (resultSet.next())
      logger.info("your password is:" + k)
      true
    }


    }
    catch {
      case e: Exception => logger.error(e.printStackTrace)
        false


    }


  }

  def addANewTvChannelQuery(channel: Channel): String = {
    try {
      val statement = connection.createStatement()
      val query = s"INSERT INTO CHANNELS(tvChannelName,monthlySubscriptionFee,language,description) VALUES ('${channel.tvChannelName}', '${channel.monthlySubscriptionFee}','${channel.language}','${channel.description}');"
      val channelcount: Int = statement.executeUpdate(query)

      "Added Tv Channel Successfully!"
    }

    catch {
      case e: Exception => logger.error(e.printStackTrace)
        "Entered information is not valid!"


    }
  }

  def ListAllTvChannelsQuery(): Boolean = {
    var k=false
    try {
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM channels")
      while (resultSet.next()) {
        val channelid = resultSet.getInt("channelId")
        val tvChannelName = resultSet.getString("tvChannelName")
        val language=resultSet.getString("language")
        val montlysubscription=resultSet.getString("monthlySubscriptionFee")
        val descrip=resultSet.getString("description")
        println(s"ChannelId:$channelid,Channel Name:$tvChannelName,Language:$language,Montly Subscription Fee:$montlysubscription,Description:$descrip")
      }
      k=true
      k
    }

    catch {
      case e: Exception => logger.error(e.printStackTrace)
        k
    }

  }

  def updateAnExistingTvChannelQuery(channelid: Int): String = {
    var k=""
    try {
      val statement = connection.createStatement()

      var query = s"SELECT * FROM channels WHERE channelid=$channelid"
      val resultSet = statement.executeQuery(query)
      if (!resultSet.next()) {logger.info("No channel Exist with given channelId")
        k="No channel Exist with given channelId"
      }
      else {
        logger.info("Enter Tv Channel Name:")
        var tvChannelName = StdIn.readLine()
        logger.info("Enter Monthly Subscription Fee:")
        var monthlySubscriptionFee = StdIn.readLine()
        logger.info("Enter Language name:")
        var language = StdIn.readLine()
        logger.info("Enter Channel Discription")
        var description = StdIn.readLine()
        query = s"UPDATE channels SET tvChannelName='$tvChannelName',monthlySubscriptionFee=$monthlySubscriptionFee,language='$language',description='$description' WHERE channelid=$channelid;"
        statement.executeUpdate(query)
        logger.info("updated successfully")
        k="updated successfully"
      }
      k
    }
    catch {
      case e: Exception => logger.error(e.printStackTrace())
        "Error"
    }

  }


  def subscribeTvChannelQuery(username: String, channelid: Int): String = {
    var kkk=""
    try {

      val statement = connection.createStatement()
      var query = s"SELECT * FROM channels WHERE channelid=$channelid"
      var resultSet = statement.executeQuery(query)
      var userid: Int = 0
      var kk:Int=0
      if (!resultSet.next()) {
        logger.info("No channel Exist with given channelId")
        kkk="No channel Exist with given channelId"
        kkk
      }
      else {
        try {
          try {
            val statement = connection.createStatement()
            val query1 = s"SELECT * FROM users WHERE emailId='$username'"
            var resultSet = statement.executeQuery(query1)
            while (resultSet.next()) {
              userid = resultSet.getInt("userid")
            }
          }
          catch {
            case e: Exception => logger.error(e.printStackTrace)


          }
          val statement = connection.createStatement()
          val query = s"select c.channelid from channels c join subscriptions s on c.channelid=s.channelid where s.userid=$userid;"
          val resultSet: ResultSet = statement.executeQuery(query)
          var cid: Int = 0
          while (resultSet.next()) {
            cid = resultSet.getInt("channelid")
            if(cid==channelid)
              {
                logger.info("Already Subscribed to this Channel")
                kk=1
                kkk="Already Subscribed to this Channel"

              }


          }
        }
        catch {
          case e: Exception => logger.error(e.printStackTrace)

        }
      }
      if(kk==0) {
        query = s"INSERT INTO SUBSCRIPTIONS(userid,channelid) VALUES (${userid}, ${channelid});"
        val co = statement.executeUpdate(query)
        logger.info("Subscribed to channel successfully" + channelid + " co" + co)
        kkk="Subscribed to channel successfully"
        kkk
      }
      else {
        kkk="Already Subscribed to this Channel"
      }
      kkk
    }

    catch {
      case e: Exception =>logger.error(e.printStackTrace)
        "Error"


    }

  }

  def unSubscribeTvChannelQuery(username: String, channelid: Int): Boolean = {
    var kk=false
    try {
      var userid = 0
      try {
        val statement = connection.createStatement()
        val query1 = s"SELECT * FROM users WHERE emailId='$username'"
        var resultSet = statement.executeQuery(query1)
        while (resultSet.next()) {
          userid = resultSet.getInt("userid")
        }
      }
      catch {
        case e: Exception =>logger.error(e.printStackTrace)
          kk


      }
      val statement = connection.createStatement()
      val query = s"select c.channelid,c.tvChannelName from channels c join subscriptions s on c.channelid=s.channelid where s.userid=$userid order by c.channelid;"
      val resultSet: ResultSet = statement.executeQuery(query)
      var cid: Int = 0
      var cn: String = ""
      while (resultSet.next()) {
        cid = resultSet.getInt("channelid")
        if(cid==channelid)
          {
            val statement = connection.createStatement()
            val query = s"delete from subscriptions where channelid=$channelid;"
            val com= statement.executeUpdate(query)
            if(com>0)
              {
                logger.info("Unsubscribed to Tv Channel Id+"+channelid)
                kk=true
              }


          }

      }
      kk

    }
    catch {
      case e: Exception => logger.error(e.printStackTrace)
        false

    }

  }

  def viewSubscriptionDetailsQuery(username: String): Boolean = {
    var k=false
    try {
      var userid = 0
      var montlyfee=0
      var expiredays=0
      var expiremonths=0
      var expirydate:Date=null
      var expiredaysfinal=0
      var startDate:Date=null
      try {
        val statement = connection.createStatement()
        val query1 = s"SELECT * FROM users WHERE emailId='$username'"
        var resultSet = statement.executeQuery(query1)
        while (resultSet.next()) {
          userid = resultSet.getInt("userid")
        }
      }
      catch {
        case e: Exception => logger.error(e.printStackTrace)


      }
      var statement = connection.createStatement()
      var query = s"select c.channelid,c.tvChannelName, c.monthlySubscriptionFee from channels c join subscriptions s on c.channelid=s.channelid where s.userid=$userid order by c.channelid;"
      var resultSet: ResultSet = statement.executeQuery(query)
      var cid: Int = 0
      var cn: String = ""
      var montlychargechannel=0
      var currentamount:Int=0
      while (resultSet.next()) {
        cid = resultSet.getInt("channelid")

        cn = resultSet.getString("tvChannelname")
        montlychargechannel=resultSet.getInt("monthlySubscriptionFee")
        logger.info(s"channelId:$cid,TvChannelName:$cn,channel price:$montlychargechannel")


      }

      try{
        statement=connection.createStatement()
        query=s"select sum(c.monthlySubscriptionFee),round((b.currentBalance/sum(c.monthlySubscriptionFee))) as expirydaysplan from channels c join subscriptions s on c.channelid=s.channelid join billings b on s.userid=b.userid where s.userid=$userid ;"
        resultSet=statement.executeQuery(query)
        while(resultSet.next()){
         montlyfee=resultSet.getInt("sum(c.monthlySubscriptionFee)")
          expiremonths=resultSet.getInt("expirydaysplan")
        }

      }
      catch {
        case e: Exception => logger.error(e.printStackTrace)


      }
      try {
        expiredays=expiremonths*30
        statement = connection.createStatement()
        query = s"update billings set sumOfChannels=$montlyfee,expiryDate=date(date_add(now(),interval $expiredays day)),expireDays=$expiredays,startDate=curdate() where userid=$userid;"
        val k:Int= statement.executeUpdate(query)
      }
      catch {
        case e: Exception => logger.error(e.printStackTrace)
      }

      try{
        var totalchannelprice=0
        statement = connection.createStatement()
        query = s"SELECT * FROM billings WHERE userid=$userid"
        resultSet = statement.executeQuery(query)
        while (resultSet.next()) {
          currentamount= resultSet.getInt("currentBalance")
          expirydate=resultSet.getDate("expiryDate")
          expiredaysfinal=resultSet.getInt("expireDays")
          totalchannelprice=resultSet.getInt("sumOfChannels")
          startDate=resultSet.getDate("startDate")


        }
        logger.info(s"Current Balance Amount:$currentamount,All Subscribed channel price:$totalchannelprice,start Date:$startDate, Expiry Date:$expirydate, Expire in $expiredaysfinal days,Expire in $expiremonths months!")
        k=true
        k
        }
      catch {
        case e: Exception => logger.error(e.printStackTrace)
          k

      }
    }
    catch {
      case e: Exception => logger.error(e.printStackTrace)
        k

    }


  }
  def addBalanceToWalletQuery(username:String,money:Int): Boolean = {
    var k=false
    try {
      var userid: Int = 0
      var oldBalance: Int = 0
      val statement = connection.createStatement()
      val query1 = s"SELECT userid FROM users WHERE emailId='$username'"
      var resultSet = statement.executeQuery(query1)
      while (resultSet.next()) {
        userid = resultSet.getInt("userid")
      }
      try {
        val statement = connection.createStatement()
        var query = s"SELECT * FROM billings WHERE userid=$userid"
        resultSet = statement.executeQuery(query)
        if (!resultSet.next()) {
          query = s"INSERT INTO BILLINGS(userid,currentBalance) VALUES (${userid}, ${money});"
          val co = statement.executeUpdate(query)
          if(co>=0)
            {
              logger.info(s"Added Money:$money to the wallet Successfully!\nCurrent Balance=$money")
              k=true
            }


        }
        else {
          do{
            oldBalance = resultSet.getInt("currentBalance")

          }while(resultSet.next())
          val newbalance=oldBalance+money
          logger.info("Old Balance:"+oldBalance)
          query = s"UPDATE BILLINGS SET currentBalance=$newbalance where userid=${userid};"
          val co = statement.executeUpdate(query)
          if (co >0) {
            logger.info(s"Added Money:$money to the wallet Successfully!\nCurrent Balance=$newbalance")
            k=true
          }
        }

      k
      }
      catch {
        case e: Exception => logger.error(e.printStackTrace)
          k
      }
    }

      catch
      {
        case e: Exception => logger.error(e.printStackTrace)
          k
      }
    }

  def closeConnectionToJdbcQuery(): Unit = {
    connection.close()
  }

}
