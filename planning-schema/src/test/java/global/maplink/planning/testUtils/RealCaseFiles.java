package global.maplink.planning.testUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RealCaseFiles implements TestFiles {

    PLANNING_CARGA_INTELIGENTE("planning.json/realCases/planningCargaInteligente.json"),
    PLANNING_EXEMPLO_TESTE("planning.json/realCases/planningExemploTeste.json"),
    PLANNING_TESTE_COLLECTION_DELIVERY("planning.json/realCases/planningTesteCollectionDelivery.json"),
    REQUEST_BOTICARIO("planning.json/realCases/requestBoticario.json"),
    REQUEST_LOGISTIC_PLANNING("planning.json/realCases/requestLogisticPlanning.json");

    private final String filePath;
}
