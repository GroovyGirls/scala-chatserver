package de.htw.messenger

import java.io.{BufferedWriter, File, FileWriter}

import com.google.gson.{Gson, GsonBuilder}

/**
  * @author Vera
  */
object UserService {

  var _userFile: String = "data/users.json"
  private val gson: Gson = new GsonBuilder().create

  def userFile_(userFile : String) = _userFile = userFile

  def userFile : String = _userFile

  private var registeredUsers: Map[String, User] = {
    val json = scala.io.Source.fromFile(userFile).mkString
    val users: Array[User] = gson.fromJson(json, classOf[Array[User]])
    users.map(user => (user.email, user)).toMap
  }

  def validateUser(user: User) = !registeredUsers.contains(user.email)

  def addUser(user: User) = {
    registeredUsers = registeredUsers + (user.email -> user)
    val json: String = gson.toJson(registeredUsers.values.toArray)
    val file = new File(userFile)
    val bw = new BufferedWriter(new FileWriter(file))
    try {
      bw.write(json)
    } finally {
      bw.close()
    }
  }


}
