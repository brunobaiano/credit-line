package br.tribal.creditline.domain.service;

import br.tribal.creditline.application.request.CreditLineRequest;
import br.tribal.creditline.application.response.CreditLineResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TribalCreditServiceTest {

    @InjectMocks
    TribalCreditService tribalCreditService;

    @ParameterizedTest
    @MethodSource("provideRequestsForNewCredit")
    @DisplayName("Apply for a new credit with a lot of examples")
    void testApplyNewCredit(CreditLineRequest creditLineRequest, CreditLineResponse creditLineResponse) {
        Assertions.assertEquals(creditLineResponse, tribalCreditService.applyNewCreditLine(creditLineRequest));
    }

    @Test
    @DisplayName("Apply with a different founding type")
    void testApplyNewCreditWithDifferentFoundingType(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tribalCreditService.applyNewCreditLine(CreditLineRequest.builder()
                    .foundingType("LTDA")
                    .cashBalance(435.30)
                    .monthlyRevenue(4235.45)
                    .requestedCreditLine(100)
                    .requestedDate(LocalDateTime.of(2021, 7, 19, 16, 32, 59, 860))
                    .build());
        });

        Assertions.assertTrue(exception.getMessage().contains("founding type not acceptable"));
    }


    private static Stream<Arguments> provideRequestsForNewCredit() {
        return Stream.of(
                Arguments.of(CreditLineRequest.builder()
                                .foundingType("SME")
                                .cashBalance(435.30)
                                .monthlyRevenue(4235.45)
                                .requestedCreditLine(100)
                                .requestedDate(LocalDateTime.of(2021, 7, 19, 16, 32, 59, 860))
                                .build(),
                        CreditLineResponse.builder().creditLine(100).message("The credit line was authorized").build()),
                Arguments.of(CreditLineRequest.builder()
                                .foundingType("SME")
                                .cashBalance(435.30)
                                .monthlyRevenue(4235.45)
                                .requestedCreditLine(1000)
                                .requestedDate(LocalDateTime.of(2021, 7, 19, 16, 32, 59, 860))
                                .build(),
                        CreditLineResponse.builder().creditLine(-1).message("The credit line was rejected").build()),
                Arguments.of(CreditLineRequest.builder()
                                .foundingType("Startup")
                                .cashBalance(435.30)
                                .monthlyRevenue(4235.45)
                                .requestedCreditLine(100)
                                .requestedDate(LocalDateTime.of(2021, 7, 19, 16, 32, 59, 860))
                                .build(),
                        CreditLineResponse.builder().creditLine(100).message("The credit line was authorized").build()),
                Arguments.of(CreditLineRequest.builder()
                                .foundingType("Startup")
                                .cashBalance(435.30)
                                .monthlyRevenue(4235.45)
                                .requestedCreditLine(1000)
                                .requestedDate(LocalDateTime.of(2021, 7, 19, 16, 32, 59, 860))
                                .build(),
                        CreditLineResponse.builder().creditLine(-1).message("The credit line was rejected").build()),
                Arguments.of(CreditLineRequest.builder()
                                .foundingType("Startup")
                                .cashBalance(435.30)
                                .monthlyRevenue(4235.45)
                                .requestedCreditLine(847.09)
                                .requestedDate(LocalDateTime.of(2021, 7, 19, 16, 32, 59, 860))
                                .build(),
                        CreditLineResponse.builder().creditLine(-1).message("The credit line was rejected").build()),
                Arguments.of(CreditLineRequest.builder()
                                .foundingType("Startup")
                                .cashBalance(435.30)
                                .monthlyRevenue(4235.45)
                                .requestedCreditLine(847.08)
                                .requestedDate(LocalDateTime.of(2021, 7, 19, 16, 32, 59, 860))
                                .build(),
                        CreditLineResponse.builder().creditLine(847.08).message("The credit line was authorized").build())
        );

    }

}
