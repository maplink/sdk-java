package global.maplink.geocode.sync;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.geocode.GeocodeRequest;
import global.maplink.geocode.reverse.ReverseRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static global.maplink.geocode.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.geocode.common.Defaults.DEFAULT_SECRET;
import static global.maplink.geocode.reverse.ReverseRequest.entry;
import static global.maplink.geocode.schema.Type.ZIPCODE;
import static java.math.BigDecimal.ZERO;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
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
    public void mustDelegateAllGeocodeToAsync() {
        val async = mock(GeocodeAsyncAPI.class);
        when(async.geocode(any())).thenReturn(completedFuture(new SuggestionsResult()));
        val sync = new GeocodeSyncApiImpl(async);
        sync.geocode(GeocodeRequest.multi());
        sync.geocode(GeocodeRequest.ofDistrict(SOMETHING, SOMETHING, SOMETHING, SOMETHING));
        verify(async, times(1)).geocode(any(GeocodeRequest.Single.class));
        verify(async, times(1)).geocode(any(GeocodeRequest.Multi.class));
    }


    @Test
    public void mustDelegateAllSuggestionsToAsync() {
        val async = mock(GeocodeAsyncAPI.class);
        when(async.suggestions(any(String.class))).thenCallRealMethod();
        when(async.suggestions(any(), any())).thenCallRealMethod();
        when(async.suggestions(any(SuggestionsRequest.class))).thenReturn(completedFuture(new SuggestionsResult()));
        val sync = new GeocodeSyncApiImpl(async);
        sync.suggestions(SOMETHING);
        sync.suggestions(SOMETHING, ZIPCODE);
        sync.suggestions(SuggestionsRequest.builder()
                .query(SOMETHING)
                .build());
        verify(async, times(3)).suggestions(any(SuggestionsRequest.class));
    }


    @Test
    public void mustDelegateAllReverseToAsync() {
        val async = mock(GeocodeAsyncAPI.class);
        when(async.reverse(any(ReverseRequest.Entry[].class))).thenCallRealMethod();
        when(async.reverse(any(ReverseRequest.class))).thenReturn(completedFuture(new SuggestionsResult()));
        val sync = new GeocodeSyncApiImpl(async);
        sync.reverse(entry(1, 1), entry("teste", ZERO, ZERO));
        sync.reverse(ReverseRequest.builder()
                .entry(entry(1, 1))
                .entry(entry(ZERO, ZERO))
                .build()
        );
        verify(async, times(2)).reverse(any(ReverseRequest.class));
    }
}
