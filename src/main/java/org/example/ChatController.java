package org.example;

import org.jgroups.BaseMessage;
import org.jgroups.ObjectMessage;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final SimpleChat chatService;

    public ChatController(SimpleChat chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public String addMessage(@RequestBody String name) throws Exception {
//        String name = payload.get("name");
        if (name == null || name.isBlank()) {
            return "Error: 'name' es obligatorio.";
        }

        chatService.receive(new ObjectMessage(null, name) {
        });
        chatService.channel.send(new ObjectMessage(null, name));
        return "Mensaje recibido y replicado en el cluster.";
    }

//    @GetMapping
//    public Map<String, Long> getMessages() {
//        return chatService.getState();
//    }
}
