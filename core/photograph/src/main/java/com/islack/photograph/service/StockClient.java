package com.islack.photograph.service;

import com.islack.photograph.domain.StockPhotoHits;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stock", url = "https://pixabay.com")
public interface StockClient {
    @GetMapping("/api/?key=9001302-e16401fe3c3b7c38ea8a2ba92&q={c}&page={p}")
    StockPhotoHits getHits(@PathVariable("c") String c, @PathVariable("p") int p);
}

