import org.junit.Test;
import com.je.io.configuration.*;
import com.je.core.util.Bundle;
import com.je.core.JeLib;

public class IOTest {
	@Test
	public void store_config() {
		JeLib.console().log("Initializing configuration class...");
		Configuration.init("lib-io-test");

		JeLib.console().log("Creating and storing bundle...");
		Bundle bundle = Bundle.builder()
				.put("key-a",   "a")
				.put("key-2",   2)
				.put("key-3.14",3.14)
				.build();

		Configuration.storeBundle("bundle-a", bundle);

		JeLib.console().log("Loading configuration class...");
		Bundle lBundle = Configuration.loadBundle("bundle-a");

		JeLib.console().log("Loaded bundle: "+lBundle);
	}
}