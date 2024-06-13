package com.tsp.foxnight.services;

import com.google.common.base.Objects;
import com.tsp.foxnight.auth.UserDetailsService;
import com.tsp.foxnight.dto.LinkDTO;
import com.tsp.foxnight.entity.Link;
import com.tsp.foxnight.entity.RestType;
import com.tsp.foxnight.entity.UserRole;
import com.tsp.foxnight.repositories.LinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.tsp.foxnight.utils.Errors.E167;
import static com.tsp.foxnight.utils.Errors.E613;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final RestAuditService restAuditService;
    private final UserDetailsService userDetailsService;

    @SneakyThrows
    public List<Link> getLinks(){
        List<Link> links = linkRepository.findAll();
        restAuditService.saveAuditRequest(RestType.GET, Collections.singletonMap("links", "all"));
        return links;
    }

    @SneakyThrows
    public Link createLink(LinkDTO linkDTO){
        E167.thr(!Objects.equal(userDetailsService.getRoleNow(), UserRole.EMPLOYEE));
        Link link = new Link()
                .setShortName(linkDTO.getShortName())
                .setUrl(linkDTO.getUrl());
        linkRepository.save(link);
        restAuditService.saveAuditRequest(RestType.POST, linkDTO);
        return link;
    }
    @SneakyThrows
    public Boolean deleteLink(Long linkId){
        E167.thr(!Objects.equal(userDetailsService.getRoleNow(), UserRole.EMPLOYEE));
        Link link = linkRepository.findById(linkId).orElseThrow(() -> E613.thr(linkId));
        linkRepository.delete(link);
        restAuditService.saveAuditRequest(RestType.DELETE, Collections.singletonMap("linkId", linkId));
        return true;
    }
}
