package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class LogisticZoneType {

	public static final String PRIORITARY = "PRIORITARY";
	public static final String SECUNDARY = "SECUNDARY";

}
