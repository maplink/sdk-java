package global.maplink.commons;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PageResult<T> {

    public final long total;
    public final List<T> results;

    public static <T> PageResult<T> of(long total, List<T> results) {
        return new PageResult<>(total, results);
    }
}