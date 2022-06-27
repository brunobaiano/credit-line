package br.tribal.creditline.domain.service;

import br.tribal.creditline.application.request.CreditLineRequest;
import br.tribal.creditline.application.response.CreditLineResponse;

public interface CreditLineService {
    CreditLineResponse applyNewCreditLine(CreditLineRequest creditLineRequest);
}
