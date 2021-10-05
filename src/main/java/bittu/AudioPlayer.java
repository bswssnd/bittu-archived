package bittu;

import java.nio.ByteBuffer;


import net.dv8tion.jda.api.audio.AudioSendHandler;

public class AudioPlayer implements AudioSendHandler {
	private ByteBuffer buffer;

	public AudioPlayer() {
		this.buffer = ByteBuffer.allocate(INPUT_FORMAT.getFrameSize());
		
	}

	@Override
	public boolean canProvide() {
		return true;
	}

	@Override
	public ByteBuffer provide20MsAudio() {
		return this.buffer;
	}
}