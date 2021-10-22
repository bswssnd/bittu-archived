package bittu;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import bittu.exceptions.CorruptMedia;
import bittu.exceptions.InternalPlayerError;
import bittu.exceptions.NoAudioStreams;
import bittu.jni.AudioPlayerJNI;
import net.dv8tion.jda.api.audio.AudioSendHandler;

public class AudioPlayerWrapper implements AudioSendHandler {
	private ByteBuffer buffer;
	private AudioPlayerJNI player;
	private boolean providing;
	private boolean isPlayerInit;

	public AudioPlayerWrapper() {
		this.buffer = ByteBuffer.allocate(AudioPlayerJNI.AUDIO_BUFFER_MAX_SIZE);
		this.player = new AudioPlayerJNI();
		
		this.buffer.order(ByteOrder.nativeOrder());
		this.isPlayerInit = false;
	}
	
	public void stream(String url) throws NoAudioStreams, InternalPlayerError, CorruptMedia {
		if(this.isPlayerInit)
			this.player.uninit();
		this.player.init();
		
		this.isPlayerInit = true;
		
		switch(this.player.stream(url)) {
			case AudioPlayerJNI.MEDIA_INAUDIBLE:
				throw new NoAudioStreams();
			case AudioPlayerJNI.MEDIA_INVALID:
				throw new CorruptMedia();
			case AudioPlayerJNI.MEDIA_INTERNAL_ERROR:
				throw new InternalPlayerError();
		}
		
		this.providing = true;
	}

	@Override
	public boolean canProvide() {
		return providing;
	}

	private static void toBigEndian(ByteBuffer buffer) {
		int limit = buffer.limit();
		
		if(buffer.order() != ByteOrder.BIG_ENDIAN) {
			for(int i = 0; i < limit; i+=2) {
				buffer.put(i, buffer.get(i+1));
				buffer.put(i+1, buffer.get(i));
			}
		}
	}
	
	@Override
	public ByteBuffer provide20MsAudio() {
		int ret = this.player.get20ms(this.buffer.array());
		
		if(ret > -1)
			this.buffer.limit(ret);
		else {
			this.providing = false;
			
			this.buffer.limit(0);
			this.player.uninit();
			this.isPlayerInit = false;
		}
		
		toBigEndian(this.buffer);
		
		return this.buffer;
	}
}