package global.maplink.place.schema;

import global.maplink.commons.PageResult;

import java.util.List;

public class PlacePageResult extends PageResult<Place> {

    public PlacePageResult(int total, List<Place> results) {
        super(total, results);
    }
}
