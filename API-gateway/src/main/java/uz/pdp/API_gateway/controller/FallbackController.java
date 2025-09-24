package uz.pdp.API_gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/studentFallback")
    public ResponseEntity<String> studentFallback() {
        return ResponseEntity.ok("❌ Student serviceda tamirlash ishlari olib borilmoqda. Iltimos keyinroq urinib ko‘ring.");
    }

    @GetMapping("/bookFallback")
    public ResponseEntity<String> bookFallback() {
        return ResponseEntity.ok("❌ Book serviceda tamirlash ishlari olib borilmoqda. Iltimos keyinroq urinib ko‘ring.");
    }
}