package br.tribal.creditline.application.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreditLineResponse {
    private double creditLine;
    private String message;
}
