package com.rapaccinim.messagetemplates;

import com.symphony.bdk.core.activity.command.CommandContext;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.spring.annotation.Slash;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PriceCommand {

    private MessageService messages;

    public PriceCommand(MessageService messages){
        this.messages = messages;
    }

    @Slash(value = "/price", mentionBot = false)
    public void showPriceForm(CommandContext context){
        String form = messages.templates()
                .newTemplateFromClasspath("/templates/price-form.ftl")
                .process(Map.of());

        messages.send(context.getStreamId(), form);
    }
}
