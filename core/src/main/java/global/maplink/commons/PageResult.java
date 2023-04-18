package global.maplink.commons;

import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;
@RequiredArgsConstructor
public class PageResult<T> {

    public final long total;
    public final List<T> results;

    public PageResult() {
        this(0, emptyList());
    }

    public static <T> PageResult<T> of(long total, List<T> results) {
        return new PageResult<>(total, results);
    }
}