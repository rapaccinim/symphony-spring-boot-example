package com.rapaccinim.messagetemplates;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4MessageSent;
import com.symphony.bdk.gen.api.model.V4SymphonyElementsAction;
import com.symphony.bdk.spring.events.RealTimeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class MyOrderListener {

    private final MessageService messages;

    public MyOrderListener(MessageService messages) {
        this.messages = messages;
    }

    @EventListener
    public void onMessageSent(RealTimeEvent<V4MessageSent> event){
        // first of all let's take the message
        V4Message message = event.getSource().getMessage();
        // get rid of all the HTML and other tags
        String messageText = message.getMessage().replaceAll("<[^>]*>", "");

        // if the user has prompted an /order command then the bot will reply with a form
        if (messageText.startsWith("/order")) {
            // here we use the appropriate form template
            String form = messages.templates()
                    .newTemplateFromClasspath("/templates/order-form.ftl")
                    .process(Map.of()); // here we use an empty Map for now

            // and send back the form in the stream
            messages.send(message.getStream(), form);
        }
    }

    @EventListener
    public void onSymphonyElementsAction(
            RealTimeEvent<V4SymphonyElementsAction> event){

        // the handler will activate only if the form submission is regarding the "order" form
        if(event.getSource().getFormId().equals("order")){
            System.out.println("I am here!");
            // we assume that all the values coming from the form are String
            @SuppressWarnings("unchecked")
            Map<String, String> values = (Map<String, String>) event.getSource().getFormValues();

            // then we get the actual individual values
            String ticker = values.get("ticker").replace("$", "");
            int quantity = Integer.parseInt(values.get("quantity"));
            int price = Integer.parseInt(values.get("price"));

            // here we create a data object (in this case a simple Map)
            Map<String, Object> data = Map.of(
                    "ticker", ticker,
                    "quantity", quantity,
                    "price", price
            );

            // and the bot will send back a template message with cash tag
            String replyMessageTemplate = messages.templates()
                    .newTemplateFromClasspath("/templates/order-confirm.ftl")
                    .process(data); // here we pass the data object

            messages.send(event.getSource().getStream(), String.format(replyMessageTemplate, quantity, ticker, price));
        }

    }
}
