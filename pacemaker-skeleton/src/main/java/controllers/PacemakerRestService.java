package controllers;

import io.javalin.Context;
import models.Activity;

import models.Location;
import models.Message;
import models.User;
import static models.Fixtures.users;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public class PacemakerRestService {

  PacemakerAPI pacemaker = new PacemakerAPI();

  PacemakerRestService() {
    users.forEach(
        user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password));
    
  }

  public void listUsers(Context ctx) {
    System.out.println("list users requested");
    ctx.json(pacemaker.getUsers());
  }
  
  public void createUser(Context ctx) {
    User user = ctx.bodyAsClass(User.class);
    User newUser = pacemaker
        .createUser(user.firstname, user.lastname, user.email, user.password);
    ctx.json(newUser);
  }
  
  public void listUser(Context ctx) {
    String id = ctx.pathParam("id");
    ctx.json(pacemaker.getUser(id));
  }
  
  public void deletetUser(Context ctx) {
    String id = ctx.pathParam("id");
    ctx.json(pacemaker.deleteUser(id));
  }
  
  public void deleteUsers(Context ctx) {
    pacemaker.deleteUsers();
    ctx.json("REMOVED");
  }
  
  public void getActivities(Context ctx) {
    String id = ctx.pathParam("id");
    User user = pacemaker.getUser(id);
    if (user != null) {
      ctx.json(user.activities.values());
    } else {
      ctx.status(404);
    }
  }

  public void createActivity(Context ctx) {
    String id = ctx.pathParam("id");
    User user = pacemaker.getUser(id);
    if (user != null) {
      Activity activity = ctx.bodyAsClass(Activity.class);
      Activity newActivity = pacemaker
          .createActivity(id, activity.type, activity.location, activity.distance);
      ctx.json(newActivity);
    } else {
      ctx.status(404);
    }
  }
  
  public void getActivity(Context ctx) {
    String id = ctx.pathParam("activityid");
    Activity activity = pacemaker.getActivity(id);
    if (activity != null) {
      ctx.json(activity);
    } else {
      ctx.status(404);
    }
  }
  
  public void addLocation(Context ctx) {
    String id = ctx.pathParam("activityid");
    Activity activity = pacemaker.getActivity(id);
    if (activity != null) {
      Location location = ctx.bodyAsClass(Location.class);
      activity.route.add(location);
      ctx.json(location);
    } else {
      ctx.status(404);
    }
  }

  public void getActivityLocations(Context ctx) {
    String id = ctx.pathParam("activityId");
    Activity activity = pacemaker.getActivity(id);
    if (activity != null) {
      ctx.json(activity.route);
    } else {
      ctx.status(404);
    }
  }
  
  public void deleteActivities(Context ctx) {
    String id = ctx.pathParam("id");
    pacemaker.deleteActivities(id);
    ctx.json(204);
  }
  

  public void followFriend(Context ctx)
  {
	  String friendemail=ctx.pathParam("emailid");
	    String userid=ctx.pathParam("id");
  	if(friendemail!=null)
  	{
  	pacemaker.followFriend(userid, friendemail);
  		
  		System.out.println("follow Users email");
  	}
  	
  	
  }
  public void listFriends(Context ctx)
  {
	  String userid=ctx.pathParam("id");
	  if(userid!=null)
	  {
		  ctx.json(pacemaker.friendsIndex.get(userid));
	  }
	  else
	  {
		  ctx.status(404);
	  }
	
  }
  
  public void getFriendActivities(Context ctx) {
	    String email = ctx.pathParam("email");
	    User friend= pacemaker.getUserByEmail(email);
	    if (friend != null) {
	      ctx.json(friend.activities.values());
	    } else {
	      ctx.status(404);
	    }
	  }
  public void unfollowFriend(Context ctx) {
	    String email = ctx.pathParam("email");
	    String id=ctx.pathParam("id");
	    User user=pacemaker.getUser(id);
	    if(user!=null)
	    {
	    	ctx.json(pacemaker.unfollowFriend(user.id,email));
	    }
	    
	  }
  
  public void messageFriend(Context ctx) throws IOException
  {
	  String id=ctx.pathParam("id");
	  String email=ctx.pathParam("email");
	  User user=pacemaker.getUser(id);
	  if(user!=null)
	  {
	  Message message= ctx.bodyAsClass(Message.class);
	  Message sentmessage=pacemaker.messageFriend(message);
	       
	    ctx.json(sentmessage);
	  }
	  else
	  {
		  ctx.status(404);
	  }
  }
  	
 public void listMessages(Context ctx)
 {
	 System.out.println("list messages requested");
	 String email=ctx.pathParam("email");
	 User user=pacemaker.getUserByEmail(email);
	 if(user!=null)
	 {
	    ctx.json(user.message);
 }
 else
 {
	 ctx.status(404);
 }
}
}


