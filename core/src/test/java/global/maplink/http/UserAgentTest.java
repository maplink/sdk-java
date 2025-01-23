package global.maplink.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserAgentTest {

    @Test
    void shouldWorkWithLoadDefault(){
        UserAgent agent = UserAgent.loadDefault();

        assertThat(agent).isNotNull();
        assertThat(agent.getHeaderContent()).isNotNull().doesNotContain("null");
    }

    @Test
    void shouldWorkWithLoadDefaultAndCustomDetails(){
        String customAgentString = "CustomAgent";
        UserAgent agent = UserAgent.loadDefault(customAgentString);

        assertThat(agent).isNotNull();
        assertThat(agent.getHeaderContent()).isNotNull().doesNotContain("null").contains(customAgentString);
    }

    @Test
    void shouldBuildHeaderContentWithFilledValues(){
        UserAgent agent = new UserAgent(
                "fake-agent",
                "11 (fake)",
                "1.0.0",
                "fake-linux"
        );

        assertThat(agent).isNotNull();
        assertThat(agent.getHeaderContent()).isEqualTo("Maplink SDK 1.0.0 Java 11 (fake) fake-linux fake-agent");
    }

    @Test
    void shouldBuildHeaderContentWithNullValues(){
        UserAgent agent = new UserAgent(
                null,
                null,
                null,
                null
        );

        assertThat(agent).isNotNull();
        assertThat(agent.getHeaderContent()).isEqualTo("Maplink SDK Java");
    }

}