package org.example.developer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@RestController
public class TtsApiController {

    @Value("${naver.tts.client-id}")
    private String clientId;

    @Value("${naver.tts.client-secret}")
    private String clientSecret;

    private static final String TTS_API_URL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";

    @GetMapping("/tts")
    public ResponseEntity<?> convertTextToSpeech(@RequestParam String text) throws IOException {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
            headers.set("X-NCP-APIGW-API-KEY", clientSecret);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("speaker", "nara");
            params.add("speed", "0");
            params.add("text", text);

            RequestEntity<MultiValueMap<String, String>> request = RequestEntity
                    .post(URI.create(TTS_API_URL))
                    .headers(headers)
                    .body(params);

            ResponseEntity<byte[]> response = restTemplate.exchange(request, byte[].class);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseHeaders.set("Content-Disposition", "attachment; filename=\"speech.mp3\"");

            return new ResponseEntity<>(response.getBody(), responseHeaders, HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request: " + ex.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}

