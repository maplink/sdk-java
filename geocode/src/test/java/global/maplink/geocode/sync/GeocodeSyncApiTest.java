package global.maplink.geocode.sync;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.schema.cities.CitiesByStateRequest;
import global.maplink.geocode.schema.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.structured.StructuredRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static global.maplink.geocode.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.geocode.common.Defaults.DEFAULT_SECRET;
import static global.maplink.geocode.schema.Type.ZIPCODE;
import static global.maplink.geocode.schema.crossCities.CrossCitiesRequest.point;
import static global.maplink.geocode.schema.reverse.ReverseRequest.entry;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
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
        when(async.structured(any())).thenReturn(completedFuture(new SuggestionsResult()));
        val sync = new GeocodeSyncApiImpl(async);
        sync.structured(StructuredRequest.multi());
        sync.structured(StructuredRequest.ofDistrict(SOMETHING, SOMETHING, SOMETHING, SOMETHING));
        verify(async, times(1)).structured(any(StructuredRequest.Single.class));
        verify(async, times(1)).structured(any(StructuredRequest.Multi.class));
    }

    @Test
    public void mustDelegateAllSuggestionsToAsync() {
        val async = mock(GeocodeAsyncAPI.class);
        when(async.suggestions(any(String.class))).thenCallRealMethod();
        when(async.suggestions(any(), any())).thenCallRealMethod();
        when(async.suggestions(any(SuggestionsRequest.class))).thenReturn(completedFuture(new SuggestionsResult()));
        val sync = new GeocodeSyncApiImpl(async);
        val result1 = sync.suggestions(SOMETHING);
        val result2 = sync.suggestions(SOMETHING, ZIPCODE);
        val result3 = sync.suggestions(SuggestionsRequest.builder()
                .query(SOMETHING)
                .build());
        assertThat(asList(result1, result2, result3)).doesNotContainNull();
        verify(async, times(3)).suggestions(any(SuggestionsRequest.class));
    }

    @Test
    public void mustDelegateAllCitiesByStateToAsync() {
        val async = mock(GeocodeAsyncAPI.class);
        when(async.citiesByState(any(String.class))).thenCallRealMethod();
        when(async.citiesByState(any(CitiesByStateRequest.class))).thenReturn(completedFuture(new SuggestionsResult()));
        val sync = new GeocodeSyncApiImpl(async);
        val result1 = sync.citiesByState("SC");
        val result2 = sync.citiesByState(CitiesByStateRequest.builder()
                .state("SC")
                .build());
        assertThat(result1).doesNotContainNull();
        assertThat(result2).doesNotContainNull();
        verify(async, times(2)).citiesByState(any(CitiesByStateRequest.class));
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

    @Test
    public void mustDelegateAllCrossCitiesToAsync() {
        val async = mock(GeocodeAsyncAPI.class);
        when(async.crossCities(any(CrossCitiesRequest.Point[].class))).thenCallRealMethod();
        when(async.crossCities(anyList())).thenCallRealMethod();
        when(async.crossCities(any(CrossCitiesRequest.class))).thenReturn(completedFuture(new SuggestionsResult()));
        val sync = new GeocodeSyncApiImpl(async);
        val result1 = sync.crossCities(point(1, 1), point(ZERO, ZERO));
        val result2 = sync.crossCities(CrossCitiesRequest.builder()
                .point(point(1, 1))
                .point(point(ZERO, ZERO))
                .build()
        );
        assertThat(asList(result1, result2)).doesNotContainNull();
        verify(async, times(2)).crossCities(any(CrossCitiesRequest.class));
    }
}
