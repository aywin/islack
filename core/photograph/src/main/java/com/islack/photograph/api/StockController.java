package com.islack.photograph.api;

import com.islack.photograph.service.impl.StockImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
public class StockController {
    @Autowired
    private StockImageService stockImageService;

    @GetMapping("test/{category}/{page}")
    public ResponseEntity<String> test(@PathVariable("category") int category, @PathVariable("page") int page) {
        stockImageService.stockToDB(category, page);
        return new ResponseEntity<>("its done !", HttpStatus.OK);
    }

}
