package com.sanduhr.main.commands.pub;
public class Time extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceiveEvent e) {
        //Never respond to a bot
        if (e.getAurhor().isBot()) {
            return;
        }
        String content = e.getMessage().getContent();
        //If the commamd isn't time
        if (!content.equalsIgnoreCase(Lib.PREFIX + "time")) {
            return;
        }
        Lib.receivedcmd++;
        OffsetDateTime now = OffsetDateTime.now();
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("");
        e.getChannel.sendMessage(now.format(DTF)).queue();
    }
    publix void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA, e.getReponseNumber, e.getMessage));
    }
    public void onReady(ReadyEvent e) {
        initter();
    }
    public void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    public String getName() {
        return Time.class.getSimpleName().toLowerCase();
    }
    public String getDescription() {
        return "Returns the current time (german time)";
    }
    public String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}
