package global.maplink.trip.schema.v2.features.crossedBorders;

import global.maplink.geocode.schema.Address;

public enum CrossedBorderLevel {
    NONE{
        @Override
        CrossedBorderResponse fromAddress(Address address) {
            return null;
        }
    },
	CITY {
        CrossedBorderResponse fromAddress(Address address) {
			return CrossedBorderResponse.builder()
					.city(address.getCity())
					.state(address.getState().getCode())
					.country(address.getCountry())
					.build();
		}
	},
	STATE {
        CrossedBorderResponse fromAddress(Address address) {
			return CrossedBorderResponse.builder()
					.state(address.getState().getCode())
					.country(address.getCountry())
					.build();
		}
	};

	abstract CrossedBorderResponse fromAddress(Address address);
}
