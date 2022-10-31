package global.maplink.toll.schema;

import gloabl.maplink.toll.schema.TollServiceType;
import global.maplink.json.JsonMapper;
import global.maplink.toll.testUtils.SampleFiles;
import org.junit.jupiter.api.Test;

import static global.maplink.toll.testUtils.SampleFiles.TOLL_SERVICE_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TollServiceTypeTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        TollServiceType tollServiceType = mapper.fromJson(TOLL_SERVICE_TYPE.load(), TollServiceType.class);
        assertEquals("236e9cd5-4181-408c-b90f-a24c31237f11", tollServiceType.getServiceId());
        assertEquals("MAPLINK", tollServiceType.getName());
    }
}
