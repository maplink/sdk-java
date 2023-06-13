package global.maplink.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
public enum TransponderOperator {
    SEM_PARAR(Arrays.asList("Via Facil", "SemPararPay")),
    CONECTCAR(Arrays.asList("Conectcar"));

    private final List<String> tags;
}
