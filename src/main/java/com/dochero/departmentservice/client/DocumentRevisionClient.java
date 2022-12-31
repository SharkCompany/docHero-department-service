package com.dochero.departmentservice.client;

import com.dochero.departmentservice.client.dto.DocumentRevision;
import com.dochero.departmentservice.client.dto.UpdateRevisionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "DOCUMENT-REVISION-SERVICE", path = "/document-revision")
public interface DocumentRevisionClient {
    @GetMapping("/test")
    String testService();

    @PutMapping("/document/{documentId}/initial-document")
    DocumentRevision createBlankRevision(@PathVariable("documentId") String documentId);

    @GetMapping("/document/{documentId}")
    List<DocumentRevision> getAllRevisionsByDocumentId(@PathVariable("documentId") String documentId);

    @PutMapping("/document/{documentId}/save-document")
    DocumentRevision createRevisionForExistedDocument(@PathVariable("documentId") String documentId, @RequestBody UpdateRevisionRequest updateRevisionRequest);

    @PutMapping("/{documentRevisionId}/document/{documentId}/revert-revision")
    DocumentRevision revertToDocumentRevision(@PathVariable("documentId") String documentId,@PathVariable("documentRevisionId") String revisionId);

}
