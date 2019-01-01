package controllers;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;

public class RestMain {
  
  private static int getAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 7000;
  }

  public static void main(String[] args) throws Exception {
    Javalin app = Javalin.create();
    app.port(getAssignedPort());
    app.start();
    PacemakerRestService service = new PacemakerRestService();
    configRoutes(app, service);
    
    Map<String, String> map = new HashMap<>();
    map.put("key", "value");
     
    ObjectMapper mapper = new ObjectMapper();
    String jsonResult = mapper.writerWithDefaultPrettyPrinter()
      .writeValueAsString(map);
    
    String jsonInput = "{\"key\": \"value\"}";
    TypeReference<HashMap<String, String>> typeRef 
      = new TypeReference<HashMap<String, String>>() {};
    Map<String, String> readmap = mapper.readValue(jsonInput, typeRef);
  }
  
  static void configRoutes(Javalin app, PacemakerRestService service) {

    app.get("/users", ctx -> {service.listUsers(ctx);});
    
    app.post("/users", ctx -> {service.createUser(ctx);});
    
    app.get("/users/:id", ctx -> {service.listUser(ctx);});
    
    app.delete("/users", ctx -> {service.deleteUsers(ctx);});

    app.delete("/users/:id", ctx -> {service.deletetUser(ctx);});
    
    app.get("/users/:id/activities", ctx -> {service.getActivities(ctx);});

    app.post("/users/:id/activities", ctx -> {service.createActivity(ctx);});
    
    app.get("/users/:id/activities/:activityid", ctx -> {service.getActivity(ctx);});

    app.get("/users/:id/activities/:activityid/locations", ctx -> {service.getActivityLocations(ctx);});
    
    app.post("/users/:id/activities/:activityid/locations", ctx -> {service.addLocation(ctx);});
    
    app.delete("/users/:id/activities", ctx -> {service.deleteActivities(ctx);});
    
    
    
    app.get("/users/:id/friend/:emailid ", ctx -> {service.followFriend(ctx);});
    
    app.get("/users/:id/friend ", ctx->{service.listFriends(ctx);});
    
    app.get("/users/:email/friends/activities", ctx -> {service.getFriendActivities(ctx);});
    
    app.delete("/users/:email/friends", ctx->{service.unfollowFriend(ctx);});
    
    app.post("users/:id/friends/message/recieverid", ctx->{service.messageFriend(ctx);});
    
    app.get("users/:email/message", ctx->{service.listMessages(ctx);});
  }
}
