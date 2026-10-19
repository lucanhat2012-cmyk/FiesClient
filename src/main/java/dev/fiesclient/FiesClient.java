
package dev.fiesclient;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FiesClient implements ModInitializer {
    public static final String MOD_ID = "fiesclient";
    public static final Logger LOGGER =
            LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("FiesClient initialized!");
    }
}
