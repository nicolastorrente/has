package api.ticket;

import java.io.Serializable;

/**
 * Created by nicol on 03/09/2016.
 */
public class Document implements Serializable {
    private String fileName;
    private String mimeType;
    private Photo thumb;
    private String fileId;
    private long fileSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Photo getThumb() {
        return thumb;
    }

    public void setThumb(Photo thumb) {
        this.thumb = thumb;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
