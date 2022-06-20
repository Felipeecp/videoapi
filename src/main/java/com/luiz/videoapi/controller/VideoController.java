package com.luiz.videoapi.controller;

import com.luiz.videoapi.dto.VideoDto;
import com.luiz.videoapi.form.VideoForm;
import com.luiz.videoapi.models.Video;
import com.luiz.videoapi.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/videos")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping
    public ResponseEntity<List<Video>> listarVideos(){
        List<Video> listVideos = videoRepository.findAll();
        if(listVideos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listVideos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> listarVideo(@PathVariable("id") Long id){
        Optional<Video> video = videoRepository.findById(id);
        return video.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VideoDto> cadastrar(@RequestBody @Valid VideoForm form, UriComponentsBuilder uriBuilder){
        Video video = form.converter();
        videoRepository.save(video);

        URI uri = uriBuilder.path("/api/videos/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new VideoDto(video));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<Video> atualizar(@PathVariable("id") Long id, @RequestBody @Valid VideoForm form){
        Optional<Video> videoData = videoRepository.findById(id);
        if(videoData.isPresent()){
            Video videoAtualizado = form.atualizar(id, videoRepository);
            return new ResponseEntity<>(videoRepository.save(videoAtualizado), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deletar(@PathVariable("id") Long id){
        try{
            videoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
