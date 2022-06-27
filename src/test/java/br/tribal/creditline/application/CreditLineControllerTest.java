package br.tribal.creditline.application;


import br.tribal.creditline.application.request.CreditLineRequest;
import br.tribal.creditline.domain.service.CreditLineService;
import br.tribal.creditline.domain.service.TribalCreditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class CreditLineControllerTest {

    @Mock
    CreditLineService creditLineService;

    @InjectMocks
    CreditLineController creditLineController;

    private final ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    @Test
    public void testTooManyRequests() throws Exception {
       String content = objectMapper.writeValueAsString(CreditLineRequest.builder()
                .foundingType("SME")
                .cashBalance(435.30)
                .monthlyRevenue(4235.45)
                .requestedCreditLine(1000)
                .requestedDate(LocalDateTime.of(2021, 7, 19, 16, 32, 59, 860))
                .build());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/credit-line")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(this.creditLineController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        result ->  MockMvcBuilders.standaloneSetup(this.creditLineController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                )
                .andDo(   result ->  MockMvcBuilders.standaloneSetup(this.creditLineController)
                        .build()
                        .perform(requestBuilder)
                        .andExpect(MockMvcResultMatchers.status().isTooManyRequests()));

    }


}
