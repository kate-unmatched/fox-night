package com.tsp.foxnight.services;

import com.tsp.foxnight.entity.InfoCompany;
import com.tsp.foxnight.utils.Errors;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.N;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoCompanyService {
    private final InfoCompany infoCompany = new InfoCompany();
    public InfoCompany updateFiles(List<MultipartFile> files){
//        for (MultipartFile file : files) {
//            if (file.isEmpty()) {
//                return Errors.E167
//            }
//            byte[] fileBytes = file.getBytes();
//            String fileDataBase64 = Base64.getEncoder().encodeToString(fileBytes);
//        }
//


        return null;
    }
}
