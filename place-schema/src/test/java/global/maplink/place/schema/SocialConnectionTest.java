package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.place.testUtils.SampleFiles.SOCIALCONNECTION;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocialConnectionTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        SocialConnection socialConnection = mapper.fromJson(SOCIALCONNECTION.load(), SocialConnection.class);
        assertEquals("facebook", socialConnection.getProvider());
        assertEquals("https://www.facebook.com/maplink", socialConnection.getUrl());
    }
}
