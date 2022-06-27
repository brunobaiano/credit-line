package br.tribal.creditline.domain.service;

import br.tribal.creditline.application.request.CreditLineRequest;
import br.tribal.creditline.application.response.CreditLineResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TribalCreditService implements CreditLineService {

    private final static double CASH_BALANCE_RATIO = 3;
    private final static double MONTHLY_REVENUE_RATIO = 5;

    @Override
    public CreditLineResponse applyNewCreditLine(CreditLineRequest creditLineRequest) {
        double cashBalanceRule = creditLineRequest.getCashBalance() / CASH_BALANCE_RATIO;
        double monthlyRevenueRule = creditLineRequest.getMonthlyRevenue() / MONTHLY_REVENUE_RATIO;

        double recommendedCreditLine = switch (creditLineRequest.getFoundingType()) {
            case "SME" -> monthlyRevenueRule;
            case "Startup" -> Math.max(cashBalanceRule, monthlyRevenueRule);
            default -> throw new IllegalArgumentException("founding type not acceptable");
        };

        if (recommendedCreditLine > creditLineRequest.getRequestedCreditLine()){
            log.info("Authorized for {}", creditLineRequest);
          return CreditLineResponse.builder()
                  .creditLine(creditLineRequest.getRequestedCreditLine())
                  .message("The credit line was authorized").build();
        }

        log.info("Not Authorized for {}", creditLineRequest);
        return CreditLineResponse.builder()
                .creditLine(-1)
                .message("The credit line was rejected").build();
    }
}
