package com.parkking.parkingservice.utils;

import com.parkking.parkingservice.dto.auth.TwoFactorResponse;
import com.parkking.parkingservice.exception.BadRequestException;
import com.parkking.parkingservice.exception.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Component
public class DefaultSmsStrategy implements SmsStrategy {

    @Value("${2factor.sms.apiKey}")
    private String apiKey;

    @Override
    public TwoFactorResponse makeSmsCall(String phoneNo, String otp){

        HttpClient httpClient = HttpClient.create()
                .resolver(spec -> spec.queryTimeout(Duration.ofSeconds(10)));

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://2factor.in/API/V1/" +apiKey)
                .build();
        Mono<TwoFactorResponse> twoFactorResponse = webClient.get()
                .uri(String.format("/SMS/%s/%s/SEND_OTP",phoneNo,otp))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(TwoFactorResponse.class);
                    } else if (response.statusCode().is4xxClientError()) {
                        throw new BadRequestException("4xx error while calling 2factor");
                    } else {
                        throw new InternalServerErrorException("5xx error while calling 2factor");
                    }
                });
        return twoFactorResponse.block();

    }
}
