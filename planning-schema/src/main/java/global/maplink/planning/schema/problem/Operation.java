package global.maplink.planning.schema.problem;

import global.maplink.freight.schema.OperationType;
import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.planning.schema.validator.FieldValidator;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Operation {

    private final String id;
    private final String group;
    private final Double weight;
    private final Double volume;
    private final Double quantity;
    private final String product;
    private final OperationType type;
    private final Integer priority;
    private final String depotSize;
    private final String customerSite;
    @Singular
    private final List<TimeWindow> customerTimeWindows;
    @Singular
    private final List<TimeWindow> depotTimeWindows;
    private final String preAllocatedVehicleName;
    private final Integer depotHandlingDuration;
    private final OperationStatus status;
    private final Boolean depotTimeWindowBlocked;
    private final Boolean customerTimeWindowBlocked;
    private final List<String> characteristics;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(FieldValidator.isInvalid(id)){
            violations.add(PlanningUpdateViolation.of("operation.id"));
        }

        if(FieldValidator.isInvalid(product)){
            violations.add(PlanningUpdateViolation.of("operation.product"));
        }

        if(FieldValidator.isInvalid(depotSize)){
            violations.add(PlanningUpdateViolation.of("operation.depotSize"));
        }

        if(FieldValidator.isInvalid(customerSite)){
            violations.add(PlanningUpdateViolation.of("operation.customerSite"));
        }

        if(isNull(type)){
            violations.add(PlanningUpdateViolation.of("operation.type"));
        }

        if(isNull(weight)){
            violations.add(PlanningUpdateViolation.of("operation.weight"));
        }

        if(isNull(volume)){
            violations.add(PlanningUpdateViolation.of("operation.volume"));
        }

        if(!FieldValidator.isNotNegative(weight)){
            violations.add(PlanningUpdateViolation.of("operation.weight"));
        }

        if(!FieldValidator.isNotNegative(volume)){
            violations.add(PlanningUpdateViolation.of("operation.volume"));
        }

        if(!FieldValidator.isNotNegative(priority)){
            violations.add(PlanningUpdateViolation.of("operation.priority"));
        }

        if(FieldValidator.isEmpty(customerTimeWindows)){
            violations.add(PlanningUpdateViolation.of("operation.customerTimeWindows"));
        }

        return violations;
    }
}
