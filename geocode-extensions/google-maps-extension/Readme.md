# Geocode SDK - Google Maps Extension

This SDK extensions aims to provide Google Maps interaction with MapLink Geocode API.

To enable this extension we need to **add dependency**.

Maven:

    <dependency>
        <groupId>global.maplink</groupId>
        <artifactId>sdk-geocode-google-maps-extension</artifactId>
        <version>${sdk.version}</version>
    </dependency>

Gradle (Groovy DSL):

    implementation 'global.maplink:sdk-geocode-google-maps-extension:$sdkVersion'

Gradle (Kotlin DSL):

    implementation("global.maplink:sdk-geocode-google-maps-extension:$sdkVersion")


After that we need to define Google Maps API key environment variable, this is the default way to parametrize this.
Define environment variable `GOOGLE_MAPS_KEY` with your valid key.

Then at SDK initialization add this module:
    
    MapLinkSDK.configure()
        .with(GeocodeGMapsExtensionCatalog()) 

With this you add all extensions available in this module.

## Suggestions

In default configuration this extension, available on catalog, will call Google Maps API if
 the best `score` present on MapLink Geocode API result is bellow 70. 

This switch do not change the result, being transparent, 
then Google Maps result will be transformed to `SuggestionResponse`.
