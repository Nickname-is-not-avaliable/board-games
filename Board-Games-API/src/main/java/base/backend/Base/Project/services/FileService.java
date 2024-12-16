package base.backend.Base.Project.services;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileService {

    private final String uploadDir = "uploads/";

    public Boolean addFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image file is empty"
            );
        }

        String fileName = StringUtils.cleanPath(
                Objects.requireNonNull(file.getOriginalFilename())
        );

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);

            return true;
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to upload file",
                    e
            );
        }
    }

    public Resource loadFile(String fileName) throws IOException {
        Path filePath = Paths
                .get(uploadDir)
                .resolve(fileName)
                .toAbsolutePath()
                .normalize();
        return new UrlResource(filePath.toUri());
    }

    public Boolean removeFile(String fileName) {
        Path filePath = Paths.get(uploadDir).resolve(fileName).toAbsolutePath();

        try {
            Files.deleteIfExists(filePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}