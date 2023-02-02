package conebot.discord.client;

import conebot.discord.utilites.Enviroment;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DiscordBot extends Thread{

    private final String token;

    public DiscordBot() {
        token = Enviroment.TOKEN;
    }

    @Override
    public void run(){
        DiscordClient client = DiscordClient.create(token);
        Mono<Void> login = client.withGateway(this::gateway);
        login.block();

    }

    private Flux<Message> gateway(GatewayDiscordClient gateway){
        return gateway.on(MessageCreateEvent.class, this::messageHandler);
    }

    private MessageCreateMono eventToChanel(MessageChannel channel){
        return channel.createMessage("pong");
    }

    private Mono<Message> messageHandler(MessageCreateEvent event){
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("!ping")) {
            return message.getChannel().flatMap(this::eventToChanel);
        }
        return Mono.empty();
    }

}
