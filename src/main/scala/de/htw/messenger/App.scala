package de.htw.messenger

import java.net.URI

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig

/**
 * Hello world!
 *
 */
object App {

  def main(args: Array[String]) {

    println( "Hello World!" )
    GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8081"),
      new ResourceConfig(classOf[RegisterController]))


  }

}
