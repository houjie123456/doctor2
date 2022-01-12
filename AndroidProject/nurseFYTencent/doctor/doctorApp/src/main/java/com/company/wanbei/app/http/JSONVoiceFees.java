package com.company.wanbei.app.http;

/**
 * Created by YC on 2018/8/1.
 */

public class JSONVoiceFees {
//    soundSmall：语音最低费用（每分钟）
//    soundBig：语音最高费用（每分钟）
//    videoSmall：视频最低费用（每分钟）
//    videoBig：视频最高费用（每分钟）

    private String soundSmall;
    private String soundBig;
    private String videoSmall;
    private String videoBig;

    private String audioDurationMin;
    private String audioDurationMax;
    private String videoDurationMin;
    private String videoDurationMax;

    public String getSoundSmall() {
        return soundSmall;
    }

    public void setSoundSmall(String soundSmall) {
        this.soundSmall = soundSmall;
    }

    public String getSoundBig() {
        return soundBig;
    }

    public void setSoundBig(String soundBig) {
        this.soundBig = soundBig;
    }

    public String getVideoSmall() {
        return videoSmall;
    }

    public void setVideoSmall(String videoSmall) {
        this.videoSmall = videoSmall;
    }

    public String getVideoBig() {
        return videoBig;
    }

    public void setVideoBig(String videoBig) {
        this.videoBig = videoBig;
    }

    public String getAudioDurationMin() {
        return audioDurationMin;
    }

    public void setAudioDurationMin(String audioDurationMin) {
        this.audioDurationMin = audioDurationMin;
    }

    public String getAudioDurationMax() {
        return audioDurationMax;
    }

    public void setAudioDurationMax(String audioDurationMax) {
        this.audioDurationMax = audioDurationMax;
    }

    public String getVideoDurationMin() {
        return videoDurationMin;
    }

    public void setVideoDurationMin(String videoDurationMin) {
        this.videoDurationMin = videoDurationMin;
    }

    public String getVideoDurationMax() {
        return videoDurationMax;
    }

    public void setVideoDurationMax(String videoDurationMax) {
        this.videoDurationMax = videoDurationMax;
    }
}
