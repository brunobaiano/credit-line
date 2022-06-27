package br.tribal.creditline.application;

import br.tribal.creditline.application.request.CreditLineRequest;
import br.tribal.creditline.application.response.CreditLineResponse;
import br.tribal.creditline.domain.service.CreditLineService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/credit-line")
public class CreditLineController {

    private final Bucket bucket;

    private final CreditLineService creditLineService;

    public CreditLineController(CreditLineService creditLineService) {
        this.bucket = Bucket.builder()
                .addLimit(Bandwidth.classic(2, Refill.greedy(2, Duration.ofMinutes(2))))
                .build();

        this.creditLineService = creditLineService;
    }

    @Operation(summary = "Return if the credit line will be approved or not")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "The credit line was authorized or not",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditLineResponse.class))}),
            @ApiResponse(responseCode = "429", description = "Too many Requests", content = @Content)})
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditLineResponse> applyNewCreditLine(@RequestBody CreditLineRequest creditLineRequest) {
        CreditLineResponse creditLineResponse = creditLineService.applyNewCreditLine(creditLineRequest);
        if (bucket.tryConsume(1))
            return ResponseEntity.ok(creditLineResponse);

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
