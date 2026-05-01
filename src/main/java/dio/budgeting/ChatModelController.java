package dio.budgeting;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatModelController {
    private final ChatModel chatModel;

    public ChatModelController(ChatModel chatModel){
        this.chatModel = chatModel;
    }

    @GetMapping("/chat-model")
    String chat(String prompt){
        return this.chatModel.call(prompt);
    }


}
