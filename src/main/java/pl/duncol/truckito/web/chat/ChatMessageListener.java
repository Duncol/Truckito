package pl.duncol.truckito.web.chat;

import pl.duncol.truckito.valueobjects.chat.ChatMessage;

public interface ChatMessageListener {
    void receiveBroadcast(ChatMessage message);
//    void addBroadcastAction(Runnable action);
}