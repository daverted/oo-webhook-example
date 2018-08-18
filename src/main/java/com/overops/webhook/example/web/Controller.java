package com.overops.webhook.example.web;

import com.overops.webhook.example.data.Event;
import com.overops.webhook.example.integrations.PivotalService;
import com.overops.webhook.example.integrations.PivotalStory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    private PivotalService pivotalService;

    @Autowired
    public Controller(PivotalService pivotalService) {
        this.pivotalService = pivotalService;
    }

    @PostMapping(value = "/wh/simple", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity simple(@RequestBody Event event) {

        if (Event.Type.ALERT.equals(event.getType())) {

            log.debug("OverOps event posted to /simple via WebHook integration: {}", event.toString());

            // add your custom logic here to do something with the Event...


        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/wh/pivotal-tracker", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity pivotalTracker(@RequestBody Event event) {

        if (Event.Type.ALERT.equals(event.getType())) {

            log.debug("OverOps event posted to /pivotal-tracker via WebHook integration: {}", event.toString());

            RestTemplate restTemplate = new RestTemplateBuilder().build();

            restTemplate.postForObject(pivotalService.getStoryUrl(), pivotalService.getPivotalStory(event), PivotalStory.class, (Object) null);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }


}
