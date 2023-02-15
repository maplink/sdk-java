package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static global.maplink.place.schema.PaymentMethod.*;
import static global.maplink.place.testUtils.SampleFiles.PLACE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class PlaceTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize() {
        Place place = mapper.fromJson(PLACE.load(), Place.class);
        assertEquals("a98553bc-78e5-4035-a048-74e361625ce1", place.getId());
        assertEquals("MAPLINK", place.getName());
        assertEquals("95.424.764/0001-10", place.getDocumentNumber());
        assertEquals("Maplink Brasil", place.getDescription());
        assertTrue(StringUtils.isBlank(place.getUrl()));
        assertEquals("http://maplink.global", place.getWebsite());
        assertTrue(StringUtils.isBlank(place.getFavicon()));
        assertEquals(4, place.getPayments().size());
        assertTrue(place.getPayments().containsAll(Arrays.asList(BOLETO_BANCARIO, DINHEIRO, MASTERCARD, VISA)));
        assertEquals(Category.TECNOLOGIA, place.getCategory());
        assertEquals(SubCategory.DESENVOLVIMENTO_DE_SOFTWARE, place.getSubCategory());
        assertEquals(2, place.getPhones().size());
        assertTrue(place.getPhones().contains("(11) 2222-3333"));
        assertTrue(place.getPhones().contains("(11) 4444-5555"));
        assertNull(place.getAdditionalInfo());
        assertTrue(place.isActive());
        assertEquals("maplinkClientId", place.getClientId());

        assertEquals(2, place.getConnections().size());
        SocialConnection facebook = SocialConnection.builder().provider("facebook")
                .url("https://www.facebook.com/maplink").build();
        SocialConnection instagram = SocialConnection.builder().provider("instagram")
                .url("https://br.linkedin.com/company/maplink").build();
        assertTrue(place.getConnections().contains(facebook));
        assertTrue(place.getConnections().contains(instagram));

        assertNotNull(place.getOpeningHours());
        assertEquals(5, place.getOpeningHours().getPeriods().size());
        List<OpenHour> periods = place.getOpeningHours().getPeriods();
        periods.forEach(p -> assertEquals("09:00", p.getOpen().getTime()));
        periods.forEach(p -> assertEquals("18:00", p.getClose().getTime()));
        List<DayOfWeek> daysOfWeek = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        assertTrue(periods.stream().map(p -> p.getOpen().getDay()).collect(Collectors.toList())
                .containsAll(daysOfWeek));
        assertTrue(periods.stream().map(p -> p.getClose().getDay()).collect(Collectors.toList())
                .containsAll(daysOfWeek));

        assertNotNull(place.getAddress());
        assertEquals("Alameda Campinas", place.getAddress().getStreet());
        assertEquals("579", place.getAddress().getNumber());
        assertEquals("Jardim Paulista", place.getAddress().getDistrict());
        assertEquals("São Paulo", place.getAddress().getCity());
        assertEquals("SP", place.getAddress().getState());
        assertEquals("01404-100", place.getAddress().getZipcode());
        assertEquals("9th floor", place.getAddress().getComplement());
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(place.getAddress().getPoint().getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(place.getAddress().getPoint().getLongitude()));
        assertThat(place.validate()).isEmpty();
        place.throwIfInvalid();
    }

    @Test
    void shouldValidate() {
        val place = Place.builder().build();
        assertThat(place.validate()).isNotEmpty().hasSize(5);

        assertThatThrownBy(place::throwIfInvalid).isInstanceOf(ValidationException.class);
    }
}
