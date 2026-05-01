package dio.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GROQ_API_KEY", matches = ".+")
public class OpenAiChatClientIT {
    @Autowired
    ChatModel openAiChatModel;

    @Test
    void should_executeSun_when_prompted(){
        var chatClient = ChatClient.builder((ChatModel) openAiChatModel).defaultSystem("Você é um matemático").build();

        var prompt = new Prompt("Some 10 mais 20, Depois subtraia 30 do resultado anterior. E explique o resultado final ");
        var response = chatClient.prompt(prompt).call().content();

        assertThat(response).contains("0");
        System.out.println(response);
        System.out.println("Chave utilizada: " + System.getenv("GROQ_API_KEY"));
    }


}

























