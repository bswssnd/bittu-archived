package bittu;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Bot extends ListenerAdapter {
	@Override
	public void onSlashCommand(SlashCommandEvent event) {
		switch(event.getCommandPath()) {
		case "join" -> { 
			var vc = event.getMember().getVoiceState().getChannel();
			var manager = event.getGuild().getAudioManager();
			
			manager.openAudioConnection(vc);
			manager.setSelfDeafened(true);
			manager.setSendingHandler(new AudioPlayer());
			
			event.reply("Joined " + vc.getAsMention()).queue();
		}
		
		case "leave" -> {
			var vc = event.getMember().getVoiceState().getChannel();
			
			event.getGuild().getAudioManager().closeAudioConnection();
			event.reply("Left " + vc.getAsMention()).queue();
		}
		}
	}
}
