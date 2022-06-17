package com.luiz.videoapi.controller;

import com.luiz.videoapi.models.Video;
import com.luiz.videoapi.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/videos")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping
    public List<Video> listarVideos(){
        List<Video> videos = videoRepository.findAll();
        return videos;
    }

}
