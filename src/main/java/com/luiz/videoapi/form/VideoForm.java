package com.luiz.videoapi.form;

import com.luiz.videoapi.models.Video;
import com.luiz.videoapi.repository.VideoRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class VideoForm {

    @NotNull
    @NotEmpty
    private String titulo;

    @NotNull
    @NotEmpty
    private String descricao;

    @NotNull
    @NotEmpty
    private String url;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Video converter(){
        return new Video(titulo, descricao, url);
    }

    public Video atualizar(Long id, VideoRepository videoRepository) {
        Optional<Video> videoData = videoRepository.findById(id);
        if(videoData.isPresent()){
            Video video = videoData.get();
            video.setTitulo(this.titulo);
            video.setDescricao(this.descricao);
            video.setUrl(this.url);
            return video;
        }
        return null;
    }
}
