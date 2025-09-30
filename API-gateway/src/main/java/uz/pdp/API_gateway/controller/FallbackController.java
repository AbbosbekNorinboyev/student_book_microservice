package uz.pdp.API_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
    @GetMapping("/fallback/students")
    public Mono<String> studentFallback() {
        return Mono.just("❌ Student serviceda tamirlash ishlari olib borilmoqda. Iltimos keyinroq urinib ko‘ring.");
    }

    @GetMapping("/fallback/books")
    public Mono<String> bookFallback() {
        return Mono.just("❌ Book serviceda tamirlash ishlari olib borilmoqda. Iltimos keyinroq urinib ko‘ring.");
    }
}
