package com.islack.photograph.service;

import com.islack.photograph.domain.dto.vision.AnalyzeRequestDto;
import com.islack.photograph.domain.dto.vision.AnalyzedPhotographDto;
import feign.Headers;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Headers("Content-type: application/json")
@FeignClient(name = "vision", url = "https://westeurope.api.cognitive.microsoft.com/vision/v1.0")
public interface ComputerVisionClient {

    @PostMapping("/analyze?visualFeatures=Description,ImageType,Color,Categories")
    AnalyzedPhotographDto analyze(@RequestHeader("Ocp-Apim-Subscription-Key") String key, @RequestBody AnalyzeRequestDto analyzeRequestDto);

}
