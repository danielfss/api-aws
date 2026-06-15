package br.com.danielfss.api_aws.dynamo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielfss.api_aws.dynamo.service.DynamoService;

@RestController
@RequestMapping("/dynamodb")
public class DynamoController {

    private final DynamoService service;

    public DynamoController(DynamoService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestParam String id,
            @RequestParam String nome
    ) {
        return ResponseEntity.ok(service.save(id, nome));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Map<String, String>> find(@PathVariable String id) {
        return ResponseEntity.ok(service.find(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(
            @RequestParam String id,
            @RequestParam String nome
    ) {
        return ResponseEntity.ok(service.update(id, nome));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/query/{id}")
    public ResponseEntity<List<Map<String, String>>> query(@PathVariable String id) {
        return ResponseEntity.ok(service.query(id));
    }

    @GetMapping("/scan")
    public ResponseEntity<List<Map<String, String>>> scan() {
        return ResponseEntity.ok(service.scan());
    }

    @PostMapping("/batch-get")
    public ResponseEntity<List<Map<String, String>>> batchGet(@RequestBody List<String> ids) {
        return ResponseEntity.ok(service.batchGet(ids));
    }

    @PostMapping("/batch-write")
    public ResponseEntity<String> batchWrite(@RequestBody List<Map<String, String>> itens) {
        return ResponseEntity.ok(service.batchWrite(itens));
    }
}