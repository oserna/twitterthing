package au.com.ing.arq.twitterthing.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/twitter")
public class WebHooksController {

    @RequestMapping(method = RequestMethod.GET)
    public String challenge(@RequestParam("crc_token") String token)
    {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> append( @RequestBody String message){
        System.out.println(message);
        return ResponseEntity.ok().build();
    }

}
