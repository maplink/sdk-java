package global.maplink.commons;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(staticName = "of")
public class PageResult<T> {

	public final long total;
	public final List<T> results;
}