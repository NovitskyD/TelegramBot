package telegramBot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import telegramBot.entity.BinaryContent;
import telegramBot.service.FileService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/get-doc")
    public ResponseEntity<?> getDoc(@RequestParam("id") String id) {
        try {
            var document = fileService.getDocument(id);
            if (document == null) {
                throw new NoSuchElementException();
            }

            BinaryContent binaryContent = document.getBinaryContent();
            FileSystemResource fileSystemResource = fileService.getFileSystemResource(binaryContent);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(document.getMimeType()))
                    .header("Content-disposition", "attachment; filename*=UTF-8''"
                            + URLEncoder.encode(document.getDocName(), StandardCharsets.UTF_8))
                    .body(fileSystemResource);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }

    @GetMapping("/get-photo")
    public ResponseEntity<?> getPhoto(@RequestParam("id") String id) {
        try {
            var photo = fileService.getPhoto(id);
            if (photo == null) {
                throw new NoSuchElementException();
            }

            BinaryContent binaryContent = photo.getBinaryContent();
            FileSystemResource fileSystemResource = fileService.getFileSystemResource(binaryContent);
            if (fileSystemResource == null) {
                throw new IOException("File system resource not found");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header("Content-disposition", "attachment;")
                    .body(fileSystemResource);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
