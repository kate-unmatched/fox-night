package com.tsp.foxnight.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsp.foxnight.auth.UserDetailsService;
import com.tsp.foxnight.dto.RestDTO;
import com.tsp.foxnight.dto.UserAllDTO;
import com.tsp.foxnight.entity.RestAudit;
import com.tsp.foxnight.entity.RestType;
import com.tsp.foxnight.entity.User;
import com.tsp.foxnight.repositories.RestAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestAuditService {
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;
    private final RestAuditRepository restAuditRepository;
    @SneakyThrows
    public void saveAuditRequest(RestType type, Object entity) {

        JsonNode entityJson = objectMapper.valueToTree(entity);
        String body = objectMapper.writeValueAsString(entityJson);

        RestAudit restAudit = new RestAudit()
                .setName(userDetailsService.getNameNow())
                .setRequestType(type)
                .setRole(userDetailsService.getRoleNow())
                .setBody(body);
        restAuditRepository.save(restAudit);
    }
    @SneakyThrows
    public void saveAuditRequest(RestType type, Map<?,?> entity) {

        JsonNode entityJson = objectMapper.valueToTree(entity);
        String body = objectMapper.writeValueAsString(entityJson);
        RestAudit restAudit = new RestAudit()
                .setName(userDetailsService.getNameNow())
                .setRequestType(type)
                .setRole(userDetailsService.getRoleNow())
                .setBody(body);
        restAuditRepository.save(restAudit);
    }

    public List<RestDTO> getAllRequests(){
        List<RestDTO> requestsShort = new ArrayList<>();
        List<RestAudit> requests = restAuditRepository.findAll();
        requests.forEach(x -> requestsShort.add(new RestDTO()
                .setId(x.getId())
                .setName(x.getName())
                .setRequestTime(x.getRequestTime())
                .setRole(x.getRole().name())
                .setRequestType(x.getRequestType().name())
                .setBody(x.getBody())));
        return requestsShort;
    }


}
