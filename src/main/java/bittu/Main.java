package bittu;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {
	public static void main(String[] args) throws LoginException, InterruptedException {
		// load JNI library for music decoding.
		var bot = JDABuilder
				.createDefault(args[0])
				.setBulkDeleteSplittingEnabled(false)
				.setMemberCachePolicy(MemberCachePolicy.VOICE)
				.addEventListeners(new Bot())
				.build();
		
		bot.awaitReady();
		
		// for the testing period let it be Trilounge.
		bot.getGuildById("678263205562286112")
			.updateCommands()
			.addCommands(
					new CommandData("join", "Summon in your current VC"),
					new CommandData("play", "Play an URL")
						.addOption(OptionType.STRING, "url", "URL to be played", true),
					new CommandData("leave", "Leave from your current VC")
			).queue();
	}
}