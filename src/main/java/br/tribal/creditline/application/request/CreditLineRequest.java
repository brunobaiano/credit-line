package br.tribal.creditline.application.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditLineRequest {
    private String foundingType;
    private double cashBalance;
    private double monthlyRevenue;
    private double requestedCreditLine;
    private LocalDateTime requestedDate;
}
