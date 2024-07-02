package com.armydev.tasleehbackend.guaranteeletter.files;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("guaranteeLetterUploads")
@AllArgsConstructor
public class GuaranteeUploadsController {

  private final GuaranteeLetterFilesRepo filesRepo;
  private final Path rootLocation = Paths.get("guaranteeLetterUploads");

  @GetMapping(path = "{id}/{filename}")
  public ResponseEntity<Resource> serveGuaranteeFile(@PathVariable("id") Integer id,

      @PathVariable("filename") String fileName) {
    var file = new File(this.rootLocation.resolve(Paths.get(Integer.toString(id),
        fileName)).normalize().toString());
    try {
      if (file.exists()) {
        Resource resource = new FileSystemResource(file);
        System.out.println(id);
        System.out.println(fileName);
        var fileEntry = filesRepo.findByGuaranteeLetterIdAndFileName(id, fileName);
        var contentType = Files.probeContentType(resource.getFile().toPath());
        if (contentType == null) {
          contentType = "application/octet-stream";
        }
        System.out.println(fileEntry);
        if (fileEntry == null) {
          throw new Exception("File entry doesn't exist");
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(
            resource);

      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping(path = "{id}/{filename}")
  public ResponseEntity<Map<String, Object>> deleteAnnualFile(@PathVariable("id") Integer id,

      @PathVariable("filename") String fileName) {
    var result = new HashMap<String, Object>();
    var file = new File(this.rootLocation.resolve(Paths.get(Integer.toString(id),
        fileName)).normalize().toString());
    System.out.println("id: " + id);
    System.out.println("fileName: " + fileName);
    try {
      if (file.exists()) {
        var fileEntry = filesRepo.findByGuaranteeLetterIdAndFileName(id, fileName);
        System.out.println(fileEntry);
        if (fileEntry == null) {
          throw new Exception("File entry doesn't exist");
        }
        Files.delete(file.toPath());
        filesRepo.delete(fileEntry);
        result.put("message", "File deleted successfully");
        result.put("success", true);
        result.put("status", HttpStatus.OK.value());
      } else {
        result.put("error", "File doesn't exist");
        result.put("status", HttpStatus.NOT_FOUND.value());
      }
    } catch (Exception e) {
      result.put("error", "File cannot be deleted");
      result.put("success", false);
      result.put("status", HttpStatus.NOT_FOUND.value());
    }
    return ResponseEntity.ok(result);
  }

}
