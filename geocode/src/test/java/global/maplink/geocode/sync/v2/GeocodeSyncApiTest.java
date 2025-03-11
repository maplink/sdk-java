package global.maplink.geocode.sync.v2;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.geocode.async.v2.GeocodeAsyncAPI;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v2.reverse.ReverseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static global.maplink.geocode.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.geocode.common.Defaults.DEFAULT_SECRET;
import static global.maplink.geocode.schema.v1.Type.ZIPCODE;
import static global.maplink.geocode.schema.v2.reverse.ReverseRequest.entry;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GeocodeSyncApiTest {

    public static final String SOMETHING = "something";

    @BeforeEach
    public void initializeSDK() {
        MapLinkSDK
                .configure()
                .with(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET))
                .initialize();
    }

    @AfterEach
    public void cleanupSDK() {
        MapLinkSDK.resetConfiguration();
    }

    @Test
    public void mustBeInstantiableWithGetInstance() {
        GeocodeSyncAPI instance = GeocodeSyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }

    @Test
    public void mustDelegateAllSuggestionsToAsync() {
        val async = mock(GeocodeAsyncAPI.class);
        when(async.suggestions(any(String.class))).thenCallRealMethod();
        when(async.suggestions(any(), any())).thenCallRealMethod();
        when(async.suggestions(any(SuggestionsBaseRequest.class))).thenReturn(completedFuture(new SuggestionsResult()));
        val sync = new GeocodeSyncApiImpl(async);
        val result1 = sync.suggestions(SOMETHING);
        val result2 = sync.suggestions(SOMETHING, ZIPCODE);
        val result3 = sync.suggestions(SuggestionsBaseRequest.builder()
                .query(SOMETHING)
                .build());
        assertThat(asList(result1, result2, result3)).doesNotContainNull();
        verify(async, times(3)).suggestions(any(SuggestionsBaseRequest.class));
    }


    @Test
    public void mustDelegateAllReverseToAsync() {
        val async = mock(GeocodeAsyncAPI.class);
        when(async.reverse(any(ReverseRequest.Entry[].class))).thenCallRealMethod();
        when(async.reverse(anyList())).thenCallRealMethod();
        when(async.reverse(any(ReverseRequest.class))).thenReturn(completedFuture(new SuggestionsResult()));
        val sync = new GeocodeSyncApiImpl(async);
        val result1 = sync.reverse(entry(1, 1), entry("teste", ZERO, ZERO));
        val result2 = sync.reverse(ReverseRequest.builder()
                .entry(entry(1, 1))
                .entry(entry(ZERO, ZERO))
                .build()
        );
        assertThat(asList(result1, result2)).doesNotContainNull();
        verify(async, times(2)).reverse(any(ReverseRequest.class));
    }


}
