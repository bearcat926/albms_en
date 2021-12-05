package alb.framework.config.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName WebSocketConfig
 * @Description supportwebsocketThe configuration of the class
 * @Date 2020/7/23 11:00
 */
@Configuration
public class WebSocketConfig {

    /**
     * Into aServerEndpointExporter,theBeanWill automatically register to use@ServerEndpointAnnotated declarativewebsocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
