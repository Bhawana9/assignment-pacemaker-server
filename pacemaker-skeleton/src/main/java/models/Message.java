package models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


public class Message implements Serializable
{

public String sender;
public String message;
public String reciever;
public String id;
public Message()
{}

public String getId()
{
	return id;
}
public String getsender()
{
	return sender;
	}
public String getreciever()
{
return reciever;	
}

  public String getMessage()
  {
    return message;
  }
  
 
  
   
  public Message(String sender,String reciever, String message)
  {
	  this.id = UUID.randomUUID().toString();
        this.sender=sender;
	    this.reciever=reciever;
	    this.message=message;
	  }
   
}

