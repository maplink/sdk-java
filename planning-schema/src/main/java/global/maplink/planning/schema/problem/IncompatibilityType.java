package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class IncompatibilityType {
	public static final String ORDERED = "ORDERED";
	public static final String STRICT = "STRICT";
}

